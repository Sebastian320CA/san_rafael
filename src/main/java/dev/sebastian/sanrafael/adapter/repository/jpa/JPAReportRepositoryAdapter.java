package dev.sebastian.sanrafael.adapter.repository.jpa;

import dev.sebastian.sanrafael.adapter.repository.jpa.entity.EventJpaEntity;
import dev.sebastian.sanrafael.adapter.repository.jpa.entity.ProductJpaEntity;
import dev.sebastian.sanrafael.adapter.repository.jpa.entity.ReportJpaEntity;
import dev.sebastian.sanrafael.core.entity.Event;
import dev.sebastian.sanrafael.core.entity.Report;
import dev.sebastian.sanrafael.core.port.ReportRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation; // 👈 Añadido para el control transaccional
import org.springframework.transaction.annotation.Transactional; // 👈 Añadido para el control transaccional

import java.util.List;
import java.util.Optional;

@Repository
public class JPAReportRepositoryAdapter implements ReportRepository {

    private final SpringDataReportRepository reportRepository;
    private final SpringDataEventRepository eventRepository;

    public JPAReportRepositoryAdapter(
            SpringDataReportRepository reportRepository,
            SpringDataEventRepository eventRepository
    ) {
        this.reportRepository = reportRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveReport(Long productId, Report report) {

        ReportJpaEntity reportEntity = new ReportJpaEntity();

        ProductJpaEntity product = new ProductJpaEntity();
        product.setId(productId);

        reportEntity.setProduct(product);

        if (report.date() != null) {
            java.time.LocalDate localDate = report.date().toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();
            reportEntity.setDate(localDate);
        }

        reportEntity.setRamUsagePercentage(report.ramUsagePercentage());
        reportEntity.setDiskUsagePercentage(report.diskUsagePercentage());

        ReportJpaEntity savedReport = reportRepository.save(reportEntity);

        List<EventJpaEntity> events = report.events()
                .stream()
                .map(e -> {
                    EventJpaEntity eventEntity = new EventJpaEntity();
                    eventEntity.setDescription(e.description());
                    eventEntity.setCondition(e.condition());
                    eventEntity.setReport(savedReport);
                    return eventEntity;
                })
                .toList();

        eventRepository.saveAll(events);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Report> findLastByProduct(Long productId) {

        return reportRepository.findByProductId(productId)
                .stream()
                .max((a, b) -> a.getDate().compareTo(b.getDate()))
                .map(reportEntity -> {

                    List<Event> events = eventRepository
                            .findByReportId(reportEntity.getId())
                            .stream()
                            .map(e -> new Event(
                                    String.valueOf(e.getId()),
                                    e.getCondition(),
                                    e.getDescription()
                            ))
                            .toList();

                    java.util.Date domainDate = java.sql.Date.valueOf(reportEntity.getDate());

                    return new Report(
                            String.valueOf(reportEntity.getId()),
                            domainDate,
                            events,
                            reportEntity.getRamUsagePercentage(),
                            reportEntity.getDiskUsagePercentage()
                    );
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Report> findByProduct(Long productId) {
        return reportRepository.findByProductId(productId)
                .stream()
                .map(reportEntity -> {
                    List<Event> events = eventRepository
                            .findByReportId(reportEntity.getId())
                            .stream()
                            .map(e -> new Event(
                                    String.valueOf(e.getId()),
                                    e.getCondition(),
                                    e.getDescription()
                            ))
                            .toList();

                    java.util.Date domainDate = java.sql.Date.valueOf(reportEntity.getDate());

                    return new Report(
                            String.valueOf(reportEntity.getId()),
                            domainDate,
                            events,
                            reportEntity.getRamUsagePercentage(),
                            reportEntity.getDiskUsagePercentage()
                    );
                })
                .toList();
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void deleteEventById(Long eventId) {
        System.out.println("-> Ejecutando borrado físico en Postgres para el evento ID: " + eventId);
        eventRepository.deleteById(eventId);
    }



}

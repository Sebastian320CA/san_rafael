package dev.sebastian.sanrafael.core.service;

import dev.sebastian.sanrafael.core.constant.Condition;
import dev.sebastian.sanrafael.core.dto.ProductStatusDTO;
import dev.sebastian.sanrafael.core.dto.ReportDTO;
import dev.sebastian.sanrafael.core.entity.Event;
import dev.sebastian.sanrafael.core.entity.Product;
import dev.sebastian.sanrafael.core.entity.Report;
import dev.sebastian.sanrafael.core.port.HealthReportGateway;
import dev.sebastian.sanrafael.core.port.ProductRepository;
import dev.sebastian.sanrafael.core.port.ReportRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StatusRequestService {

    private final ProductRepository productRepository;
    private final HealthReportGateway healthReportGateway;
    private final ReportRepository reportRepository;

    public StatusRequestService(
            ProductRepository productRepository,
            HealthReportGateway healthReportGateway,
            @Qualifier("JPAReportRepositoryAdapter") ReportRepository reportRepository
    ) {
        this.productRepository = productRepository;
        this.healthReportGateway = healthReportGateway;
        this.reportRepository = reportRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAllProducts();
    }

    @Scheduled(fixedRate = 30000)
    public void requestStatusOfAllProducts() {
        List<Product> products = productRepository.findAllProducts();

        for (Product product : products) {
            try {
                System.out.println("Requesting service status for " + product.name());
                ReportDTO dto = healthReportGateway.getHealthReport(product.healthURL());

                List<Event> domainEvents = dto.events()
                        .stream()
                        .map(e -> new Event(
                                null,
                                mapCondition(e.condition().name()),
                                e.description()
                        ))
                        .toList();

                Report domainReport = new Report(
                        null,
                        new Date(),
                        domainEvents,
                        dto.ramPercentage(),
                        dto.diskPercentage()
                );

                reportRepository.saveReport(product.id(), domainReport);
                System.out.println("Report saved successfully for " + product.name());

            } catch (Exception e) {

                System.out.println("Servidor offline o inaccesible para " + product.name() + ". Guardando reporte de caída...");

                List<Event> mockEvents = List.of(
                        new Event(null, Condition.CONDITION_ERROR, "Falla de Monitoreo: El endpoint de salud no responde (Timeout / Connection Refused).")
                );


                Report mockReport = new Report(
                        null,
                        new Date(),
                        mockEvents,
                        0,
                        0
                );

                reportRepository.saveReport(product.id(), mockReport);
            }
        }
    }

    public List<ProductStatusDTO> getCurrentStatus() {
        return productRepository.findAllProducts().stream()
                .map(product -> {

                    Report lastReport = reportRepository.findLastByProduct(product.id()).orElse(null);

                    if (lastReport == null) {
                        return new ProductStatusDTO(
                                product.name(),
                                0,
                                0,
                                "CONDITION_UNKNOWN",
                                "Sin reportes",
                                List.of()
                        );
                    }

                    return new ProductStatusDTO(
                            product.name(),
                            lastReport.diskUsagePercentage(),
                            lastReport.ramUsagePercentage(),
                            lastReport.calculateCondition().name(),
                            "Último reporte",
                            lastReport.events()
                    );
                })
                .toList();
    }

    private Condition mapCondition(String condition) {
        if (condition == null) return Condition.CONDITION_ACTIVE;

        return switch (condition.toUpperCase()) {
            case "ERROR", "CONDITION_ERROR" -> Condition.CONDITION_ERROR;
            case "WARNING", "CONDITION_WARNING" -> Condition.CONDITION_WARNING;

            default -> Condition.CONDITION_ACTIVE;
        };
    }
}
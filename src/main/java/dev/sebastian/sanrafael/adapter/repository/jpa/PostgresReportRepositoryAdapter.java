package dev.sebastian.sanrafael.adapter.repository.jpa;

import dev.sebastian.sanrafael.adapter.repository.jpa.entity.ReportJpaEntity;
import dev.sebastian.sanrafael.core.entity.Report;
import dev.sebastian.sanrafael.core.port.ReportRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PostgresReportRepositoryAdapter implements ReportRepository {
    private final SpringDataReportRepository springDataRepository;

    public PostgresReportRepositoryAdapter(SpringDataReportRepository springDataReportRepository) {
        this.springDataRepository = springDataReportRepository;
    }

    @Override
    public void saveReport(Report report) {

        ReportJpaEntity jpaEntity = new ReportJpaEntity();

        jpaEntity.setDate(LocalDate.now());
        jpaEntity.setRamUsagePercentage(report.ramUsagePercentage());
        jpaEntity.setDiskUsagePercentage(report.diskUsagePercentage());

        springDataRepository.save(jpaEntity);

    }
}

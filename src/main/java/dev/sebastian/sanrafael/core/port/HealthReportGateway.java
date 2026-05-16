package dev.sebastian.sanrafael.core.port;

import dev.sebastian.sanrafael.core.dto.ReportDTO;
import dev.sebastian.sanrafael.core.entity.Product;

public interface HealthReportGateway {
    ReportDTO getHealthReport(String productName);
}


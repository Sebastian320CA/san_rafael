package dev.sebastian.sanrafael.adapter.api.rest;

import dev.sebastian.sanrafael.core.dto.ReportDTO;
import dev.sebastian.sanrafael.core.port.HealthReportGateway;

public class RestHealthReportGateway implements HealthReportGateway {

    @Override
    public ReportDTO getHealthReport(String productName) {
        return null;
    }
}

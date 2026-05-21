package dev.sebastian.sanrafael.core.port;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.sebastian.sanrafael.core.dto.ReportDTO;
import dev.sebastian.sanrafael.core.entity.Product;

import java.net.URL;

public interface HealthReportGateway {
    ReportDTO getHealthReport(URL healthEndpoint) throws JsonProcessingException;
}


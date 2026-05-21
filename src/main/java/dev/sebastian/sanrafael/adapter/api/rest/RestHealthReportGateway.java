package dev.sebastian.sanrafael.adapter.api.rest;

import dev.sebastian.sanrafael.core.dto.ReportDTO;
import dev.sebastian.sanrafael.core.port.HealthReportGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

public class RestHealthReportGateway implements HealthReportGateway {
    final RestTemplate restTemplate = new RestTemplate();
    @Override
    public ReportDTO getHealthReport(URL healthEndpoint) {
        try{
            ResponseEntity<ReportDTO> response =
                restTemplate.getForEntity(healthEndpoint.toString(), ReportDTO.class);
            return response.getBody();
        }catch(Exception e){
            throw new RuntimeException("Failed to get health report" + healthEndpoint, e);
        }
    }
}

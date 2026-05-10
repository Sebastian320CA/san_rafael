package dev.sebastian.sanrafael;

import dev.sebastian.sanrafael.adapter.api.spring.SpringAPI;
import dev.sebastian.sanrafael.adapter.repository.inmemory.InMemoryHealthReportGateway;
import dev.sebastian.sanrafael.adapter.repository.inmemory.InMemoryProductRepository;
import dev.sebastian.sanrafael.core.dto.ReportDTO;
import dev.sebastian.sanrafael.core.port.HealthReportGateway;
import dev.sebastian.sanrafael.core.port.ProductRepository;
import dev.sebastian.sanrafael.core.service.StatusRequestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Date;

import static dev.sebastian.sanrafael.adapter.api.spring.SpringConfig.service;


@SpringBootApplication
public class StatusApplication {

    public static void main(String[] args) {
        ProductRepository productRepository = new InMemoryProductRepository();
        HealthReportGateway healthReportGateway = new InMemoryHealthReportGateway(productRepository);

        ReportDTO report = new ReportDTO(new Date(),new ArrayList<>(),23, 32);
                service.requestStatusOfAllProducts();

        SpringAPI api = new SpringAPI(service);
        api.runServer(args);
    }

}

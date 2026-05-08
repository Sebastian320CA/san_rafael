package dev.sebastian.sanrafael.adapter.repository.inmemory;

import dev.sebastian.sanrafael.core.dto.ReportDTO;
import dev.sebastian.sanrafael.core.entity.Product;
import dev.sebastian.sanrafael.core.port.HealthReportGateway;
import dev.sebastian.sanrafael.core.port.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class InMemoryHealthReportGateway implements HealthReportGateway {

    private final ProductRepository productRepository;
    private final RestTemplate restTemplate = new  RestTemplate();

    public InMemoryHealthReportGateway(ProductRepository productRepository) {
        this.productRepository=productRepository;
    }
    @Override
    public ReportDTO getHealthReport(String productName) {
        Product product = productRepository.findByName(productName);

        if(product == null){
            throw new IllegalArgumentException("Product not found");
        }
        String url = product.healthURL().toString();

        ResponseEntity<ReportDTO> response = restTemplate.getForEntity(url,
                ReportDTO.class);

        return response.getBody();
    }
}

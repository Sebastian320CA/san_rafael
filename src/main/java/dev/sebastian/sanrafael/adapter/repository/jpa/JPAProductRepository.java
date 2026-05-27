package dev.sebastian.sanrafael.adapter.repository.jpa;

import dev.sebastian.sanrafael.adapter.repository.jpa.entity.ProductJpaEntity;
import dev.sebastian.sanrafael.core.entity.Event;
import dev.sebastian.sanrafael.core.entity.Product;
import dev.sebastian.sanrafael.core.entity.Report;
import dev.sebastian.sanrafael.core.port.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

@Component
@Primary
public class JPAProductRepository implements ProductRepository {

    private final ProductSpringDataRepository repository;
    public JPAProductRepository(ProductSpringDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> findAllProducts() {

        List<ProductJpaEntity> entities = repository.findAll();

        return entities.stream()
                .map(entity -> {
                    try {
                        return new Product(
                                entity.getId(),
                                entity.getProductName(),
                                List.of(),
                                new URL(entity.getHealthUrl())
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    @Override
    public Product findProductByName(String productName) {

        ProductJpaEntity entity = repository.findByProductName(productName)
                .orElseThrow();

        try {
            return new Product(
                    entity.getId(),
                    entity.getProductName(),
                    List.of(),
                    new URL(entity.getHealthUrl())
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product findById(Long id) {

        ProductJpaEntity entity = repository.findById(id)
                .orElseThrow();

        try {
            return new Product(
                    entity.getId(),
                    entity.getProductName(),
                    List.of(),
                    new URL(entity.getHealthUrl())
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void saveProduct(Product product) {

        ProductJpaEntity entity = new ProductJpaEntity();

        entity.setProductName(product.name());
        entity.setHealthUrl(product.healthURL().toString());

        repository.save(entity);
    }

    @Override
    public void updateProduct(Product product) {

        ProductJpaEntity entity = repository.findById(product.id())
                .orElseThrow();

        entity.setProductName(product.name());
        entity.setHealthUrl(product.healthURL().toString());

        repository.save(entity);
    }
    @Override
    public void addReportToProduct(Product product, Report report) {

    }

    @Override
    public void deleteReport(String reportId) {

    }

    @Override
    public void addEventToReport(String reportId, Event event) {

    }

    @Override
    public void deleteEvent(String eventId) {

    }

    @Override
    public Product findByName(String productName) {
        return null;
    }
}

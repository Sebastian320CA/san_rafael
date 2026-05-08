package dev.sebastian.sanrafael.adapter.repository.inmemory;

import dev.sebastian.sanrafael.core.entity.Event;
import dev.sebastian.sanrafael.core.entity.Product;
import dev.sebastian.sanrafael.core.entity.Report;
import dev.sebastian.sanrafael.core.port.ProductRepository;

import java.util.ArrayList;
import java.util.List;


public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = new ArrayList<>();

    @Override
    public List<Product> findAllProducts() {
        return products;
    }

    @Override
    public Product findProductByName(String productName) {
       return products.stream()
               .filter(p-> p.name().equals(productName))
               .findFirst().orElse(null);
    }

    @Override
    public void saveProduct(Product product) {
        products.add(product);
    }

    @Override
    public void addReportToProduct(Product product, Report report) {
        products.stream()
                .filter(p -> p.name().equals(product.name()))
                .findFirst()
                .ifPresent(p ->  p.reports().add(report));
    }

    @Override
    public void deleteReport(String reportId) {
        products.forEach(p ->
                p.reports().removeIf(r -> r.id().equals(reportId))
        );
    }

    @Override
    public void addEventToReport(String reportId, Event event) {
        products.stream()
                .flatMap(p -> p.reports().stream())
                .filter(r -> r.id().equals(reportId))
                .findFirst()
                .ifPresent(r -> r.events().add(event));
    }

    @Override
    public void deleteEvent(String eventId) {
        products.stream()
                .flatMap(p -> p.reports().stream())
                .forEach(r -> r.events().removeIf(e -> e.id().equals(eventId)));
    }

    @Override
    public Product findByName(String productName) {
        return null;
    }
}

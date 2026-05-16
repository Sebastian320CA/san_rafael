package dev.sebastian.sanrafael.adapter.repository.jpa;

import dev.sebastian.sanrafael.core.entity.Event;
import dev.sebastian.sanrafael.core.entity.Product;
import dev.sebastian.sanrafael.core.entity.Report;
import dev.sebastian.sanrafael.core.port.ProductRepository;

import java.util.List;

public class JpaProductRepository implements ProductRepository {
    @Override
    public List<Product> findAllProducts() {
        return List.of();
    }

    @Override
    public Product findProductByName(String productName) {
        return null;
    }

    @Override
    public void saveProduct(Product product) {

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

package dev.sebastian.sanrafael.core.port;

import dev.sebastian.sanrafael.core.entity.Event;
import dev.sebastian.sanrafael.core.entity.Product;
import dev.sebastian.sanrafael.core.entity.Report;

import java.util.List;

/*
 * This is a comment:)
 */

public interface ProductRepository {
    List<Product> findAllProducts();

    Product findProductByName(String productName);

    Product findById(Long id);

    void saveProduct(Product product);

    void updateProduct(Product product);

    void deleteById(Long id);

    void addReportToProduct(Product product, Report report);

    void deleteReport(String reportId);

    void addEventToReport(String reportId, Event event);

    void deleteEvent(String eventId);

    Product findByName(String productName);
}

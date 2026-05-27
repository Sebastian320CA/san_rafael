package dev.sebastian.sanrafael.core.service;

import dev.sebastian.sanrafael.core.dto.ProductDTO;
import dev.sebastian.sanrafael.core.dto.ProductRequest;
import dev.sebastian.sanrafael.core.entity.Product;
import dev.sebastian.sanrafael.core.port.ProductRepository;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void save(ProductRequest request) {

        try {
            Product product = new Product(
                    null,
                    request.name(),
                    List.of(),
                    new URL(request.healthUrl())
            );

            repository.saveProduct(product);

        } catch (Exception e) {
            throw new RuntimeException("URL inválida: " + request.healthUrl(), e);
        }
    }

    public List<ProductDTO> findAll() {

        return repository.findAllProducts()
                .stream()
                .map(p -> new ProductDTO(
                        p.id(),
                        p.name(),
                        p.healthURL().toString()
                ))
                .toList();
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public ProductDTO findById(Long id) {

        Product product = repository.findById(id);

        return new ProductDTO(
                product.id(),
                product.name(),
                product.healthURL().toString()
        );
    }
    public void update(Long id, ProductRequest request) {

        try {

            Product product = new Product(
                    id,
                    request.name(),
                    List.of(),
                    new URL(request.healthUrl())
            );

            repository.updateProduct(product);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

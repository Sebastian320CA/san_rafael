package dev.sebastian.sanrafael.adapter.ui.spring;

import dev.sebastian.sanrafael.core.dto.ProductDTO;
import dev.sebastian.sanrafael.core.dto.ProductRequest;
import dev.sebastian.sanrafael.core.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductAPI {
    private final ProductService service;

    public ProductAPI(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProductRequest request) {
        service.save(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<ProductDTO> findAll() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}

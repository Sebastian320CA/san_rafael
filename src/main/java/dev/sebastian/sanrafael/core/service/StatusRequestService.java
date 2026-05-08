package dev.sebastian.sanrafael.core.service;

import dev.sebastian.sanrafael.core.entity.Product;
import dev.sebastian.sanrafael.core.port.ProductRepository;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StatusRequestService {
    public static final int REQUEST_FREQUENCY = 1;
    public static TimeUnit REQUEST_FREQUENCY_UNIT = TimeUnit.SECONDS;

    private final ProductRepository repository;
    public StatusRequestService(ProductRepository repository) {
        this.repository = repository;

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::requestStatusOfAllProducts, 0,REQUEST_FREQUENCY,
                REQUEST_FREQUENCY_UNIT);
    }
    public void requestStatusOfAllProducts() {
        List<Product> products = repository.findAllProducts();
        products.forEach(product -> {
            System.out.println("Solicitando etado de servicio" + product);
        });
        System.out.println("Termine de solicitar todos los productos");
    }
}

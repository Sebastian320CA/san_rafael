package dev.sebastian.sanrafael;

import dev.sebastian.sanrafael.adapter.repository.inmemory.InMemoryProductRepository;
import dev.sebastian.sanrafael.core.port.ProductRepository;
import dev.sebastian.sanrafael.core.service.StatusRequestService;

public class StatusApplication {

    public static void main(String[] args) {
        ProductRepository repository = new InMemoryProductRepository();

        //repository.saveProduct();

        StatusRequestService service = new StatusRequestService(repository);

    }

}

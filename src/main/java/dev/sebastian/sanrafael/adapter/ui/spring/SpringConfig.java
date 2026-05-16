package dev.sebastian.sanrafael.adapter.ui.spring;

import dev.sebastian.sanrafael.adapter.repository.inmemory.InMemoryHealthReportGateway;
import dev.sebastian.sanrafael.adapter.repository.inmemory.InMemoryProductRepository;
import dev.sebastian.sanrafael.core.port.ProductRepository;
import dev.sebastian.sanrafael.core.service.StatusRequestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    /*
    * Al dejar para este caso en especifico esta clase vacia , SpringBoot usara la
    * anotación de StatusRequestService para inyectar automaticamente en el repositorio lo cual
    * es que se lleve a cabo la arquitectura hexagonal satisfactoriamente.
    * */
}

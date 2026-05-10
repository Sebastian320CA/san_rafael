package dev.sebastian.sanrafael.adapter.api.spring;

import dev.sebastian.sanrafael.core.service.StatusRequestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAPI {
    public SpringApi(StatusRequestService service) {
        SpringConfig.service = service;

    }

        public void runServer(String[] args) {
            SpringApplication.run(SpringAPI.class, args);
    }
}

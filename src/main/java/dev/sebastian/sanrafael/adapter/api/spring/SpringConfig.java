package dev.sebastian.sanrafael.adapter.api.spring;

import dev.sebastian.sanrafael.core.service.StatusRequestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    public static StatusRequestService service;

    @Bean
    public StatusRequestService injectStatusService() {
        return this.injectStatusService();
    }
}

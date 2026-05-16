package dev.sebastian.sanrafael.adapter.ui.spring;

import dev.sebastian.sanrafael.core.service.StatusRequestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "+") //Esto nos permite que para aplicar al FrontEnd al momento de que se conecte, este no presente incomveniente de algun bloqueo.
public class SpringAPI {

    private final StatusRequestService service;

    public SpringAPI(StatusRequestService service) {
        this.service = service;
    }

    @GetMapping("/status")
    public String getStatus(){
        service.requestStatusOfAllProducts();
        return "{\"status\": \"Scan request processed successfully\"}";
    }
}

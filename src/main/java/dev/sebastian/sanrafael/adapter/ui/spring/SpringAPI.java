package dev.sebastian.sanrafael.adapter.ui.spring;

import dev.sebastian.sanrafael.core.dto.ProductStatusDTO;
import dev.sebastian.sanrafael.core.service.StatusRequestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class SpringAPI {

    private final StatusRequestService service;

    public SpringAPI(StatusRequestService service) {
        this.service = service;
    }

    @GetMapping("/status")
    public List<ProductStatusDTO> getStatus() {
        return service.getCurrentStatus();
    }
}

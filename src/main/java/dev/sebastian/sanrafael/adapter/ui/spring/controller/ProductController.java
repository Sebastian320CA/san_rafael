package dev.sebastian.sanrafael.adapter.ui.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class ProductController {
    @GetMapping("/")
    public String showIndex() {
        return "products/index";
    }
    @GetMapping("/login")
    public String loginAdmin(){
        return "products/login";
    }
    @GetMapping("/productos")
    public String showProducts(){
        return "products/productos";
    }
    @GetMapping("/reportes")
    public String showReports(){
        return "products/reportes";
    }
    @GetMapping("/eventos")
    public String showEventos(){
        return "products/productos";
    }
}


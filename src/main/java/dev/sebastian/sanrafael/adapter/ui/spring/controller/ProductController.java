package dev.sebastian.sanrafael.adapter.ui.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class ProductController {
    @GetMapping("/")
    public String showIndex() {
        return "products/index";
    }
}


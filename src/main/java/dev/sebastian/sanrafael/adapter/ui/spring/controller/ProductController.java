package dev.sebastian.sanrafael.adapter.ui.spring.controller;

import dev.sebastian.sanrafael.adapter.repository.jpa.JPAUserRepository;
import dev.sebastian.sanrafael.adapter.repository.jpa.entity.UserEntity;
import dev.sebastian.sanrafael.core.dto.ProductRequest;
import dev.sebastian.sanrafael.core.dto.ReportDTO;
import dev.sebastian.sanrafael.core.service.ProductService;
import dev.sebastian.sanrafael.core.service.ReportService;
import dev.sebastian.sanrafael.core.service.StatusRequestService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProductController {

    private final StatusRequestService statusRequestService;
    private final ProductService productService;
    private final ReportService reportService;

    private final JPAUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ProductController(StatusRequestService statusRequestService,
                             ProductService productService,
                             ReportService reportService,
                             JPAUserRepository userRepository,
                             BCryptPasswordEncoder passwordEncoder) {
        this.statusRequestService = statusRequestService;
        this.productService = productService;
        this.reportService = reportService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/fix-admin")
    @ResponseBody
    public String fixAdmin() {
        var optUser = userRepository.findByUsername("admin@hospitalsanrafael.com");

        UserEntity admin;
        if (optUser.isPresent()) {
            admin = optUser.get();
        } else {
            admin = new UserEntity();
            admin.setUsername("admin@hospitalsanrafael.com");
        }

        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole("ROLE_ADMIN");
        admin.setUserState(true);

        userRepository.save(admin);

        return "Administrador actualizado con éxito por Java. Contraseña reseteada a: admin123";
    }

    @GetMapping("/")
    public String showIndex(Model model) {
        var activeProducts = statusRequestService.getCurrentStatus();
        model.addAttribute("products", activeProducts);

        String globalStatus = "CONDITION_ACTIVE";
        for (var product : activeProducts) {
            if ("CONDITION_ERROR".equals(product.condition())) {
                globalStatus = "CONDITION_ERROR";
                break;
            } else if ("CONDITION_WARNING".equals(product.condition())) {
                globalStatus = "CONDITION_WARNING";
            }
        }
        model.addAttribute("globalStatus", globalStatus);

        return "products/index";
    }

    @GetMapping("/login")
    public String loginAdmin() {
        return "products/login";
    }

    @GetMapping("/productos")
    public String showProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products/productos";
    }

    @PostMapping("/productos")
    public String createProduct(@RequestParam String productName, @RequestParam String healthUrl) {
        productService.save(new ProductRequest(productName, healthUrl));
        return "redirect:/productos";
    }

    @GetMapping("/productos/editar/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("productEdit", productService.findById(id));
        return "products/productos";
    }

    @PostMapping("/productos/actualizar")
    public String updateProduct(@RequestParam Long id, @RequestParam String productName, @RequestParam String healthUrl) {
        productService.update(id, new ProductRequest(productName, healthUrl));
        return "redirect:/productos";
    }

    @PostMapping("/productos/eliminar/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/productos";
    }

    @GetMapping("/reportes")
    public String showReports(@RequestParam(required = false) Long productId, Model model) {
        model.addAttribute("products", productService.findAll());

        if (productId != null) {
            List<ReportDTO> reportes = reportService.getReportsOrMockContingency(productId);

            model.addAttribute("reports", reportes);
            model.addAttribute("selectedProductId", productId);
        }
        return "products/reportes";
    }

    @PostMapping("/eventos/eliminar/{id}")
    public String deleteEventFromBitacora(@PathVariable Long id, @RequestParam(required = false) Long productId) {
        reportService.deleteEventById(id);

        if (productId != null) {
            return "redirect:/reportes?productId=" + productId;
        }
        return "redirect:/eventos";
    }

    @GetMapping("/eventos")
    public String showEventos(Model model) {
        List<?> todosLosEventos = statusRequestService.findAllProducts().stream()
                .flatMap(product -> reportService.findByProduct(product.id()).stream())
                .flatMap(reportDTO -> reportDTO.events().stream())
                .toList();

        model.addAttribute("events", todosLosEventos);
        model.addAttribute("isAdmin", true);

        return "products/eventos";
    }
}
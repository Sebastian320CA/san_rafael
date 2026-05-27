package dev.sebastian.sanrafael.core.entity;

import java.net.URL;
import java.util.List;

/*
 * Product es en este caso un modelo de dominio inmutable que representa un evento dentro del sistema y
 * este product pertenece al nucleo del dominio, por lo tanto no depende de plugins adicionales como Spring o
 * Jpa para su funcionamiento.
 * */
public record Product(
        Long id,
        String name,
        List<Report> reports,
        URL healthURL
) {
}

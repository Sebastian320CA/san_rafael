package dev.sebastian.sanrafael.core.entity;

import dev.sebastian.sanrafael.core.constant.Condition;


/*
* Event es en este caso un modelo de dominio inmutable que representa un evento dentro del sistema y
* este record pertenece al nucleo del dominio, por lo tanto no depende de plugins adicionales como Spring o
* Jpa para su funcionamiento.
* */
public record Event(
        String id,
        Condition condition,
        String description
) {}

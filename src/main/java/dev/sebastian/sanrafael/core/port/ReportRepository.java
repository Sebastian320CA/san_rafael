package dev.sebastian.sanrafael.core.port;

import dev.sebastian.sanrafael.core.entity.Report;
import java.util.List;
import java.util.Optional;

/*
 * Este ReportRepository es el encargado de la salida, la cual define cómo el Core le solicita
 * guardar y recuperar los respectivos reportes.
 * */
public interface ReportRepository {

    void saveReport(Long productId, Report report);

    Optional<Report> findLastByProduct(Long productId);

    List<Report> findByProduct(Long productId);

    void deleteEventById(Long eventId);

}

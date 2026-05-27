package dev.sebastian.sanrafael.core.service;

import dev.sebastian.sanrafael.core.dto.ReportDTO;
import dev.sebastian.sanrafael.core.entity.Event;
import dev.sebastian.sanrafael.core.entity.Report;
import dev.sebastian.sanrafael.core.constant.Condition; // Asegúrate de que apunte a tu enum
import dev.sebastian.sanrafael.core.port.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public List<ReportDTO> findByProduct(Long productId) {
        return repository.findByProduct(productId)
                .stream()
                .map(report -> new ReportDTO(
                        report.date(),
                        report.events(),
                        report.ramUsagePercentage(),
                        report.diskUsagePercentage()
                ))
                .toList();
    }


    public List<ReportDTO> getReportsOrMockContingency(Long productId) {
        List<ReportDTO> reportes = findByProduct(productId);

        if (reportes.isEmpty()) {
            System.out.println("[Core Service] Base de datos vacía para producto ID: " + productId + ". Generando datos de contingencia...");

            Event eventoPrueba = new Event(
                    null,
                    Condition.CONDITION_ERROR,
                    "Alerta de Monitoreo: Tiempo de respuesta del servicio excede el límite permitido (Timeout > 5000ms)."
            );

            Report reporteSimulado = new Report(
                    null,
                    new Date(),
                    List.of(eventoPrueba),
                    76,
                    85
            );


            try {
                repository.saveReport(productId, reporteSimulado);
                System.out.println("[Core Service] ¡Métricas de contingencia guardadas correctamente en el puerto!");
            } catch (Exception e) {
                System.err.println("[Core Service] Error al persistir reporte de contingencia: " + e.getMessage());
            }


            reportes = findByProduct(productId);
        }

        return reportes;
    }

    public void deleteEventById(Long eventId) {
        System.out.println("[Core Service] Solicitando remoción física del evento ID: " + eventId);
        try {
            repository.deleteEventById(eventId);
        } catch (Exception e) {
            System.err.println("[Core Service] Error al eliminar el evento en el repositorio: " + e.getMessage());
        }
    }
}
package dev.sebastian.sanrafael.core.port;

import dev.sebastian.sanrafael.core.entity.Report;

/*
* Este ReportRepository es el encargado de la salida, la cual define como el Core le solicita
* Guardar los respectivos reportes.
* */
public interface ReportRepository {
    public void saveReport(Report report);
}

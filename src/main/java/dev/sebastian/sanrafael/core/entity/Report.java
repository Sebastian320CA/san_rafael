package dev.sebastian.sanrafael.core.entity;

import java.util.Date;
import java.util.List;
import dev.sebastian.sanrafael.core.constant.Condition;


/*
 * Report es en este caso un modelo de dominio inmutable que representa un evento dentro del sistema y
 * este report pertenece al nucleo del dominio, por lo tanto no depende de plugins adicionales como Spring o
 * Jpa para su funcionamiento.
 * */
public record Report(
        String id,
        Date date,
        List<Event> events,
        int ramUsagePercentage,
        int diskUsagePercentage
) {

    /*
    * Condition calculateCondition calcula el estado del reporte basado en los eventos que este tenga asociado,
    *
    * */
    public Condition calculateCondition() {
        if(isAnyEventWithCondition(Condition.CONDITION_ERROR)){
            return Condition.CONDITION_ERROR;
        }
        if(isAnyEventWithCondition(Condition.CONDITION_WARNING)){
            return Condition.CONDITION_ACTIVE;
        }
        return Condition.CONDITION_ACTIVE;
    }

    /*
    * Este boolean isAnyEventWithCondition se encarga de verificar si existe al menos un evento con la condición indicada.
    * */
    private boolean isAnyEventWithCondition(Condition condition) {
        return events.stream().anyMatch(event -> event.equals(condition));
    }
}

package dev.sebastian.sanrafael.core.dto;

import dev.sebastian.sanrafael.core.entity.Event;

import java.util.Date;
import java.util.List;

public record ReportDTO(
        Date date,
        List<Event> events,
        int ramPercentage,
        int diskPercentage
) {
}

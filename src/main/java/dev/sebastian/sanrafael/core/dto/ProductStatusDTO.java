package dev.sebastian.sanrafael.core.dto;

import dev.sebastian.sanrafael.core.entity.Event;

import java.util.List;

public record ProductStatusDTO(
        String name,
        int ramUsage,
        int diskUsage,
        String condition,
        String lastReport,
        List<Event> events
) {
}

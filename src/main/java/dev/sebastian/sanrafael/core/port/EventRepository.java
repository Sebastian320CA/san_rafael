package dev.sebastian.sanrafael.core.port;

import dev.sebastian.sanrafael.core.entity.Event;

import java.util.List;

public interface EventRepository {
    List<Event> findAllEvents();
}

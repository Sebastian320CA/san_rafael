package dev.sebastian.sanrafael.core.service;

import dev.sebastian.sanrafael.core.entity.Event;
import dev.sebastian.sanrafael.core.port.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public List<Event> findAll() {
        return repository.findAllEvents();
    }
}

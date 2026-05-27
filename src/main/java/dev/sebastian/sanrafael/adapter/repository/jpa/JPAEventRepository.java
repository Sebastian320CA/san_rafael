package dev.sebastian.sanrafael.adapter.repository.jpa;

import dev.sebastian.sanrafael.core.entity.Event;
import dev.sebastian.sanrafael.core.port.EventRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JPAEventRepository  implements EventRepository {
    private final EventSpringDataRepository repository;

    public JPAEventRepository(EventSpringDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Event> findAllEvents() {
        return repository.findAll()
                .stream()
                .map(e -> new Event(
                        String.valueOf(e.getId()),
                        e.getCondition(),
                        e.getDescription()
                ))
                .toList();
    }
}

package dev.sebastian.sanrafael.adapter.repository.jpa;

import dev.sebastian.sanrafael.adapter.repository.jpa.entity.EventJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventSpringDataRepository extends JpaRepository<EventJpaEntity, Long> {
}

package dev.sebastian.sanrafael.adapter.repository.jpa;

import dev.sebastian.sanrafael.adapter.repository.jpa.entity.EventJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataEventRepository extends JpaRepository<EventJpaEntity, Long> {
    List<EventJpaEntity> findByReportId(Long reportId);
}

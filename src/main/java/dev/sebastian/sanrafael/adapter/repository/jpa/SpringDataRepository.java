package dev.sebastian.sanrafael.adapter.repository.jpa;

import dev.sebastian.sanrafael.adapter.repository.jpa.entity.ReportJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataRepository extends JpaRepository<ReportJpaEntity, Long> {
    List<ReportJpaEntity> findByProductId(Long productId);
}

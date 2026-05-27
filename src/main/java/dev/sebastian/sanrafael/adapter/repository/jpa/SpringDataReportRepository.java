package dev.sebastian.sanrafael.adapter.repository.jpa;

import dev.sebastian.sanrafael.adapter.repository.jpa.entity.ReportJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataReportRepository extends JpaRepository<ReportJpaEntity, Long> {

    @Query("SELECT r FROM ReportJpaEntity r WHERE r.product.id = :productId")
    List<ReportJpaEntity> findByProductId(@Param("productId") Long productId);
}

package dev.sebastian.sanrafael.adapter.repository.jpa;

import dev.sebastian.sanrafael.adapter.repository.jpa.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JPARepositoryProduct extends JpaRepository<ProductJpaEntity, Long> {
    Optional<ProductJpaEntity> findByProductName(String productName);
}

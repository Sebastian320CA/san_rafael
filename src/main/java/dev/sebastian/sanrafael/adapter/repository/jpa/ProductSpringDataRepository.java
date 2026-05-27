package dev.sebastian.sanrafael.adapter.repository.jpa;

import dev.sebastian.sanrafael.adapter.repository.jpa.entity.ProductJpaEntity;
import dev.sebastian.sanrafael.core.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductSpringDataRepository extends JpaRepository<ProductJpaEntity, Long> {

    Optional<ProductJpaEntity> findByProductName(String productName);
}

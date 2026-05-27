package dev.sebastian.sanrafael.adapter.repository.jpa;

import dev.sebastian.sanrafael.adapter.repository.jpa.entity.EventJpaEntity;
import dev.sebastian.sanrafael.adapter.repository.jpa.entity.ProductJpaEntity;
import dev.sebastian.sanrafael.adapter.repository.jpa.entity.ReportJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/*
* Este componente es el encargado de la conexión nativa con Spring Data a PostgreSQL para todas las entidades JPA
s*/
@Repository
public interface JPARepository extends JpaRepository<ReportJpaEntity, Long> {
List<ReportJpaEntity> findByProductId(Long productId);

}

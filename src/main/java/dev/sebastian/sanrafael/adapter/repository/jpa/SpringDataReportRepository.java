package dev.sebastian.sanrafael.adapter.repository.jpa;

import dev.sebastian.sanrafael.adapter.repository.jpa.entity.ProductJpaEntity;
import dev.sebastian.sanrafael.adapter.repository.jpa.entity.ReportJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/*
* Este componente es el encargado de la conexión nativa con Spring Data a PostgreSQL para todas las entidades JPA
*/
@Repository
public interface SpringDataReportRepository extends JpaRepository<ReportJpaEntity, Long> {
    /*
    * En este caso no es necesario colocar nada, ya que Spring hereda automaticamente todos los
    * metodos necesarios para su correcto funcionamiento.
    */

    //Optional<ProductJpaEntity> findByName(String name);
}

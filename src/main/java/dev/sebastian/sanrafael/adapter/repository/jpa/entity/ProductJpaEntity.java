package dev.sebastian.sanrafael.adapter.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
/*
 * Entity en este caso nos indica que representa la tabla events en la base de datos que creamos.
 */
@Entity
/*
 * Este es el nombre de la tabla en la base de datos.
 */
@Table(name = "PRODUCTS")
public class ProductJpaEntity {
    /*
     * Id, es el identificador univo del evente la cual es la llave primaria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    @ToString.Include
    private long id;

    @Column(name = "productName", length = 60, nullable = false)
    @ToString.Include
    private String productName;

    @Column(name = "health_url", nullable = false)
    private String healthUrl;
    /*
    * Esta es una relación uno a muchos ya que un producto puede tener muchos reportes asociados y mappedBy
    * nos indica que la relación es controlada directamente por el atributo, lo cual es que ProductEntity no es el
    * dueño de la relación, solamente lo esta referenciando.
    * */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    /*
    * Lista de reportes asociadas con el producto.
    * */
    private List<ReportJpaEntity> reports;
}

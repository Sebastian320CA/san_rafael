package dev.sebastian.sanrafael.core.entity;

import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

/*
 * Entity en este caso nos indica que representa la tabla events en la base de datos que creamos.
 */
@Entity
/*
 * Este es el nombre de la tabla en la base de datos.
 */
@Table(name = "REPORTS")
public class ReportEntity {
    /*
     * Id, es el identificador univo del evente la cual es la llave primaria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_report")
    @ToString.Include
    private long id;

    @Column(nullable = false)
    private LocalDate date;

    /*
    * Porcentaje de uso de la memoria Ram
    */
    @Column(name = "ram_usage_percentage")
    private int ramUsagePercentage;

    /*
    * Porcentaje de uso en el disco de almacenamiento.
    */
    @Column(name = "disk_usage_percentage")
    private int diskUsagePercentage;


    /*
    * Esta es una relación de muchos a uno relacionada con la tabla ProductEntity.
    * Significa que muchos reportes pertenecen a un solo producto
    */
    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    @ToString.Include
    private ProductEntity product;


    /*
    * Relación de uno a muchos con la tabla EventEntity.
    * Un reporte puede tener muchos eventos asociados.
    * mappedBy nos indica que la relación la controla el campo report de la tabla EventEntity.{
    * la función cascade significa que todas las operaciones se usen como actualizar, borrar.
    * orPhanRemoval es para eliminar a los datos hijos que no tengan un padre.
    * */
    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Include
    private List<EventEntity> events;
}

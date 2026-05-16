package dev.sebastian.sanrafael.adapter.repository.jpa.entity;


import dev.sebastian.sanrafael.core.constant.Condition;
import jakarta.persistence.*;
import lombok.ToString;


/*
* Entity en este caso nos indica que representa la tabla events en la base de datos que creamos.
*/
@Entity
/*
* Este es el nombre de la tabla en la base de datos.
*/
@Table(name = "EVENTS")
public class EventJpaEntity {

    /*
    * Id, es el identificador univo del evente la cual es la llave primaria.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event")
    @ToString.Include
    private Long id;

    @Column(name = "description", length = 200 ,nullable = false)
    @ToString.Include
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition", nullable = false)
    @ToString.Include
    private Condition condition;

    /*
    * En este caso, muchos eventos pertenecen a un solo reporte, por lo tanto
    * se implementa llave foranea en la tabla events para indicar la conexión con reports.
    * */
    @ManyToOne
    @JoinColumn(name = "id_report", nullable = false)
    private ReportJpaEntity report;
}

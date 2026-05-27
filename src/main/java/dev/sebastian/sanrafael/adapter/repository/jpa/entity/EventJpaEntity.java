package dev.sebastian.sanrafael.adapter.repository.jpa.entity;

import dev.sebastian.sanrafael.core.constant.Condition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "EVENTS")
public class EventJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event")
    @ToString.Include
    private Long id;

    @Column(name = "description", length = 200, nullable = false)
    @ToString.Include
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition", nullable = false)
    @ToString.Include
    private Condition condition; // Usa directamente el Enum de dominio gracias a @Enumerated

    @ManyToOne
    @JoinColumn(name = "id_report", nullable = false)
    private ReportJpaEntity report;
}

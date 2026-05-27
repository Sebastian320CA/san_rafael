package dev.sebastian.sanrafael.adapter.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "REPORTS")
public class ReportJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_report")
    @ToString.Include
    private long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "ram_usage_percentage")
    private int ramUsagePercentage;

    @Column(name = "disk_usage_percentage")
    private int diskUsagePercentage;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    @ToString.Include
    private ProductJpaEntity product;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventJpaEntity> events;
}

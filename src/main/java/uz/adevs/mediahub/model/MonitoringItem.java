package uz.adevs.mediahub.model;

import jakarta.persistence.*;
import lombok.*;
import uz.adevs.mediahub.constants.MonitoringDaraja;
import uz.adevs.mediahub.constants.MonitoringStatus;
import java.time.LocalDateTime;

@Entity
@Table(name = "monitoring_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MonitoringItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    @Builder.Default
    private String turi = "TANQIDIY"; // TANQIDIY, NHH

    @Column(columnDefinition = "TEXT")
    private String link;

    @Column(nullable = false, length = 50)
    private String platforma;

    @Column(name = "muallif_manba", length = 255)
    private String muallifManba;

    @Column(nullable = false, length = 500)
    private String mavzu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hudud_tuzilma_id")
    private Organization hududTuzilma;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MonitoringDaraja daraja;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masul_org_id")
    private Organization masulOrg;

    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private MonitoringStatus status = MonitoringStatus.YANGI;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

package uz.adevs.mediahub.model;

import jakarta.persistence.*;
import lombok.*;
import uz.adevs.mediahub.constants.MaterialStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "coverage_materials")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CoverageMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String nomi;

    @Column(name = "material_turi", nullable = false, length = 100)
    private String materialTuri;

    @Column(nullable = false, length = 50)
    private String platforma;

    @Column(columnDefinition = "TEXT")
    private String havola;

    @Column(nullable = false)
    private LocalDate sana;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hudud_tuzilma_id")
    private Organization hududTuzilma;

    @Column(length = 255)
    private String mavzu;

    @Column(name = "korishlar_soni")
    @Builder.Default
    private Long korishlarSoni = 0L;

    @Column(columnDefinition = "TEXT")
    private String izoh;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private MaterialStatus status = MaterialStatus.TASDIQLASHDA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tasdiqlagan_id")
    private User tasdiqlagan;

    @Column(name = "tasdiqlagan_vaqt")
    private LocalDateTime tasdiqlaganVaqt;

    @Column(name = "qaytarish_sababi", columnDefinition = "TEXT")
    private String qaytarishSababi;

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

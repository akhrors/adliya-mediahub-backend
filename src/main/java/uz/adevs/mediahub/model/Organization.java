package uz.adevs.mediahub.model;

import jakarta.persistence.*;
import lombok.*;
import uz.adevs.mediahub.constants.OrgType;
import java.time.LocalDateTime;

@Entity
@Table(name = "organizations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private OrgType turi;

    @Column(length = 100)
    private String hudud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yuqori_tashkilot_id")
    private Organization yuqoriTashkilot;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

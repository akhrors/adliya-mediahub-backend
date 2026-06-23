package uz.adevs.mediahub.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "media_request_experts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MediaRequestExpert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private MediaRequest mediaRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_id", nullable = false)
    private ExpertProfile expert;

    @Column(nullable = false, length = 30)
    @Builder.Default
    private String status = "TAKLIF_QILINDI"; // TAKLIF_QILINDI, TASDIQLANDI, RAD_ETILDI

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by")
    private User assignedBy;

    @Column(name = "assigned_at", nullable = false, updatable = false)
    private LocalDateTime assignedAt;

    @PrePersist
    protected void onCreate() {
        assignedAt = LocalDateTime.now();
    }
}

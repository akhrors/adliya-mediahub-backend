package uz.adevs.mediahub.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "expert_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ExpertProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String yonalishlar; // JSON array

    @Column(name = "til_bilish", columnDefinition = "TEXT")
    private String tilBilish; // JSON

    @Column(columnDefinition = "TEXT")
    private String tajriba; // JSON array

    @Column(precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal reyting = BigDecimal.ZERO;

    @Column(nullable = false, length = 30)
    @Builder.Default
    private String status = "FAOL"; // FAOL, VAQTINCHA_MAVJUD_EMAS, TAVSIYA_ETILMAYDI

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

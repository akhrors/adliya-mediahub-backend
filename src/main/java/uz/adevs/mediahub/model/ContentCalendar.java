package uz.adevs.mediahub.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "content_calendar")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ContentCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kontent_nomi", nullable = false, length = 500)
    private String kontentNomi;

    @Column(nullable = false, length = 50)
    private String format; // POST, VIDEO, REELS, INFOGRAFIKA, INTERVYU, PRESS_RELIZ

    @Column(nullable = false, length = 50)
    private String platforma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masul_id")
    private User masul;

    @Column(nullable = false)
    private LocalDate muddat;

    @Column(name = "elon_vaqti")
    private LocalDateTime elonVaqti;

    @Column(nullable = false, length = 30)
    @Builder.Default
    private String status = "REJADA";

    @Column(columnDefinition = "TEXT")
    private String izoh;

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

package uz.adevs.mediahub.model;

import jakarta.persistence.*;
import lombok.*;
import uz.adevs.mediahub.constants.EventStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String nomi;

    @Column(nullable = false, length = 100)
    private String turi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tashkilotchi_id", nullable = false)
    private Organization tashkilotchi;

    @Column(length = 100)
    private String hudud;

    @Column(nullable = false)
    private LocalDateTime sana;

    @Column(nullable = false)
    private String joy;

    @Column(columnDefinition = "TEXT")
    private String ishtirokchilar;

    @Column(name = "kun_tartibi", columnDefinition = "TEXT")
    private String kunTartibi;

    @Column(name = "press_reliz", columnDefinition = "TEXT")
    private String pressReliz;

    @Column(name = "foto_kerak")
    @Builder.Default
    private Boolean fotoKerak = false;

    @Column(name = "video_kerak")
    @Builder.Default
    private Boolean videoKerak = false;

    @Column(name = "oav_taklif")
    @Builder.Default
    private Boolean oavTaklif = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masul_id", nullable = false)
    private User masul;

    @Column(columnDefinition = "TEXT")
    private String izoh;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private EventStatus status = EventStatus.QORALAMA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<EventChecklist> checklists = new ArrayList<>();

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

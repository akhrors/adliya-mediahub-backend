package uz.adevs.mediahub.model;

import jakarta.persistence.*;
import lombok.*;
import uz.adevs.mediahub.constants.MediaRequestStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "media_requests")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MediaRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oav_id", nullable = false)
    private MediaOutlet oav;

    @Column(nullable = false, length = 500)
    private String mavzu;

    @Column(nullable = false, length = 100)
    private String yonalish;

    @Column(nullable = false)
    private LocalDateTime sana;

    @Column(nullable = false)
    private String joy;

    @Column(name = "kerakli_mutaxassislar_soni", nullable = false)
    @Builder.Default
    private Integer kerakliMutaxassislarSoni = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarkibiy_tuzilma_id", nullable = false)
    private Organization tarkibiyTuzilma;

    @Column(name = "taymer_muddat_minut", nullable = false)
    private Integer taymerMuddatMinut;

    private LocalDateTime deadline;

    @Column(columnDefinition = "TEXT")
    private String izoh;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private MediaRequestStatus status = MediaRequestStatus.YANGI;

    @Column(name = "yakuniy_havola", columnDefinition = "TEXT")
    private String yakuniyHavola;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "mediaRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MediaRequestExpert> experts = new ArrayList<>();

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

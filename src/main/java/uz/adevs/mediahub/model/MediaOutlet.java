package uz.adevs.mediahub.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "media_outlets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MediaOutlet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomi;

    @Column(nullable = false, length = 50)
    private String turi; // TV, RADIO, INTERNET_NASHRI, GAZETA, JURNAL, BLOGER, BOSHQA

    @Column(length = 100)
    private String aloqa;

    private String email;

    @Column(columnDefinition = "TEXT")
    private String izoh;

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

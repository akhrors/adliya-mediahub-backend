package uz.adevs.mediahub.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_checklists")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EventChecklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "is_done", nullable = false)
    @Builder.Default
    private Boolean isDone = false;

    @Column(name = "done_at")
    private LocalDateTime doneAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "done_by")
    private User doneBy;
}

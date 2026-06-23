package uz.adevs.mediahub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.adevs.mediahub.model.MediaRequest;
import uz.adevs.mediahub.constants.MediaRequestStatus;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MediaRequestRepository extends JpaRepository<MediaRequest, Long>, JpaSpecificationExecutor<MediaRequest> {
    List<MediaRequest> findByStatus(MediaRequestStatus status);
    List<MediaRequest> findByTarkibiyTuzilmaId(Long orgId);
    List<MediaRequest> findByCreatedById(Long userId);
    @Query("SELECT mr FROM MediaRequest mr WHERE mr.deadline < :now AND mr.status NOT IN ('YAKUNLANDI', 'RAD_ETILDI')")
    List<MediaRequest> findOverdue(LocalDateTime now);
}

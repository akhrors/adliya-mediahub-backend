package uz.adevs.mediahub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.adevs.mediahub.model.Event;
import uz.adevs.mediahub.constants.EventStatus;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    List<Event> findByStatus(EventStatus status);
    List<Event> findByTashkilotchiId(Long orgId);
    List<Event> findBySanaBetween(LocalDateTime from, LocalDateTime to);
}

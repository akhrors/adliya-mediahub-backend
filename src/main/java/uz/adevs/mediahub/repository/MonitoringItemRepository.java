package uz.adevs.mediahub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.adevs.mediahub.model.MonitoringItem;
import uz.adevs.mediahub.constants.MonitoringStatus;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MonitoringItemRepository extends JpaRepository<MonitoringItem, Long>, JpaSpecificationExecutor<MonitoringItem> {
    List<MonitoringItem> findByStatus(MonitoringStatus status);
    List<MonitoringItem> findByTuri(String turi);
    @Query("SELECT m FROM MonitoringItem m WHERE m.deadline < :now AND m.status NOT IN ('YAKUNLANDI')")
    List<MonitoringItem> findOverdue(LocalDateTime now);
}

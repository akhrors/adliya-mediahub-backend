package uz.adevs.mediahub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.adevs.mediahub.model.ContentCalendar;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContentCalendarRepository extends JpaRepository<ContentCalendar, Long>, JpaSpecificationExecutor<ContentCalendar> {
    List<ContentCalendar> findByMuddatBetween(LocalDate from, LocalDate to);
    List<ContentCalendar> findByStatus(String status);
    List<ContentCalendar> findByMasulId(Long userId);
}

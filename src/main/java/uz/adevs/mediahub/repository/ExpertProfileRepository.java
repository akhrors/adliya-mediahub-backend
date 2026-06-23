package uz.adevs.mediahub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.adevs.mediahub.model.ExpertProfile;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertProfileRepository extends JpaRepository<ExpertProfile, Long> {
    Optional<ExpertProfile> findByUserId(Long userId);
    List<ExpertProfile> findByStatus(String status);
    List<ExpertProfile> findByYonalishlarContaining(String yonalish);
}

package uz.adevs.mediahub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.adevs.mediahub.model.RatingRule;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRuleRepository extends JpaRepository<RatingRule, Long> {
    Optional<RatingRule> findByKorsatkich(String korsatkich);
    List<RatingRule> findByIsActiveTrue();
}

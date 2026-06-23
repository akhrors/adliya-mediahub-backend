package uz.adevs.mediahub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.adevs.mediahub.model.RatingRule;
import uz.adevs.mediahub.repository.RatingRuleRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRuleRepository ratingRuleRepository;

    public void addPoints(String platforma, Long userId) {
        String korsatkich = mapPlatformaToKorsatkich(platforma);
        if (korsatkich == null) return;
        ratingRuleRepository.findByKorsatkich(korsatkich).ifPresent(rule -> {
            // Rating transaction logic — extend as needed
        });
    }

    public List<RatingRule> getRules() {
        return ratingRuleRepository.findAll();
    }

    @Transactional
    public RatingRule updateRule(Long id, Integer ball) {
        RatingRule rule = ratingRuleRepository.findById(id)
                .orElseThrow(() -> new uz.adevs.mediahub.exception.ResourceNotFoundException("Qoida topilmadi"));
        rule.setBall(ball);
        rule.setUpdatedAt(java.time.LocalDateTime.now());
        return ratingRuleRepository.save(rule);
    }

    private String mapPlatformaToKorsatkich(String platforma) {
        return switch (platforma.toUpperCase()) {
            case "TV" -> "TV_CHIQISH";
            case "RADIO" -> "RADIO_CHIQISH";
            case "TELEGRAM" -> "TELEGRAM_POST";
            case "INSTAGRAM" -> "INSTAGRAM_POST";
            case "YOUTUBE" -> "YOUTUBE_VIDEO";
            case "GAZETA", "JURNAL", "INTERNET_NASHRI" -> "ELEKTRON_OAV";
            default -> null;
        };
    }
}

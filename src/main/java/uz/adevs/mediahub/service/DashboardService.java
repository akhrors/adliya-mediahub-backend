package uz.adevs.mediahub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.adevs.mediahub.constants.MediaRequestStatus;
import uz.adevs.mediahub.constants.MaterialStatus;
import uz.adevs.mediahub.repository.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final EventRepository eventRepository;
    private final MediaRequestRepository mediaRequestRepository;
    private final CoverageMaterialRepository coverageMaterialRepository;
    private final MonitoringItemRepository monitoringItemRepository;

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        LocalDateTime todayStart = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime todayEnd = todayStart.plusDays(1);

        stats.put("todayEvents", eventRepository.findBySanaBetween(todayStart, todayEnd).size());
        stats.put("weeklyRequests", mediaRequestRepository
                .findAll().stream()
                .filter(r -> r.getCreatedAt().isAfter(LocalDateTime.now().minusWeeks(1))).count());
        stats.put("pendingMaterials", coverageMaterialRepository.findByStatus(MaterialStatus.TASDIQLASHDA).size());
        stats.put("criticalItems", monitoringItemRepository.findByTuri("TANQIDIY").size());
        stats.put("overdue", mediaRequestRepository.findOverdue(LocalDateTime.now()).size());
        return stats;
    }
}

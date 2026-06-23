package uz.adevs.mediahub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.adevs.mediahub.constants.MonitoringStatus;
import uz.adevs.mediahub.exception.ResourceNotFoundException;
import uz.adevs.mediahub.model.*;
import uz.adevs.mediahub.repository.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitoringService {

    private final MonitoringItemRepository monitoringItemRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public MonitoringItem create(uz.adevs.mediahub.dto.request.MonitoringCreateRequest req, Long creatorId) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi"));
        Organization org = req.getHududTuzilmaId() != null
                ? organizationRepository.findById(req.getHududTuzilmaId()).orElse(null) : null;
        Organization masulOrg = req.getMasulOrgId() != null
                ? organizationRepository.findById(req.getMasulOrgId()).orElse(null) : null;

        MonitoringItem item = MonitoringItem.builder()
                .turi(req.getTuri()).link(req.getLink()).platforma(req.getPlatforma())
                .muallifManba(req.getMuallifManba()).mavzu(req.getMavzu())
                .hududTuzilma(org).daraja(req.getDaraja()).masulOrg(masulOrg)
                .deadline(req.getDeadline()).status(MonitoringStatus.YANGI).createdBy(creator).build();

        MonitoringItem saved = monitoringItemRepository.save(item);
        if (masulOrg != null) {
            notificationService.notifyOrg(masulOrg.getId(), "TANQIDIY_MATERIAL",
                    "Tanqidiy material", "Mavzu: " + req.getMavzu(), "monitoring", saved.getId());
        }
        return saved;
    }

    @Transactional
    public MonitoringItem updateStatus(Long id, MonitoringStatus status) {
        MonitoringItem item = findById(id);
        item.setStatus(status);
        return monitoringItemRepository.save(item);
    }

    public List<MonitoringItem> getAll() { return monitoringItemRepository.findAll(); }
    public MonitoringItem findById(Long id) {
        return monitoringItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monitoring item topilmadi: " + id));
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void checkOverdue() {
        monitoringItemRepository.findOverdue(LocalDateTime.now()).forEach(item -> {
            item.setStatus(MonitoringStatus.KECHIKDI);
            monitoringItemRepository.save(item);
        });
    }
}

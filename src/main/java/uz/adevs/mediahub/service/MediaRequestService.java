package uz.adevs.mediahub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.adevs.mediahub.constants.MediaRequestStatus;
import uz.adevs.mediahub.dto.request.MediaRequestCreateRequest;
import uz.adevs.mediahub.exception.ResourceNotFoundException;
import uz.adevs.mediahub.model.*;
import uz.adevs.mediahub.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaRequestService {

    private final MediaRequestRepository mediaRequestRepository;
    private final MediaOutletRepository mediaOutletRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final ExpertProfileRepository expertProfileRepository;
    private final NotificationService notificationService;
    private final AuditLogService auditLogService;

    @Transactional
    public MediaRequest create(MediaRequestCreateRequest req, Long creatorId) {
        MediaOutlet oav = mediaOutletRepository.findById(req.getOavId())
                .orElseThrow(() -> new ResourceNotFoundException("OAV topilmadi"));
        Organization tuzilma = organizationRepository.findById(req.getTarkibiyTuzilmaId())
                .orElseThrow(() -> new ResourceNotFoundException("Tuzilma topilmadi"));
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi"));

        LocalDateTime deadline = LocalDateTime.now().plusMinutes(req.getTaymerMuddatMinut());

        MediaRequest request = MediaRequest.builder()
                .oav(oav).mavzu(req.getMavzu()).yonalish(req.getYonalish())
                .sana(req.getSana()).joy(req.getJoy())
                .kerakliMutaxassislarSoni(req.getKerakliMutaxassislarSoni())
                .tarkibiyTuzilma(tuzilma).taymerMuddatMinut(req.getTaymerMuddatMinut())
                .deadline(deadline).izoh(req.getIzoh())
                .status(MediaRequestStatus.YUBORILDI).createdBy(creator)
                .build();

        MediaRequest saved = mediaRequestRepository.save(request);
        notificationService.notifyOrg(tuzilma.getId(), "YANGI_OAV_SOROVI",
                "Yangi OAV so'rovi", "Mavzu: " + req.getMavzu(), "media_requests", saved.getId());
        auditLogService.log(creatorId, "CREATE", "media_requests", saved.getId(), null, saved.getMavzu());
        return saved;
    }

    @Transactional
    public MediaRequest assignExpert(Long requestId, Long expertId, Long assignedById) {
        MediaRequest request = findById(requestId);
        ExpertProfile expert = expertProfileRepository.findById(expertId)
                .orElseThrow(() -> new ResourceNotFoundException("Ekspert topilmadi"));
        User assignedBy = userRepository.findById(assignedById)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi"));

        MediaRequestExpert assignment = MediaRequestExpert.builder()
                .mediaRequest(request).expert(expert)
                .status("TAKLIF_QILINDI").assignedBy(assignedBy).build();
        request.getExperts().add(assignment);
        request.setStatus(MediaRequestStatus.EKSPERT_BIRIKTIRILDI);
        return mediaRequestRepository.save(request);
    }

    @Transactional
    public MediaRequest approveExpert(Long requestId, Long assignmentId) {
        MediaRequest request = findById(requestId);
        request.getExperts().stream()
                .filter(e -> e.getId().equals(assignmentId))
                .findFirst().ifPresent(e -> e.setStatus("TASDIQLANDI"));
        request.setStatus(MediaRequestStatus.TASDIQLANDI);
        return mediaRequestRepository.save(request);
    }

    @Transactional
    public MediaRequest complete(Long requestId, String havola) {
        MediaRequest request = findById(requestId);
        request.setYakuniyHavola(havola);
        request.setStatus(MediaRequestStatus.YAKUNLANDI);
        return mediaRequestRepository.save(request);
    }

    @Transactional
    public MediaRequest updateStatus(Long requestId, MediaRequestStatus status) {
        MediaRequest request = findById(requestId);
        request.setStatus(status);
        return mediaRequestRepository.save(request);
    }

    public List<MediaRequest> getAll() { return mediaRequestRepository.findAll(); }
    public List<MediaRequest> getByStatus(MediaRequestStatus status) { return mediaRequestRepository.findByStatus(status); }
    public MediaRequest findById(Long id) {
        return mediaRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("So'rov topilmadi: " + id));
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void checkOverdue() {
        List<MediaRequest> overdue = mediaRequestRepository.findOverdue(LocalDateTime.now());
        overdue.forEach(r -> {
            r.setStatus(MediaRequestStatus.KECHIKDI);
            mediaRequestRepository.save(r);
        });
    }
}

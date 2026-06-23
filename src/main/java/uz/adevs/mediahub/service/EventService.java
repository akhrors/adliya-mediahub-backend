package uz.adevs.mediahub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.adevs.mediahub.constants.EventStatus;
import uz.adevs.mediahub.dto.request.EventCreateRequest;
import uz.adevs.mediahub.exception.ResourceNotFoundException;
import uz.adevs.mediahub.model.*;
import uz.adevs.mediahub.repository.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final AuditLogService auditLogService;

    private static final List<String> DEFAULT_CHECKLIST = List.of(
            "Press-reliz tayyorlandi","Foto olindi","Video olindi","Intervyu olindi",
            "Telegram posti joylashtirildi","Instagram posti joylashtirildi",
            "Veb-saytga joylashtirildi","OAVga yuborildi","Yakuniy havolalar kiritildi"
    );

    @Transactional
    public Event create(EventCreateRequest req, Long creatorId) {
        Organization tashkilotchi = organizationRepository.findById(req.getTashkilotchiId())
                .orElseThrow(() -> new ResourceNotFoundException("Tashkilotchi topilmadi"));
        User masul = userRepository.findById(req.getMasulId())
                .orElseThrow(() -> new ResourceNotFoundException("Mas'ul topilmadi"));
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Creator topilmadi"));

        Event event = Event.builder()
                .nomi(req.getNomi()).turi(req.getTuri()).tashkilotchi(tashkilotchi)
                .hudud(req.getHudud()).sana(req.getSana()).joy(req.getJoy())
                .ishtirokchilar(req.getIshtirokchilar()).kunTartibi(req.getKunTartibi())
                .pressReliz(req.getPressReliz()).fotoKerak(req.getFotoKerak())
                .videoKerak(req.getVideoKerak()).oavTaklif(req.getOavTaklif())
                .masul(masul).izoh(req.getIzoh())
                .status(EventStatus.QORALAMA).createdBy(creator).build();

        DEFAULT_CHECKLIST.forEach(item ->
            event.getChecklists().add(EventChecklist.builder().event(event).itemName(item).isDone(false).build()));

        Event saved = eventRepository.save(event);
        auditLogService.log(creatorId, "CREATE", "events", saved.getId(), null, saved.getNomi());
        return saved;
    }

    @Transactional
    public Event submit(Long id) {
        Event event = findById(id);
        event.setStatus(EventStatus.YUBORILDI);
        eventRepository.save(event);
        notificationService.notifyOrg(event.getTashkilotchi().getId(), "TADBIR_YUBORILDI",
                "Yangi tadbir", "Tadbir tasdiqlash uchun yuborildi: " + event.getNomi(), "events", id);
        return event;
    }

    @Transactional
    public Event updateStatus(Long id, EventStatus status) {
        Event event = findById(id);
        event.setStatus(status);
        return eventRepository.save(event);
    }

    @Transactional
    public EventChecklist updateChecklist(Long eventId, Long itemId, boolean isDone, Long userId) {
        Event event = findById(eventId);
        EventChecklist item = event.getChecklists().stream()
                .filter(c -> c.getId().equals(itemId)).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Checklist item topilmadi"));
        item.setIsDone(isDone);
        if (isDone) {
            item.setDoneAt(java.time.LocalDateTime.now());
            userRepository.findById(userId).ifPresent(item::setDoneBy);
        }
        eventRepository.save(event);
        return item;
    }

    public List<Event> getAll() { return eventRepository.findAll(); }
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tadbir topilmadi: " + id));
    }
}

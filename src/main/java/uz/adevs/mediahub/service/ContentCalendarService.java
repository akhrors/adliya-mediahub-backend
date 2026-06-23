package uz.adevs.mediahub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.adevs.mediahub.exception.ResourceNotFoundException;
import uz.adevs.mediahub.model.ContentCalendar;
import uz.adevs.mediahub.model.User;
import uz.adevs.mediahub.repository.ContentCalendarRepository;
import uz.adevs.mediahub.repository.UserRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentCalendarService {
    private final ContentCalendarRepository contentCalendarRepository;
    private final UserRepository userRepository;

    public List<ContentCalendar> getAll() { return contentCalendarRepository.findAll(); }

    @Transactional
    public ContentCalendar create(uz.adevs.mediahub.dto.request.ContentCalendarCreateRequest req, Long creatorId) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi"));
        User masul = req.getMasulId() != null
                ? userRepository.findById(req.getMasulId()).orElse(null) : null;
        ContentCalendar item = ContentCalendar.builder()
                .kontentNomi(req.getKontentNomi()).format(req.getFormat())
                .platforma(req.getPlatforma()).masul(masul).muddat(req.getMuddat())
                .elonVaqti(req.getElonVaqti()).status("REJADA").izoh(req.getIzoh())
                .createdBy(creator).build();
        return contentCalendarRepository.save(item);
    }

    @Transactional
    public ContentCalendar updateStatus(Long id, String status) {
        ContentCalendar item = contentCalendarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kontent topilmadi"));
        item.setStatus(status);
        return contentCalendarRepository.save(item);
    }
}

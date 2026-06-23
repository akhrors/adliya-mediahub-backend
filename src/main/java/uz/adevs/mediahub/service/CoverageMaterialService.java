package uz.adevs.mediahub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.adevs.mediahub.constants.MaterialStatus;
import uz.adevs.mediahub.exception.ResourceNotFoundException;
import uz.adevs.mediahub.model.CoverageMaterial;
import uz.adevs.mediahub.model.Organization;
import uz.adevs.mediahub.model.User;
import uz.adevs.mediahub.repository.CoverageMaterialRepository;
import uz.adevs.mediahub.repository.OrganizationRepository;
import uz.adevs.mediahub.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoverageMaterialService {

    private final CoverageMaterialRepository coverageMaterialRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final RatingService ratingService;
    private final AuditLogService auditLogService;

    @Transactional
    public CoverageMaterial create(uz.adevs.mediahub.dto.request.CoverageMaterialCreateRequest req, Long creatorId) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi"));
        Organization org = req.getHududTuzilmaId() != null
                ? organizationRepository.findById(req.getHududTuzilmaId()).orElse(null) : null;

        CoverageMaterial material = CoverageMaterial.builder()
                .nomi(req.getNomi()).materialTuri(req.getMaterialTuri())
                .platforma(req.getPlatforma()).havola(req.getHavola())
                .sana(req.getSana()).hududTuzilma(org).mavzu(req.getMavzu())
                .korishlarSoni(req.getKorishlarSoni() != null ? req.getKorishlarSoni() : 0L)
                .izoh(req.getIzoh()).status(MaterialStatus.TASDIQLASHDA).createdBy(creator).build();

        CoverageMaterial saved = coverageMaterialRepository.save(material);
        auditLogService.log(creatorId, "CREATE", "coverage_materials", saved.getId(), null, saved.getNomi());
        return saved;
    }

    @Transactional
    public CoverageMaterial approve(Long id, Long approverId) {
        CoverageMaterial material = findById(id);
        User approver = userRepository.findById(approverId)
                .orElseThrow(() -> new ResourceNotFoundException("Approver topilmadi"));
        material.setStatus(MaterialStatus.TASDIQLANDI);
        material.setTasdiqlagan(approver);
        material.setTasdiqlaganVaqt(LocalDateTime.now());
        CoverageMaterial saved = coverageMaterialRepository.save(material);
        ratingService.addPoints(material.getPlatforma(), material.getCreatedBy().getId());
        return saved;
    }

    @Transactional
    public CoverageMaterial reject(Long id, String reason) {
        CoverageMaterial material = findById(id);
        material.setStatus(MaterialStatus.QAYTARILDI);
        material.setQaytarishSababi(reason);
        return coverageMaterialRepository.save(material);
    }

    public List<CoverageMaterial> getAll() { return coverageMaterialRepository.findAll(); }
    public CoverageMaterial findById(Long id) {
        return coverageMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material topilmadi: " + id));
    }
}

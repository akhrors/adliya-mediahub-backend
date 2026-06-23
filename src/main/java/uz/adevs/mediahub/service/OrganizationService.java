package uz.adevs.mediahub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.adevs.mediahub.exception.ResourceNotFoundException;
import uz.adevs.mediahub.model.Organization;
import uz.adevs.mediahub.repository.OrganizationRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public List<Organization> getAll() { return organizationRepository.findByIsActiveTrue(); }
    public Organization findById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tashkilot topilmadi: " + id));
    }
    public Organization create(Organization org) { return organizationRepository.save(org); }
}

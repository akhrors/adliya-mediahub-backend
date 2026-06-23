package uz.adevs.mediahub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.adevs.mediahub.model.Organization;
import uz.adevs.mediahub.constants.OrgType;
import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    List<Organization> findByTuri(OrgType turi);
    List<Organization> findByYuqoriTashkilotId(Long parentId);
    List<Organization> findByIsActiveTrue();
}

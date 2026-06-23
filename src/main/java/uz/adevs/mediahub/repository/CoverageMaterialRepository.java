package uz.adevs.mediahub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.adevs.mediahub.model.CoverageMaterial;
import uz.adevs.mediahub.constants.MaterialStatus;
import java.util.List;

@Repository
public interface CoverageMaterialRepository extends JpaRepository<CoverageMaterial, Long>, JpaSpecificationExecutor<CoverageMaterial> {
    List<CoverageMaterial> findByStatus(MaterialStatus status);
    List<CoverageMaterial> findByHududTuzilmaId(Long orgId);
    List<CoverageMaterial> findByPlatforma(String platforma);
}

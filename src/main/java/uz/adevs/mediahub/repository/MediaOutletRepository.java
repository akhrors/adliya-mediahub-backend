package uz.adevs.mediahub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.adevs.mediahub.model.MediaOutlet;
import java.util.List;

@Repository
public interface MediaOutletRepository extends JpaRepository<MediaOutlet, Long> {
    List<MediaOutlet> findByIsActiveTrue();
    List<MediaOutlet> findByTuri(String turi);
}

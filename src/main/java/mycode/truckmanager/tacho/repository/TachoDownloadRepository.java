package mycode.truckmanager.tacho.repository;

import mycode.truckmanager.tacho.models.TachoDownload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TachoDownloadRepository extends JpaRepository<TachoDownload, Long> {
    List<TachoDownload> findAllByOrderByNextDownloadDateAsc();
    boolean existsByLicensePlateIgnoreCase(String licensePlate);
    boolean existsByLicensePlateIgnoreCaseAndIdNot(String licensePlate, Long id);
}
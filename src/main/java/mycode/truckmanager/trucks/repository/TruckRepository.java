package mycode.truckmanager.trucks.repository;

import mycode.truckmanager.trucks.models.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Long> {
    Optional<Truck> findByLicensePlateIgnoreCase(String licensePlate);
    boolean existsByLicensePlateIgnoreCase(String licensePlate);
}
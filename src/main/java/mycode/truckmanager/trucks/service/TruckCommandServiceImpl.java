package mycode.truckmanager.trucks.service;

import lombok.RequiredArgsConstructor;
import mycode.truckmanager.trucks.dtos.TruckRequestDto;
import mycode.truckmanager.trucks.dtos.TruckResponseDto;
import mycode.truckmanager.trucks.mappers.TruckMapper;
import mycode.truckmanager.trucks.models.Truck;
import mycode.truckmanager.trucks.repository.TruckRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TruckCommandServiceImpl implements TruckCommandService {

    private final TruckRepository truckRepository;
    private final TruckMapper truckMapper;

    @Override
    public TruckResponseDto createTruck(TruckRequestDto dto) {
        if (truckRepository.existsByLicensePlateIgnoreCase(dto.licensePlate().trim())) {
            throw new IllegalArgumentException("Există deja un camion cu numărul de înmatriculare: " + dto.licensePlate());
        }
        Truck truck = truckMapper.toEntity(dto);
        return truckMapper.toResponseDto(truckRepository.save(truck));
    }

    @Override
    public TruckResponseDto updateTruck(Long id, TruckRequestDto dto) {
        Truck existingTruck = truckRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Camionul cu ID-ul " + id + " nu a fost găsit."));

        if (!existingTruck.getLicensePlate().equalsIgnoreCase(dto.licensePlate().trim())
                && truckRepository.existsByLicensePlateIgnoreCase(dto.licensePlate().trim())) {
            throw new IllegalArgumentException("Numărul de înmatriculare " + dto.licensePlate() + " este deja folosit de alt camion.");
        }

        existingTruck.setLicensePlate(dto.licensePlate().trim().toUpperCase());
        existingTruck.setMake(dto.make().trim());
        existingTruck.setModel(dto.model().trim());
        existingTruck.setItpExpiryDate(dto.itpExpiryDate());
        existingTruck.setInsuranceExpiryDate(dto.insuranceExpiryDate());
        existingTruck.setTachoExpiryDate(dto.tachoExpiryDate());

        return truckMapper.toResponseDto(truckRepository.save(existingTruck));
    }

    @Override
    public void deleteTruck(Long id) {
        if (!truckRepository.existsById(id)) {
            throw new IllegalArgumentException("Camionul cu ID-ul " + id + " nu a fost găsit.");
        }
        truckRepository.deleteById(id);
    }
}
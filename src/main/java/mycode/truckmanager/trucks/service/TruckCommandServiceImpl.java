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
        String cleanPlate = dto.licensePlate().trim().toUpperCase();
        if (truckRepository.existsByLicensePlateIgnoreCase(cleanPlate)) {
            throw new IllegalArgumentException("Există deja un camion cu numărul: " + cleanPlate);
        }
        Truck truck = truckMapper.toEntity(dto);
        truck.setLicensePlate(cleanPlate);
        return truckMapper.toResponseDto(truckRepository.save(truck));
    }

    @Override
    public TruckResponseDto updateTruck(Long id, TruckRequestDto dto) {
        Truck existingTruck = truckRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Camionul cu ID-ul " + id + " nu a fost găsit."));

        String cleanPlate = dto.licensePlate().trim().toUpperCase();

        if (truckRepository.existsByLicensePlateIgnoreCaseAndIdNot(cleanPlate, id)) {
            throw new IllegalArgumentException("Numărul de înmatriculare " + cleanPlate + " este deja folosit de alt camion.");
        }

        existingTruck.setLicensePlate(cleanPlate);
        existingTruck.setMake(dto.make().trim());
        existingTruck.setModel(dto.model().trim());
        existingTruck.setVgpExpiryDate(dto.vgpExpiryDate());
        existingTruck.setItvExpiryDate(dto.itvExpiryDate());
        existingTruck.setLimitVExpiryDate(dto.limitVExpiryDate());
        existingTruck.setTGrafoExpiryDate(dto.tGrafoExpiryDate());
        existingTruck.setSeguroExpiryDate(dto.seguroExpiryDate());

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
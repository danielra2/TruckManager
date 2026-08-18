package mycode.truckmanager.trucks.service;

import lombok.RequiredArgsConstructor;
import mycode.truckmanager.trucks.dtos.TruckResponseDto;
import mycode.truckmanager.trucks.mappers.TruckMapper;
import mycode.truckmanager.trucks.models.Truck;
import mycode.truckmanager.trucks.repository.TruckRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TruckQueryServiceImpl implements TruckQueryService {

    private final TruckRepository truckRepository;
    private final TruckMapper truckMapper;

    @Override
    public List<TruckResponseDto> getAllTrucks() {
        return truckRepository.findAll().stream()
                .map(truckMapper::toResponseDto)
                .toList();
    }

    @Override
    public TruckResponseDto getTruckById(Long id) {
        Truck truck = truckRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Camionul cu ID-ul " + id + " nu a fost găsit."));
        return truckMapper.toResponseDto(truck);
    }
}
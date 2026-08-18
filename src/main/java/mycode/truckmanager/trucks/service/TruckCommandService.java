package mycode.truckmanager.trucks.service;

import mycode.truckmanager.trucks.dtos.TruckRequestDto;
import mycode.truckmanager.trucks.dtos.TruckResponseDto;

public interface TruckCommandService {
    TruckResponseDto createTruck(TruckRequestDto dto);
    TruckResponseDto updateTruck(Long id, TruckRequestDto dto);
    void deleteTruck(Long id);
}
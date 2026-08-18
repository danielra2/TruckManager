package mycode.truckmanager.trucks.service;

import mycode.truckmanager.trucks.dtos.TruckResponseDto;

import java.util.List;

public interface TruckQueryService {
    List<TruckResponseDto> getAllTrucks();
    TruckResponseDto getTruckById(Long id);
}
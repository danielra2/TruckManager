package mycode.truckmanager.trucks.dtos;

import java.time.LocalDateTime;

public record TruckResponseDto(
        Long id,
        String licensePlate,
        String make,
        String model,
        DocumentDetail itp,
        DocumentDetail insurance,
        DocumentDetail tacho,
        DocStatus overallStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
package mycode.truckmanager.trucks.dtos;

import java.time.LocalDate;

public record TruckResponseDto(
        Long id,
        String licensePlate,
        String make,
        String model,
        LocalDate vgpExpiryDate,
        LocalDate itvExpiryDate,
        LocalDate limitVExpiryDate,
        LocalDate tGrafoExpiryDate,
        LocalDate seguroExpiryDate,
        DocumentDetail vgp,
        DocumentDetail itv,
        DocumentDetail limitV,
        DocumentDetail tGrafo,
        DocumentDetail seguro,
        DocStatus overallStatus
) {}
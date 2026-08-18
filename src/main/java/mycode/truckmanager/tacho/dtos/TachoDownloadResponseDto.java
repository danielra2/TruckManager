package mycode.truckmanager.tacho.dtos;

import mycode.truckmanager.trucks.dtos.DocStatus;
import java.time.LocalDate;

public record TachoDownloadResponseDto(
        Long id,
        String licensePlate,
        String driverName,
        LocalDate lastDownloadDate,
        LocalDate nextDownloadDate,
        long daysRemaining,
        DocStatus status
) {}
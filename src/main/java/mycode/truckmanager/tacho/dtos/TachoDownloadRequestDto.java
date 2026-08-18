package mycode.truckmanager.tacho.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record TachoDownloadRequestDto(
        @NotBlank(message = "Numărul de înmatriculare este obligatoriu")
        String licensePlate,
        String driverName,
        @NotNull(message = "Data ultimei descărcări este obligatorie")
        LocalDate lastDownloadDate,
        LocalDate nextDownloadDate
) {}
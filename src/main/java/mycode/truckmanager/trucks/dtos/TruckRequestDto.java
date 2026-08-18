package mycode.truckmanager.trucks.dtos;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record TruckRequestDto(
        @NotBlank(message = "Numărul de înmatriculare este obligatoriu")
        String licensePlate,

        @NotBlank(message = "Marca este obligatorie")
        String make,

        @NotBlank(message = "Modelul este obligatoriu")
        String model,

        LocalDate vgpExpiryDate,
        LocalDate itvExpiryDate,
        LocalDate limitVExpiryDate,
        LocalDate tGrafoExpiryDate,
        LocalDate seguroExpiryDate
) {}
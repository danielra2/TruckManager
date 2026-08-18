package mycode.truckmanager.trucks.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record TruckRequestDto(
        @NotBlank(message = "Numărul de înmatriculare este obligatoriu")
        String licensePlate,

        @NotBlank(message = "Marca este obligatorie")
        String make,

        @NotBlank(message = "Modelul este obligatoriu")
        String model,

        @NotNull(message = "Data ITP este obligatorie")
        LocalDate itpExpiryDate,

        @NotNull(message = "Data asigurării este obligatorie")
        LocalDate insuranceExpiryDate,

        @NotNull(message = "Data tahografului este obligatorie")
        LocalDate tachoExpiryDate
) {}
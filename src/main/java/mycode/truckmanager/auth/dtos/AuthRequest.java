package mycode.truckmanager.auth.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "Parola este obligatorie")
        String password
) {}
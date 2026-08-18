package mycode.truckmanager.auth.service;

import lombok.RequiredArgsConstructor;
import mycode.truckmanager.auth.dtos.AuthRequest;
import mycode.truckmanager.auth.dtos.AuthResponse;
import mycode.truckmanager.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${app.security.admin-password:}")
    private String adminPassword;

    private final JwtUtil jwtUtil;

    public AuthResponse login(AuthRequest request) {
        if (request == null || request.password() == null) {
            throw new BadCredentialsException("Parolă invalidă");
        }

        String rawPassword = request.password().trim();

        if (adminPassword == null || adminPassword.isBlank()) {
            throw new BadCredentialsException("Parola de administrator nu este configurată pe server");
        }

        // Comparație sigură la nivel de octeți (previne atacurile de tip timing attack)
        boolean matches = MessageDigest.isEqual(
                rawPassword.getBytes(StandardCharsets.UTF_8),
                adminPassword.trim().getBytes(StandardCharsets.UTF_8)
        );

        if (!matches) {
            throw new BadCredentialsException("Parolă incorectă");
        }

        String token = jwtUtil.generateToken("admin");
        return new AuthResponse(token);
    }
}
package mycode.truckmanager.auth.service;

import lombok.RequiredArgsConstructor;
import mycode.truckmanager.auth.dtos.AuthRequest;
import mycode.truckmanager.auth.dtos.AuthResponse;
import mycode.truckmanager.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${app.security.admin-password-hash}")
    private String adminPasswordHash;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse login(AuthRequest request) {
        if (request == null || request.password() == null) {
            throw new BadCredentialsException("Parolă invalidă");
        }

        String rawPassword = request.password().trim();

        // Compară parola introdusă cu hash-ul BCrypt
        if (!passwordEncoder.matches(rawPassword, adminPasswordHash)) {
            throw new BadCredentialsException("Parolă incorectă");
        }

        String token = jwtUtil.generateToken("admin");
        return new AuthResponse(token);
    }
}
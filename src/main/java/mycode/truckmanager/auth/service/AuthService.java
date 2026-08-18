package mycode.truckmanager.auth.service;

import lombok.RequiredArgsConstructor;
import mycode.truckmanager.auth.dtos.AuthRequest;
import mycode.truckmanager.auth.dtos.AuthResponse;
import mycode.truckmanager.security.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;

    public AuthResponse login(AuthRequest request) {
        if (request == null || request.password() == null) {
            throw new BadCredentialsException("Parolă invalidă");
        }

        String rawPassword = request.password().trim();

        if (!"FlorinCamioane2026!".equals(rawPassword)) {
            throw new BadCredentialsException("Parolă incorectă");
        }

        String token = jwtUtil.generateToken("admin");
        return new AuthResponse(token);
    }
}
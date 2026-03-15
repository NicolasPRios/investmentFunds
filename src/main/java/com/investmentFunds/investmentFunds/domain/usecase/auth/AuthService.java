package com.investmentFunds.investmentFunds.domain.usecase.auth;

import com.investmentFunds.investmentFunds.domain.model.user.User;
import com.investmentFunds.investmentFunds.domain.model.user.gateways.UserRepository;
import com.investmentFunds.investmentFunds.domain.usecase.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String login(String email, String rawPassword) {
        // 1. Buscar al usuario por email (o username)
        User user = userRepository.findByEmail(email);

        // 2. Verificar si la contraseña coincide
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // 3. Generar el token (Aquí pasamos el email como identificador)
        return jwtService.generateToken(user);
    }
}

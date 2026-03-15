package com.investmentFunds.investmentFunds.domain.usecase.auth;

import com.investmentFunds.investmentFunds.domain.model.user.User;
import com.investmentFunds.investmentFunds.domain.model.user.gateways.UserRepository;
import com.investmentFunds.investmentFunds.domain.usecase.jwt.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Login exitoso: Debe retornar un token cuando las credenciales coinciden")
    void loginExitosoTest() {
        // 1. Arrange
        User user = User.builder()
                .email("nicolas@test.com")
                .password("password_encriptado")
                .build();

        when(userRepository.findByEmail("nicolas@test.com")).thenReturn(user);
        when(passwordEncoder.matches("password_plano", "password_encriptado")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("jwt-token-123");

        // 2. Act
        String token = authService.login("nicolas@test.com", "password_plano");

        // 3. Assert
        assertNotNull(token);
        assertEquals("jwt-token-123", token);
        verify(jwtService, times(1)).generateToken(user);
    }

    @Test
    @DisplayName("Login fallido: Debe lanzar excepción cuando la contraseña es incorrecta")
    void loginPasswordIncorrectoTest() {
        // 1. Arrange
        User user = User.builder()
                .email("nicolas@test.com")
                .password("password_encriptado")
                .build();

        when(userRepository.findByEmail("nicolas@test.com")).thenReturn(user);
        // Simulamos que la contraseña NO coincide
        when(passwordEncoder.matches("password_erroneo", "password_encriptado")).thenReturn(false);

        // 2. Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login("nicolas@test.com", "password_erroneo");
        });

        assertEquals("Credenciales inválidas", exception.getMessage());
        // Verificamos que NUNCA se generó el token
        verify(jwtService, never()).generateToken(any());
    }
}

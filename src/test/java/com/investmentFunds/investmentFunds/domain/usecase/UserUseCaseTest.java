package com.investmentFunds.investmentFunds.domain.usecase;

import com.investmentFunds.investmentFunds.domain.model.common.BusinessException;
import com.investmentFunds.investmentFunds.domain.model.user.User;
import com.investmentFunds.investmentFunds.domain.model.user.gateways.UserRepository;
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

public class UserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserUseCase userUseCase;

    @Test
    @DisplayName("Debe guardar usuario y encriptar clave si el saldo es >= 500.000")
    void saveUser_Exitoso() {

        User user = User.builder()
                .password("clave123")
                .availableBalance(600000L)
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn("encriptada");
        when(userRepository.saveUser(any(User.class))).thenReturn(user);

        User result = userUseCase.saveUser(user);

        assertNotNull(result);
        verify(passwordEncoder, times(1)).encode("clave123");
        verify(userRepository, times(1)).saveUser(user);
    }

    @Test
    @DisplayName("Debe lanzar BusinessException si el saldo es < 500.000")
    void saveUser_SaldoInsuficiente() {
        // 1. Arrange
        User user = User.builder()
                .availableBalance(100000L) // Saldo menor al requerido
                .build();

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userUseCase.saveUser(user);
        });

        assertEquals("SALDO_MENOR", exception.getCode());

        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).saveUser(any());
    }

    @Test
    @DisplayName("Debe actualizar el usuario correctamente")
    void updateUser_Exitoso() {

        User user = User.builder().id(1).name("Nicolas").build();
        when(userRepository.saveUser(any(User.class))).thenReturn(user);

        User result = userUseCase.updateUser(user);

        assertNotNull(result);
        verify(userRepository, times(1)).saveUser(user);
    }
}

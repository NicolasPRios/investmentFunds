package com.investmentFunds.investmentFunds.domain.usecase.email;

import com.investmentFunds.investmentFunds.domain.usecase.email.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    @DisplayName("Debe construir el mensaje y llamar a send")
    void enviarCorreoTest() {
        // Act
        emailService.enviarCorreo("nicolas@test.com", "Asunto", "Cuerpo del mensaje");

        // Assert: Verificamos que el mailSender recibió la orden de enviar
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}

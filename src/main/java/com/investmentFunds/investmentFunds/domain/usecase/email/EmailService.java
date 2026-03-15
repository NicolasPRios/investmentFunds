package com.investmentFunds.investmentFunds.domain.usecase.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreo(String destinado, String asunto, String mensaje) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(destinado);
        email.setSubject(asunto);
        email.setText(mensaje);
        mailSender.send(email);
    }
}

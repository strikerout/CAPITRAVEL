package com.capitravel.Capitravel.service.impl;

import com.capitravel.Capitravel.model.User;
import com.capitravel.Capitravel.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // true para permitir HTML
        mailSender.send(message);
    }

    public void sendConfirmationEmail(User user) {
        if (user != null) {
            String confirmationLink = "http://capitravel.com/confirmar?";
            String body = "<h1>Bienvenido a CapiTravel</h1>"
                    + "<p>Nombre: " + user.getName() + "</p>"
                    + "<p>Email: " + user.getEmail() + "</p>"
                    + "<p>Por favor, haz clic en el siguiente enlace para logearte a tu cuenta:</p>"
                    + "<a href='" + confirmationLink + "'>Login</a>";
            try {
                sendEmail(user.getEmail(), "Confirma tu cuenta", body);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


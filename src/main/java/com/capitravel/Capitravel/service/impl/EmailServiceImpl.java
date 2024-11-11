package com.capitravel.Capitravel.service.impl;

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

    public void sendConfirmationEmail(String email, String name, String lastName) {
        String confirmationLink = "http://localhost:5173//login?";
        String body = "<h1>Welcome to CapiTravel</h1>"
                + "<p>Name: " + name + " " + lastName + "</p>"
                + "<p>Email: " + email + "</p>"
                + "<p>Please, click the following link to login to your account:</p>"
                + "<a href='" + confirmationLink + "'>Login</a>";
        try {
            sendEmail(email, "Welcome from Capi", body);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

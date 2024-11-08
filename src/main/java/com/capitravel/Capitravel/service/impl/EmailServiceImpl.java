package com.capitravel.Capitravel.service.impl;
import com.capitravel.Capitravel.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmationEmail(String to, String subject, String body) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // true para permitir HTML

        mailSender.send(message);
    }

    public void sendConfirmationEmail(String mail) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            String confirmationLink = "http://capitravel.com/confirmar?";
            String body = "<h1>Bienvenido a nuestra aplicación</h1>"
                    + "<p>Por favor, haz clic en el siguiente enlace para logearte a tu cuenta:</p>"
                    + "<a href='" + confirmationLink + "'>Confirmar cuenta</a>";


            helper.setTo(mail);
            helper.setSubject("Confirma tu cuenta");
            helper.setText(body, true); // true para permitir HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}


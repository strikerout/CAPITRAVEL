package com.capitravel.Capitravel.service;

import com.capitravel.Capitravel.model.User;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmail(String to, String subject, String body) throws MessagingException;

    void sendConfirmationEmail(User user);
}

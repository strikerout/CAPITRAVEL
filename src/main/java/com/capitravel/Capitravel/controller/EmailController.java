package com.capitravel.Capitravel.controller;

import com.capitravel.Capitravel.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Void> resendEmail(@RequestParam String email, @RequestParam String name, @RequestParam String lastName) {
        emailService.sendConfirmationEmail(email, name, lastName);
        return ResponseEntity.noContent().build();
    }
}

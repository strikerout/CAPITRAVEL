package com.capitravel.Capitravel.controller;

import com.capitravel.Capitravel.dto.EmailDTO;
import com.capitravel.Capitravel.service.EmailService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Void> resendEmail(@Valid @RequestBody EmailDTO emailDTO) {
        emailService.sendConfirmationEmail(emailDTO.getEmail(), emailDTO.getName(), emailDTO.getLastName());
        return ResponseEntity.noContent().build();
    }
}

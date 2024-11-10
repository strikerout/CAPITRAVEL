package com.capitravel.Capitravel.controller;

import com.capitravel.Capitravel.model.User;
import com.capitravel.Capitravel.service.EmailService;
import com.capitravel.Capitravel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> resendEmail(@PathVariable Long id) {
        User foundUser = userService.getUserByID(id);
        emailService.sendConfirmationEmail(foundUser);
        return ResponseEntity.noContent().build();
    }

}

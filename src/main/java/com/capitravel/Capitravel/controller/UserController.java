package com.capitravel.Capitravel.controller;

import com.capitravel.Capitravel.dto.UserDTO;
import com.capitravel.Capitravel.model.User;
import com.capitravel.Capitravel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDTO userDTO) {
        User newUser = userService.saveUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        User user = userService.getUser(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/role")
    public ResponseEntity<Void> addRoleToUser(@RequestParam String email, @RequestParam String roleName) {
        userService.addRoleToUser(email, roleName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

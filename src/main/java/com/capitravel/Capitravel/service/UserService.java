package com.capitravel.Capitravel.service;

import com.capitravel.Capitravel.dto.UserDTO;
import com.capitravel.Capitravel.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User saveUser(UserDTO user);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);
}

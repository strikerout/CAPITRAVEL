package com.capitravel.Capitravel.service;

import com.capitravel.Capitravel.dto.UserDTO;
import com.capitravel.Capitravel.model.Role;
import com.capitravel.Capitravel.model.User;

public interface UserService {

    User saveUser(UserDTO user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);
}

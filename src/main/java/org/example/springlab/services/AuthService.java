package org.example.springlab.services;


import org.example.springlab.models.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AuthService implements AuthServiceInterface {

    private final UserServiceInterface userService;

    public AuthService(UserServiceInterface userService) {
        this.userService = userService;
    }

    @Override
    public User authenticate(String login, String password) {
        User user = userService.getAll().stream().filter(u -> u.getLogin().equals(login)).toList().getFirst();
        if(!BCrypt.checkpw(password, user.getPassword())) {
            throw new IllegalArgumentException("Login failed: passwords don't match");
        }
        return user;
    }

    @Override
    public void register(String login, String password, String passwordConfirmation) {
        if(password.compareTo(passwordConfirmation) != 0) {
            throw new IllegalArgumentException("Register failed: passwords don't match");
        }
        else if(!userService.userExists(login)) {
            userService.add(new User(UUID.randomUUID().toString(), login, BCrypt.hashpw(password, BCrypt.gensalt()), "User"));
            userService.save();
        }
    }
}

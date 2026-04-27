package org.example.services;

import org.example.models.User;
import org.example.repositories.UserJsonRepository;
import org.example.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;
import java.util.UUID;

public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User authenticate(String login, String password) {
        User user = userService.findByLogin(login);
        if(!BCrypt.checkpw(password, user.getPassword())) {
            throw new IllegalArgumentException("Login failed: passwords don't match");
        }
        return user;
    }

    public void register(String login, String password, String passwordConfirmation) {
        if(password.compareTo(passwordConfirmation) != 0) {
            throw new IllegalArgumentException("Register failed: passwords don't match");
        }
        else if(!userService.userExist(login)) {
            userService.add(new User(UUID.randomUUID().toString(), login, BCrypt.hashpw(password, BCrypt.gensalt()), "user"));
            userService.save();
        }
    }
}

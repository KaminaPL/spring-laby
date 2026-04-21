package org.example.services;

import org.example.models.User;
import org.example.repositories.UserJsonRepository;
import org.example.repositories.UserRepository;

import java.util.Optional;

public class UserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public User findByLogin(String login)
    {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("No user with such name: " + login));
    }

    public void removeByLogin(String login)
    {
        userRepository.removeByLogin(login);
    }
}

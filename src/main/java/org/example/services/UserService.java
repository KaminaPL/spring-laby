package org.example.services;

import org.example.models.User;
import org.example.repositories.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean userExists(String login) {
        return repository.findByLogin(login).isPresent();
    }

    public List<User> getAll() { return repository.getAll(); }

    public User findByLogin(String login) {
        return repository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("No user with such name: " + login));
    }

    public void removeByLogin(String login) {
        repository.removeByLogin(login);
    }

    public void add(User user) {
        repository.add(user);
    }

    public void save() { repository.save(); }
}

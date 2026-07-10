package org.example.springlab.services;


import org.example.springlab.models.User;
import org.example.springlab.repositories.UserJpaRepositoryAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService implements UserServiceInterface {

    private final UserJpaRepositoryAdapter repository;

    public UserService(UserJpaRepositoryAdapter repository) {
        this.repository = repository;
    }

    @Override
    public boolean userExists(User user) {
        return repository.userExists(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No user with such id: " + id));
    }

    @Override
    public User findByLogin(String login) {
        return repository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("No user with such login: " + login));
    }

    @Override
    public void removeById(String id) {
        repository.removeById(id);
    }

    @Override
    public void add(User user) {
        repository.add(user);
    }
}

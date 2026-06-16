package org.example.springlab.services;

import org.example.springlab.models.User;

import java.util.List;

public interface UserServiceInterface {

    boolean userExists(String id);

    List<User> getAll();

    User findById(String id);

    User findByLogin(String login);

    void removeById(String id);

    void add(User user);

    void save();
}

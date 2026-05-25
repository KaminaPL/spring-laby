package org.example.services;

import org.example.models.User;

import java.util.List;

public interface UserServiceInterface {

    boolean userExists(String login);

    List<User> getAll();

    User findByLogin(String login);

    void removeByLogin(String login);

    void add(User user);

    void save();
}

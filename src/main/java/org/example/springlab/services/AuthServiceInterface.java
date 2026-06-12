package org.example.springlab.services;

import org.example.springlab.models.User;

public interface AuthServiceInterface {

    User authenticate(String login, String password);

    void register(String login, String password, String passwordConfirmation);
}

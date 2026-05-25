package org.example.services;

import org.example.models.User;

public interface AuthServiceInterface {

    User authenticate(String login, String password);

    void register(String login, String password, String passwordConfirmation);
}

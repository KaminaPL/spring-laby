package org.example;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;

public class AuthService
{
    private UserJsonRepository repository;


    public AuthService()
    {
        repository = new UserJsonRepository("/home/bartosz/users.csv");
    }

    public User authenticate(String login, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);
        User user = repository.getUser(login);

        if(user.getLogin() != null)
        {
            if(hashedPassword.compareTo(user.getHashedPassword()) == 0)
            {
                return user;
            }
            else
            {
                System.out.println("Incorrect password.\n");
                return new User(null, null, null, null);
            }
        }

        System.out.println("User not found.\n");
        return new User(null, null, null, null);
    }

    public boolean register(String login, String password, String passwordConfirmation)
    {
        if(password.compareTo(passwordConfirmation) != 0)
        {
            System.out.println("Passwords don't match.\n");
            return false;
        }

        int outcome = repository.add(new User(login, DigestUtils.sha256Hex(password), "USER", -1));

        if(outcome == 0)
        {
            System.out.println("Successfully registered.");
            return true;
        }

        System.out.println("User already exists.");
        return false;
    }
}

package org.example;

import org.apache.commons.codec.digest.DigestUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;
import java.util.UUID;

public class AuthService
{
    private UserJsonRepository repository;

    public AuthService()
    {
        repository = new UserJsonRepository("/home/bartosz/code/projects/java/Lab3/users.json");
    }

    public Optional<User> authenticate(String login, String password)
    {
        Optional<User> user = repository.findByLogin(login);
        if(user.isPresent())
        {
            if(BCrypt.checkpw(password, user.get().getPassword()))
            {
                return user;
            }
        }
        return null;
    }

    public boolean register(String login, String password, String passwordConfirmation)
    {
        if(password.compareTo(passwordConfirmation) != 0)
        {
            return false;
        }
        if(repository.findByLogin(login).isEmpty())
        {
            repository.add(new User(UUID.randomUUID().toString(), login, BCrypt.hashpw(password, BCrypt.gensalt()), "user"));
            return true;
        }
        return false;
    }
}

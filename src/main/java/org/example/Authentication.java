package org.example;

import org.apache.commons.codec.digest.DigestUtils;

public class Authentication
{
    private UserRepositoryImpl repository;


    public Authentication()
    {
        repository = new UserRepositoryImpl("/home/bartosz/users.csv");
    }

    public User authenticate(String login, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);
        User user = repository.getUser(login);


        if(user.getLogin() != null)
        {
            if(hashedPassword.compareTo(user.getPassword()) == 0)
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
}

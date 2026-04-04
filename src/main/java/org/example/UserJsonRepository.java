package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserJsonRepository implements UserRepository
{
    private JsonFileStorage<User> repository;
    private List<User> userList;
    private String filename;


    public UserJsonRepository(String filename)
    {
        this.filename = filename;
        repository = new JsonFileStorage<>(filename, User.class);
        userList = repository.load();
    }


    @Override
    public Optional<User> getUser(String login)
    {
        User user = null;

        try
        {
            user = userList.stream().filter(it -> it.getLogin().compareTo(login) == 0).toList().getFirst();
        }
        catch (NoSuchElementException e)
        {
            e.printStackTrace();
        }

        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getUsers()
    {
        return userList.stream().map(it -> it.copy()).toList();
    }

    @Override
    public void addUser(User user)
    {
        userList.add(user);
    }

    @Override
    public void removeUserById(String id)
    {
        userList = userList.stream().filter(it -> it.getId().compareTo(id) == 0).toList();
    }

    @Override
    public void removeUserByLogin(String login)
    {
        userList = userList.stream().filter(it -> it.getLogin().compareTo(login) == 0).toList();
    }

    @Override
    public void updateUser(User user)
    {
        userList = userList.stream().filter(it -> it.getLogin().compareTo(user.getLogin()) == 0).toList();
        userList.add(user.copy());
    }
}

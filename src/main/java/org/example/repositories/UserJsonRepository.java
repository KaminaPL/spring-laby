package org.example.repositories;

import com.google.gson.reflect.TypeToken;
import org.example.db.JsonFileStorage;
import org.example.models.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class UserJsonRepository implements UserRepository
{
    private final JsonFileStorage<User> storage;
    private List<User> userList;

    public UserJsonRepository(String filename)
    {
        storage = new JsonFileStorage<>(filename, new TypeToken<List<User>>() {}.getType());
        userList = storage.load();
    }

    @Override
    public List<User> getAll()
    {
        return userList.stream().map(u -> u.copy()).toList();
    }

    @Override
    public Optional<User> findByLogin(String login)
    {
        try
        {
            User user = userList.stream().filter(u -> u.getLogin().equals(login)).toList().getFirst();
            return Optional.of(user.copy());
        }
        catch(NoSuchElementException e)
        {
           e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void add(User user)
    {
        userList.add(user.copy());
    }

    @Override
    public void removeByLogin(String login)
    {
        userList = userList.stream().filter(u -> !u.getLogin().equals(login)).toList();
    }

    @Override
    public void update(User user)
    {
        userList = userList.stream().filter(u -> !u.getLogin().equals(user.getLogin())).toList();
        userList.add(user.copy());
    }

    @Override
    public void save()
    {
        storage.save(userList);
    }
}

package org.example;

import com.google.gson.reflect.TypeToken;

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
        User user = null;
        try
        {
            user = userList.stream().filter(u -> u.getLogin().compareTo(login) == 0).toList().getFirst();
        }
        catch (NoSuchElementException e)
        {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public void add(User user)
    {
        userList.add(user.copy());
    }

    @Override
    public void removeById(String id)
    {
        userList = userList.stream().filter(u -> u.getId().compareTo(id) == 0).toList();
    }

    @Override
    public void removeByLogin(String login)
    {
        userList = userList.stream().filter(u -> u.getLogin().compareTo(login) == 0).toList();
    }

    @Override
    public void update(User user)
    {
        userList = userList.stream().filter(u -> u.getLogin().compareTo(user.getLogin()) == 0).toList();
        userList.add(user.copy());
    }

    @Override
    public void save()
    {
        storage.save(userList);
    }
}

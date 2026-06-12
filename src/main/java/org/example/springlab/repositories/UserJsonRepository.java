package org.example.springlab.repositories;

import com.google.gson.reflect.TypeToken;
import org.example.springlab.db.JsonFileStorage;
import org.example.springlab.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Profile("json")
public class UserJsonRepository implements UserRepository {
    private final JsonFileStorage<User> storage;
    private List<User> userList;

    public UserJsonRepository(@Value("${springlab.json.users-file}") String filename) {
        storage = new JsonFileStorage<>(filename, new TypeToken<List<User>>() {}.getType());
        userList = storage.load();
    }

    @Override
    public List<User> getAll()
    {
        return userList.stream().map(u -> u.copy()).toList();
    }

    @Override
    public Optional<User> findById(String id) {
        try {
            User user = userList.stream().filter(u -> u.getId().equals(id)).toList().get(0);
            return Optional.of(user.copy());
        } catch(NoSuchElementException e) {
          // e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void add(User user)
    {
        userList.add(user.copy());
    }

    @Override
    public void removeById(String id) {
        userList = userList.stream().filter(u -> !u.getId().equals(id)).toList();
    }

    @Override
    public void update(User user) {
        userList = userList.stream().filter(u -> !u.getLogin().equals(user.getLogin())).toList();
        userList.add(user.copy());
    }

    @Override
    public void save()
    {
        storage.save(userList);
    }
}

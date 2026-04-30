package org.example.repositories;

import org.example.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository
{
   Optional<User> findByLogin(String login);

   List<User> getAll();

   User add(User user);

   void removeByLogin(String login);

   void update(User user);

   void save();
}

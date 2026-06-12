package org.example.springlab.repositories;

import org.example.springlab.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository
{
   Optional<User> findById(String login);

   List<User> getAll();

   void add(User user);

   void removeById(String login);

   void update(User user);

   void save();
}

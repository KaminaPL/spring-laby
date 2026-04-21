package org.example;

import java.util.List;
import java.util.Optional;

public interface UserRepository
{
   Optional<User> findByLogin(String login);

   List<User> getAll();

   void add(User user);

   void removeById(String id);

   void removeByLogin(String login);

   void update(User user);

   void save();
}

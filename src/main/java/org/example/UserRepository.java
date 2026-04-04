package org.example;

import java.util.List;
import java.util.Optional;

public interface UserRepository
{
   Optional<User> getUser(String login);

   List<User> getUsers();

   void addUser(User user);

   void removeUserById(String id);

   void removeUserByLogin(String login);

   void updateUser(User user);
}

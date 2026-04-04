package org.example;

import java.util.List;

public interface IUserRepository
{
   User getUser(String login);

   List<User> getUsers();

   Integer add(User user);

   Integer remove(String login);

   void update(User user);
}

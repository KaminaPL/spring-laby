package org.example.springlab.repositories;

import org.example.springlab.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

   boolean userExists(User user);

   Optional<User> findById(String id);

   Optional<User> findByLogin(String login);

   List<User> findAll();

   void add(User user);

   void removeById(String id);
}

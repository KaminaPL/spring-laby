package org.example.springlab.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.springlab.models.User;

import java.util.Optional;

@Profile("jpa")
public interface UserJpaRepository extends JpaRepository<User, String> {

    Optional<User> findByLogin(String login);
}

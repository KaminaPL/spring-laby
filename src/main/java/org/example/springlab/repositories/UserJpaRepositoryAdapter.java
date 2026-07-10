package org.example.springlab.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.springlab.models.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("jpa")
public class UserJpaRepositoryAdapter implements UserRepository {

    private final UserJpaRepository delegate;

    public UserJpaRepositoryAdapter(UserJpaRepository delegate) {
        this.delegate  = delegate;
    }

    @Override
    public boolean userExists(User user) {
        return delegate.exists(Example.of(user));
    }

    @Override
    public List<User> findAll() {
        return delegate.findAll();
    }

    @Override
    public Optional<User> findById(String id) {
       return delegate.findById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return delegate.findByLogin(login);
    }

    @Override
    public void add(User user) {
        if(user.getId() == null || user.getId().isEmpty()) {
            user.setId(UUID.randomUUID().toString());
            while(delegate.findById(user.getId()).isPresent()) {
                user.setId(UUID.randomUUID().toString());
            }
        }
        delegate.save(user);
    }

    @Override
    public void removeById(String id) {
        delegate.deleteById(id);
    }
}

package org.example.springlab.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.springlab.models.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("hibernate")
public class UserHibernateRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getAll() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public Optional<User> findById(String id) {
        try {
           List<User> userList = entityManager.createQuery("from User", User.class).getResultList();
            return Optional.of(userList.stream()
                    .filter(u -> u.getId().equals(id))
                    .toList().get(0));
        } catch (ArrayIndexOutOfBoundsException | NoSuchElementException e) {
            // Some cool stuff here
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            List<User> userList = entityManager.createQuery("from User", User.class).getResultList();
            return Optional.of(userList.stream()
                    .filter(u -> u.getLogin().equals(login))
                    .toList().get(0));
        } catch (ArrayIndexOutOfBoundsException | NoSuchElementException e) {
            // Some cool stuff here
        }
        return Optional.empty();
    }

    @Override
    public void add(User user) {
        if(user.getId() == null || user.getId().isBlank()) {
            user.setId(UUID.randomUUID().toString());
            while(entityManager.find(User.class, user.getId()) != null) {
                user.setId(UUID.randomUUID().toString());
            }
        }
        entityManager.merge(user);
    }

    @Override
    public void removeById(String id) {
        try {
            List<User> userList = entityManager.createQuery("from User", User.class).getResultList();
            entityManager.remove(userList.stream()
                    .filter(u -> u.getId().equals(id))
                    .toList().get(0));
        } catch (ArrayIndexOutOfBoundsException | NoSuchElementException e) {
            // Some cool stuff here
        }
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void save() {

    }
}

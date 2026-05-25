package org.example.repositories;

import lombok.Setter;
import org.example.db.HibernateConfig;
import org.example.models.Rental;
import org.example.models.User;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;

import java.util.*;

public class UserHibernateRepository implements UserRepository {

    @Setter
    private Session session;

    public List<User> getAll() {
        Transaction ts = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            List<User> userList = session.createQuery("from User", User.class).list();
            ts.commit();
            return userList;
        } catch (SessionException e) {
            if(ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Optional<User> findByLogin(String login) {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            List<User> userList = session.createQuery("from User", User.class).list();
            ts.commit();
            return Optional.ofNullable(userList.stream().filter(v -> v.getLogin().equals(login)).toList().getFirst());
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
        } catch (NoSuchElementException e) {

        }
        return Optional.empty();
    }

    public void add(User user) {
        Transaction ts = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            user.setId(UUID.randomUUID().toString());
            while(session.find(User.class, user.getId()) != null) {
                user.setId(UUID.randomUUID().toString());
            }
            session.merge(user);
            ts.commit();
        } catch (SessionException e) {
            if(ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
        }
    }

    public void removeByLogin(String login) {
        Transaction ts = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            Optional<User> user = findByLogin(login);
            user.ifPresent(session::remove);
            ts.commit();
        } catch (SessionException e) {
            if(ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(User user) {
        Transaction ts = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            session.merge(user);
            ts.commit();
        } catch (SessionException e) {
            if(ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
        }
    }

    public void save() {

    }
}

package org.example.repositories;

import jakarta.persistence.NoResultException;
import lombok.Setter;
import org.example.db.HibernateConfig;
import org.example.models.Rental;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;

import java.util.*;

public class RentalHibernateRepository implements RentalRepository {

    @Setter
    private Session session;

    public List<Rental> getAll() {
        Transaction ts = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            List<Rental> rentalList = session.createQuery("from Rental", Rental.class)
                    .stream().filter(Rental::isActive).toList();
            ts.commit();
            return rentalList;
        } catch (SessionException e) {
            if(ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Optional<Rental> findById(String id) {
        Transaction ts = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            ts.commit();
            return Optional.ofNullable(session.find(Rental.class, id));
        } catch (SessionException e) {
            if(ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Rental> findByVehicleId(String vehicleId) {
        Transaction ts = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            List<Rental> rentalList = session.createQuery("from Rental", Rental.class).list();
            ts.commit();
            return Optional.ofNullable(rentalList.stream()
                    .filter(r -> (r.getVehicle().getId().equals(vehicleId) && r.isActive())).toList().getFirst());
        } catch (SessionException e) {
            if(ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
        } catch (NoSuchElementException e) {

        }
        return Optional.empty();
    }

    public Optional<Rental> findByUserId(String userId) {
        Transaction ts = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            List<Rental> rentalList = session.createQuery("from Rental", Rental.class).list();
            ts.commit();
            return Optional.ofNullable(rentalList.stream()
                    .filter(r -> (r.getUser().getId().equals(userId) && r.isActive())).toList().getFirst());
        } catch (SessionException e) {
            if(ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
        } catch (NoSuchElementException e) {

        }
        return Optional.empty();
    }

    public Optional<Rental> findByIdAndReturnDateIsNull(String id) {
        Transaction ts = null;

        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            Rental rental = session.find(Rental.class, id);
            ts.commit();
            if(rental.isActive()) {
                return Optional.of(rental);
            } else {
                return Optional.empty();
            }
        } catch (SessionException e) {
            if(ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void add(Rental rental) {
        Transaction ts = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            if(rental.getId() == null || rental.getId().isBlank()) {
                rental.setId(UUID.randomUUID().toString());
                while(session.find(Rental.class, rental.getId()) != null) {
                    rental.setId(UUID.randomUUID().toString());
                }
            }
            setSession(session);
            session.merge(rental);
            ts.commit();
        } catch (SessionException e) {
            if(ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
        }
    }

    public void removeById(String id) {
        Transaction ts = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            Rental rental = session.find(Rental.class, id);
            if(rental != null) {
                rental.setRentDateTime("");
                rental.setReturnDateTime("");
                session.remove(rental);
            }
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

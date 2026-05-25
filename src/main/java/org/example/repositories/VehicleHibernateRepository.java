package org.example.repositories;

import lombok.Setter;
import org.example.db.HibernateConfig;
import org.example.models.Rental;
import org.example.models.Vehicle;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VehicleHibernateRepository implements VehicleRepository {

    @Setter
    private Session session;

    public List<Vehicle> getAll() {
        Transaction ts = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            List<Vehicle> vehicleList = session.createQuery("from Vehicle", Vehicle.class).list();
            ts.commit();
            return vehicleList;
        } catch (SessionException e) {
            if(ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Optional<Vehicle> findById(String id) {
        Transaction ts = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            ts.commit();
            return Optional.ofNullable(session.find(Vehicle.class, id));
        } catch (SessionException e) {
            if(ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void add(Vehicle vehicle) {
        Transaction ts = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            vehicle.setId(UUID.randomUUID().toString());
            while(session.find(Vehicle.class, vehicle.getId()) != null) {
                vehicle.setId(UUID.randomUUID().toString());
            }
            session.merge(vehicle);
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
            Vehicle vehicle = session.find(Vehicle.class, id);
            if(vehicle != null) {
                session.remove(vehicle);
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

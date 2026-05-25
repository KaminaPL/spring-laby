package org.example.repositories;

import lombok.Setter;
import org.example.db.HibernateConfig;
import org.example.models.Vehicle;
import org.example.models.VehicleCategoryConfig;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleCategoryConfigHibernateRepository implements  VehicleCategoryConfigRepository {

    @Setter
    private Session session;

    public List<VehicleCategoryConfig> getAll() {
        Transaction ts = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            List<VehicleCategoryConfig> configList = session.createQuery("from VehicleCategoryConfig",
                    VehicleCategoryConfig.class).list();
            ts.commit();
            return configList;
        } catch (SessionException e) {
            if(ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Optional<VehicleCategoryConfig> findByCategory(String category) {
        Transaction ts = null;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            ts.commit();
            return Optional.ofNullable(session.find(VehicleCategoryConfig.class, category));
        } catch (SessionException e) {
            if(ts != null && ts.isActive()) {
                ts.rollback();
            }
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

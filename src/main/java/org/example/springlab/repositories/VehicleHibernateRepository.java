package org.example.springlab.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.springlab.models.Vehicle;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("hibernate")
public class VehicleHibernateRepository implements VehicleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Vehicle> getAll() {
        return entityManager.createQuery("from Vehicle", Vehicle.class).getResultList();
    }

    @Override
    public Optional<Vehicle> findById(String id) {
        return Optional.ofNullable(entityManager.find(Vehicle.class, id));
    }

    @Override
    public void add(Vehicle vehicle) {
        if(vehicle.getId() == null || vehicle.getId().isBlank()) {
            vehicle.setId(UUID.randomUUID().toString());
            while(entityManager.find(Vehicle.class, vehicle.getId()) != null) {
                vehicle.setId(UUID.randomUUID().toString());
            }
        }
        entityManager.merge(vehicle);
    }

    @Override
    public void removeById(String id) {
        Vehicle vehicle = entityManager.find(Vehicle.class, id);
        if(vehicle != null) {
            entityManager.remove(vehicle);
        }
    }

    @Override
    public void save() {

    }
}

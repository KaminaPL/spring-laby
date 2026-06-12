package org.example.springlab.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.Setter;
import org.example.springlab.db.HibernateConfig;
import org.example.springlab.models.Rental;
import org.example.springlab.models.Vehicle;
import org.example.springlab.services.RentalServiceInterface;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("hibernate")
public class RentalHibernateRepository implements RentalRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Rental> getAll() {
        return entityManager.createQuery("from Rental", Rental.class).getResultList();
    }

    @Override
    public Optional<Rental> findById(String id) {
        return Optional.ofNullable(entityManager.find(Rental.class, id));
    }

    @Override
    public Optional<Rental> findByVehicleId(String vehicleId) {
        try {
            List<Rental> rentalList = entityManager.createQuery("from Rental", Rental.class).getResultList();
            return Optional.of(rentalList.stream()
                    .filter(r -> (r.getVehicle().getId().equals(vehicleId) && r.isActive()))
                    .toList().get(0));
        } catch (NoSuchElementException e) {
            // Some cool stuff here
        }
        return Optional.empty();
    }

    @Override
    public Optional<Rental> findByUserId(String userId) {
        try {
            List<Rental> rentalList = entityManager.createQuery("from Rental", Rental.class).getResultList();
            return Optional.of(rentalList.stream()
                    .filter(r -> (r.getUser().getId().equals(userId) && r.isActive()))
                    .toList().get(0));
        } catch (NoSuchElementException e) {
            // Some cool stuff here
        }
        return Optional.empty();
    }

    @Override
    public Optional<Rental> findByIdAndReturnDateIsNull(String id) {
        try {
            List<Rental> rentalList = entityManager.createQuery("from Rental", Rental.class).getResultList();
            return Optional.of(rentalList.stream()
                    .filter(r -> (r.getId().equals(id) && r.isActive()))
                    .toList().get(0));
        } catch (NoSuchElementException e) {

        }
        return Optional.empty();
    }

    @Override
    public void add(Rental rental) {
        if(rental.getId() == null || rental.getId().isBlank()) {
            rental.setId(UUID.randomUUID().toString());
            while(entityManager.find(Rental.class, rental.getId()) != null) {
                rental.setId(UUID.randomUUID().toString());
            }
        }
        entityManager.merge(rental);
    }

    @Override
    public void removeById(String id) {
        Rental rental = entityManager.find(Rental.class, id);
        if(rental != null) {
            rental.setRentDateTime("");
            rental.setReturnDateTime("");
            entityManager.merge(rental);
        }
    }

    @Override
    public void save() {

    }
}

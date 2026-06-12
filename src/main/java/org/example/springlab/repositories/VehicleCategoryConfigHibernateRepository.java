package org.example.springlab.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.springlab.models.VehicleCategoryConfig;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("hibernate")
public class VehicleCategoryConfigHibernateRepository implements  VehicleCategoryConfigRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VehicleCategoryConfig> getAll() {
        return entityManager.createQuery("from VehicleCategoryConfig", VehicleCategoryConfig.class).getResultList();
    }

    @Override
    public Optional<VehicleCategoryConfig> findByCategory(String category) {
        return Optional.ofNullable(entityManager.find(VehicleCategoryConfig.class, category));
    }
}

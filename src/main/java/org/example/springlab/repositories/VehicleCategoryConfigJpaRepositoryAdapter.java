package org.example.springlab.repositories;

import org.example.springlab.models.VehicleCategoryConfig;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class VehicleCategoryConfigJpaRepositoryAdapter implements VehicleCategoryConfigRepository {

    private final VehicleCategoryConfigJpaRepository delegate;

    public VehicleCategoryConfigJpaRepositoryAdapter(VehicleCategoryConfigJpaRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<VehicleCategoryConfig> findAll() {
        return delegate.findAll();
    }

    @Override
    public Optional<VehicleCategoryConfig> findByCategory(String category) {
        return delegate.findByCategory(category);
    }
}

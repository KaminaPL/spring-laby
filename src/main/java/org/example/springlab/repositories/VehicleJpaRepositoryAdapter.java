package org.example.springlab.repositories;

import org.example.springlab.models.Vehicle;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("jpa")
public class VehicleJpaRepositoryAdapter implements VehicleRepository {

    private final VehicleJpaRepository delegate;

    public VehicleJpaRepositoryAdapter(VehicleJpaRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Vehicle> findAll() {
        return delegate.findAll();
    }

    @Override
    public Optional<Vehicle> findById(String id) {
        return delegate.findById(id);
    }

    @Override
    public void add(Vehicle vehicle) {
        if(vehicle.getId() == null || vehicle.getId().isEmpty()) {
            vehicle.setId(UUID.randomUUID().toString());
            while(delegate.findById(vehicle.getId()).isPresent()) {
                vehicle.setId(UUID.randomUUID().toString());
            }
        }
       delegate.save(vehicle);
    }

    @Override
    public void removeById(String id) {
        delegate.deleteById(id);
    }
}

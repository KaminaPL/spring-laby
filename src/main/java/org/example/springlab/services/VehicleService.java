package org.example.springlab.services;


import org.example.springlab.VehicleValidator;
import org.example.springlab.models.Vehicle;
import org.example.springlab.repositories.VehicleJpaRepositoryAdapter;
import org.example.springlab.repositories.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VehicleService implements VehicleServiceInterface {

    private final VehicleJpaRepositoryAdapter repository;
    private final VehicleValidator validator;

    public VehicleService(VehicleJpaRepositoryAdapter repository, VehicleValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public List<Vehicle> findAll() { return repository.findAll(); }

    @Override
    public Vehicle findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No vehicle with such id: " + id));
    }

    @Override
    public void add(Vehicle vehicle) {
        validator.validate(vehicle);
        repository.add(vehicle);
    }

    @Override
    public void removeById(String id) {
        repository.removeById(id);
    }
}

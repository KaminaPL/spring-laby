package org.example.springlab.services;


import org.example.springlab.VehicleValidator;
import org.example.springlab.models.Vehicle;
import org.example.springlab.repositories.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VehicleService implements VehicleServiceInterface {

    private final VehicleRepository repository;
    private final VehicleValidator validator;

    public VehicleService(VehicleRepository repository, VehicleValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public List<Vehicle> getAll() { return repository.getAll(); }

    @Override
    public Vehicle findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No vehicle with such id: " + id));
    }

    @Override
    public void add(Vehicle vehicle) {
        try {
            validator.validate(vehicle);
            repository.add(vehicle);
        } catch(IllegalStateException e) {
            e.printStackTrace();
            throw new IllegalStateException("Vehicle is null");
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Vehicle attribute is missing or invalid");
        }
    }

    @Override
    public void removeById(String id) {
        repository.removeById(id);
    }

    @Override
    public void save() { repository.save(); }
}

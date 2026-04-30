package org.example.services;

import org.example.VehicleValidator;
import org.example.models.Vehicle;
import org.example.repositories.VehicleCategoryConfigJsonRepository;
import org.example.repositories.VehicleJsonRepository;
import org.example.repositories.VehicleRepository;

import java.util.Collections;
import java.util.List;

public class VehicleService {

    private final VehicleRepository repository;
    private final VehicleValidator validator;

    public VehicleService(VehicleRepository repository, VehicleValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<Vehicle> getAll() { return repository.getAll(); }

    public Vehicle findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No vehicle with such id: " + id));
    }

    public Vehicle add(Vehicle vehicle) {
        try {
            validator.validate(vehicle);
            return repository.add(vehicle);
        } catch (IllegalStateException | IllegalArgumentException e) {
            e.getCause();
            return  new Vehicle("", "", "", "", 0, 0.0, Collections.emptyMap());
        }
    }

    public void removeById(String id) {
        repository.removeById(id);
    }

    public void save() { repository.save(); }
}

package org.example.springlab.repositories;

import org.example.springlab.models.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository
{
    Optional<Vehicle> findById(String id);

    List<Vehicle> getAll();

    void add(Vehicle vehicle);

    void removeById(String id);

    void save();
}

package org.example.repositories;

import org.example.models.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository
{
    Optional<Vehicle> findById(String id);

    List<Vehicle> getAll();

    Vehicle add(Vehicle vehicle);

    void removeById(String id);

    void save();
}

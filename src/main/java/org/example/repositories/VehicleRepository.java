package org.example;

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

package org.example;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository
{
    void addVehicle(Vehicle vehicle);

    void removeVehicle(String id);

    Optional<Vehicle> getVehicle(String id);

    List<Vehicle> getVehicles();
}

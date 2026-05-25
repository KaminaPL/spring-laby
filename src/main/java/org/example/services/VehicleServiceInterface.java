package org.example.services;

import org.example.models.Vehicle;

import java.util.List;

public interface VehicleServiceInterface {

    List<Vehicle> getAll();

    Vehicle findById(String id);

    void add(Vehicle vehicle);

    void removeById(String id);

    void save();

}

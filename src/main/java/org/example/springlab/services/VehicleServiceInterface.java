package org.example.springlab.services;

import org.example.springlab.models.Vehicle;

import java.util.List;

public interface VehicleServiceInterface {

    List<Vehicle> findAll();

    Vehicle findById(String id);

    void add(Vehicle vehicle);

    void removeById(String id);
}

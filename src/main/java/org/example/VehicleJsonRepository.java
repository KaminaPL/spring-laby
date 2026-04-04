package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class VehicleJsonRepository implements VehicleRepository
{
    private JsonFileStorage<Vehicle> repository;
    private String filename;
    private List<Vehicle> vehicleList;

    public VehicleJsonRepository(String filename)
    {
        this.filename = filename;
        repository = new JsonFileStorage<>(filename, Vehicle.class);
        vehicleList = repository.load();
    }

    @Override
    public Optional<Vehicle> getVehicle(String id)
    {
        Vehicle vehicle = null;

        try
        {
            vehicle = vehicleList.stream().filter(it -> it.getId().compareTo(id) == 0).toList().getFirst();
        }
        catch (NoSuchElementException e)
        {
            e.printStackTrace();
        }

        return Optional.ofNullable(vehicle);
    }

    @Override
    public List<Vehicle> getVehicles()
    {
        List<Vehicle> copiedVehicleList = new ArrayList<>(vehicleList.size());

        for(Vehicle vehicle : vehicleList)
        {
            copiedVehicleList.add(vehicle.copy());
        }

        return copiedVehicleList;
    }

    @Override
    public void addVehicle(Vehicle vehicle)
    {
        vehicleList.add(vehicle.copy());
    }

    @Override
    public void removeVehicle(String id)
    {
        vehicleList = vehicleList.stream().filter(it -> it.getId().compareTo(id) == 0).toList();
    }
}

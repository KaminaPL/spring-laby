package org.example.repositories;

import com.google.gson.reflect.TypeToken;
import org.example.db.JsonFileStorage;
import org.example.models.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class VehicleJsonRepository implements VehicleRepository
{
    private final JsonFileStorage<Vehicle> storage;
    private List<Vehicle> vehicleList;

    public VehicleJsonRepository(String filename)
    {
        storage = new JsonFileStorage<>(filename, new TypeToken<List<Vehicle>>() {}.getType());
        vehicleList = storage.load();
    }

    @Override
    public List<Vehicle> getAll()
    {
        return vehicleList.stream().map(Vehicle::copy).toList();
    }

    @Override
    public Optional<Vehicle> findById(String id)
    {
        Vehicle vehicle = null;
        try
        {
            vehicle = vehicleList.stream().filter(v -> v.getId().equals(id)).toList().getFirst().copy();
        }
        catch (NoSuchElementException e)
        {
            e.printStackTrace();
        }
        return Optional.ofNullable(vehicle);
    }

    @Override
    public void add(Vehicle vehicle)
    {
        List<Vehicle> appendedList = new ArrayList<>(vehicleList);
        appendedList.add(vehicle);
        vehicleList = appendedList;
    }

    @Override
    public void removeById(String id)
    {
        vehicleList = vehicleList.stream().filter(v -> v.getId().equals(id)).toList();
    }

    @Override
    public void save()
    {
        storage.save(vehicleList);
    }
}

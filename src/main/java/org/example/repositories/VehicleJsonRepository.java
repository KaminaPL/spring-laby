package org.example;

import com.google.gson.reflect.TypeToken;

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
        return vehicleList.stream().map(v -> v.copy()).toList();
    }

    @Override
    public Optional<Vehicle> findById(String id)
    {
        Vehicle vehicle = null;
        try
        {
            vehicle = vehicleList.stream().filter(v -> v.getId().compareTo(id) == 0).toList().getFirst().copy();
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
        vehicleList = vehicleList.stream().filter(v -> v.getId().compareTo(id) != 0).toList();
    }

    @Override
    public void save()
    {
        storage.save(vehicleList);
    }
}

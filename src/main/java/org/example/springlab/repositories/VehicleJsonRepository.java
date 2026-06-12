package org.example.springlab.repositories;

import com.google.gson.reflect.TypeToken;
import org.example.springlab.db.JsonFileStorage;
import org.example.springlab.models.Vehicle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("json")
public class VehicleJsonRepository implements VehicleRepository {

    private final JsonFileStorage<Vehicle> storage;
    private List<Vehicle> vehicleList;

    public VehicleJsonRepository(@Value("${spring-lab.json.vehicles-file}") String filename) {
        storage = new JsonFileStorage<>(filename, new TypeToken<List<Vehicle>>() {}.getType());
        vehicleList = storage.load();
    }

    @Override
    public List<Vehicle> getAll()
    {
        return vehicleList.stream().map(Vehicle::copy).toList();
    }

    @Override
    public Optional<Vehicle> findById(String id) {
        try {
            Vehicle vehicle = vehicleList.stream().filter(v -> v.getId().equals(id)).toList().getFirst().copy();
            return Optional.of(vehicle);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void add(Vehicle vehicle) {
        if(vehicle.getId().isBlank()) {
            vehicle.setId(UUID.randomUUID().toString());
            while(findById(vehicle.getId()).isPresent()) vehicle.setId(UUID.randomUUID().toString());
        } else {
            removeById(vehicle.getId());
        }
        List<Vehicle> appendedList = new ArrayList<>(vehicleList);
        appendedList.add(vehicle);
        vehicleList = appendedList;
    }

    @Override
    public void removeById(String id) {
        vehicleList = vehicleList.stream().filter(v -> !v.getId().equals(id)).toList();
    }

    @Override
    public void save()
    {
        storage.save(vehicleList);
    }
}

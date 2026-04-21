package org.example.repositories;

import com.google.gson.reflect.TypeToken;
import org.example.db.JsonFileStorage;
import org.example.models.VehicleCategoryConfig;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class VehicleCategoryConfigJsonRepository implements VehicleCategoryConfigRepository
{
    private final JsonFileStorage<VehicleCategoryConfig> storage;
    private List<VehicleCategoryConfig> configList;

    public VehicleCategoryConfigJsonRepository(String filename)
    {
        storage = new JsonFileStorage<>(filename, new TypeToken<List<VehicleCategoryConfig>>() {}.getType());
        configList = storage.load();
    }

    public List<VehicleCategoryConfig> getAll()
    {
        return configList.stream().map(c -> c.copy()).toList();
    }

    public Optional<VehicleCategoryConfig> findByCategory(String category)
    {
        try
        {
            return Optional.ofNullable(configList.stream()
                    .filter(c -> c.getCategory().compareTo(category) == 0).toList().getFirst().copy());
        }
        catch (NoSuchElementException e)
        {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

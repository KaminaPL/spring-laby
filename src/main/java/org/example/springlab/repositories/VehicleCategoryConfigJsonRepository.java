package org.example.springlab.repositories;

import com.google.gson.reflect.TypeToken;
import org.example.springlab.db.JsonFileStorage;
import org.example.springlab.models.VehicleCategoryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Profile("json")
public class VehicleCategoryConfigJsonRepository implements VehicleCategoryConfigRepository {

    private final JsonFileStorage<VehicleCategoryConfig> storage;
    private List<VehicleCategoryConfig> configList;

    public VehicleCategoryConfigJsonRepository(@Value("${springlab.json.configs-file}") String filename) {
        storage = new JsonFileStorage<>(filename, new TypeToken<List<VehicleCategoryConfig>>() {}.getType());
        configList = storage.load();
    }

    @Override
    public List<VehicleCategoryConfig> getAll()
    {
        return configList.stream().map(c -> c.copy()).toList();
    }

    @Override
    public Optional<VehicleCategoryConfig> findByCategory(String category) {
        try {
            return Optional.ofNullable(configList.stream()
                    .filter(c -> c.getCategory().equals(category)).toList().get(0).copy());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

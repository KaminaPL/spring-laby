package org.example.services;

import org.example.models.VehicleCategoryConfig;
import org.example.repositories.VehicleCategoryConfigRepository;

import java.util.List;

public class VehicleCategoryConfigService
{
    private final VehicleCategoryConfigRepository configRepository;

    public VehicleCategoryConfigService(VehicleCategoryConfigRepository configRepository)
    {
        this.configRepository = configRepository;
    }

    public List<VehicleCategoryConfig> getAll()
    {
        return configRepository.getAll();
    }

    public VehicleCategoryConfig findByCategory(String category)
    {
        return configRepository.findByCategory(category)
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle category: " + category));
    }

    public boolean categoryExists(String category)
    {
        return configRepository.findByCategory(category).isPresent();
    }
}

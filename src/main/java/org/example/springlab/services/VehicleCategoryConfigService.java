package org.example.springlab.services;



import org.example.springlab.models.VehicleCategoryConfig;
import org.example.springlab.repositories.VehicleCategoryConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VehicleCategoryConfigService implements VehicleCategoryConfigServiceInterface{

    private final VehicleCategoryConfigRepository configRepository;

    public VehicleCategoryConfigService(VehicleCategoryConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    @Override
    public List<VehicleCategoryConfig> getAll()
    {
        return configRepository.getAll();
    }

    @Override
    public VehicleCategoryConfig findByCategory(String category) {
        return configRepository.findByCategory(category)
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle category: " + category));
    }

    @Override
    public boolean categoryExists(String category)
    {
        return configRepository.findByCategory(category).isPresent();
    }
}

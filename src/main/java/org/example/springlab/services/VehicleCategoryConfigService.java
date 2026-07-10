package org.example.springlab.services;



import org.example.springlab.models.VehicleCategoryConfig;
import org.example.springlab.repositories.VehicleCategoryConfigJpaRepositoryAdapter;
import org.example.springlab.repositories.VehicleCategoryConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VehicleCategoryConfigService implements VehicleCategoryConfigServiceInterface{

    private final VehicleCategoryConfigJpaRepositoryAdapter repository;

    public VehicleCategoryConfigService(VehicleCategoryConfigJpaRepositoryAdapter repository) {
        this.repository = repository;
    }

    @Override
    public List<VehicleCategoryConfig> findAll()
    {
        return repository.findAll();
    }

    @Override
    public VehicleCategoryConfig findByCategory(String category) {
        return repository.findByCategory(category)
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle category: " + category));
    }
}

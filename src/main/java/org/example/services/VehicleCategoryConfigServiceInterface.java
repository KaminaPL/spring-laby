package org.example.services;

import org.example.models.VehicleCategoryConfig;

import java.util.List;

public interface VehicleCategoryConfigServiceInterface {

    boolean categoryExists(String category);

    List<VehicleCategoryConfig> getAll();

    VehicleCategoryConfig findByCategory(String category);
}

package org.example.springlab.services;

import org.example.springlab.models.VehicleCategoryConfig;

import java.util.List;

public interface VehicleCategoryConfigServiceInterface {

    boolean categoryExists(String category);

    List<VehicleCategoryConfig> getAll();

    VehicleCategoryConfig findByCategory(String category);
}

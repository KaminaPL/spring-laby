package org.example.springlab.services;

import org.example.springlab.models.VehicleCategoryConfig;

import java.util.List;

public interface VehicleCategoryConfigServiceInterface {

    List<VehicleCategoryConfig> findAll();

    VehicleCategoryConfig findByCategory(String category);
}

package org.example.springlab.repositories;

import org.example.springlab.models.VehicleCategoryConfig;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Profile("jpa")
public interface VehicleCategoryConfigJpaRepository extends JpaRepository<VehicleCategoryConfig, Long> {

    Optional<VehicleCategoryConfig> findByCategory(String category);
}

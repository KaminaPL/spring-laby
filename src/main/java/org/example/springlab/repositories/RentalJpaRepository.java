package org.example.springlab.repositories;

import org.example.springlab.models.Rental;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Profile("jpa")
public interface RentalJpaRepository extends JpaRepository<Rental, String> {

    Optional<Rental> findByVehicleId(String vehicleId);

    Optional<Rental> findByUserId(String userId);

    Optional<Rental> findByIdAndReturnDateTimeIsNull(String id);

    Optional<Rental> findByUserIdAndReturnDateTimeIsNull(String userId);
}

package org.example.springlab.repositories;

import org.example.springlab.models.Rental;

import java.util.List;
import java.util.Optional;

public interface RentalRepository {

    boolean rentalExists(Rental rental);

    List<Rental> findAll();

    Optional<Rental> findById(String id);

    Optional<Rental> findByVehicleId(String vehicleId);

    Optional<Rental> findByUserId(String userId);

    Optional<Rental> findByUserIdAndReturnDateTimeIsNull(String userId);

    Optional<Rental> findByIdAndReturnDateTimeIsNull(String id);

    void add(Rental rental);

    void removeById(String id);
}

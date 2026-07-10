package org.example.springlab.services;

import org.example.springlab.models.Rental;

import java.util.List;
import java.util.Optional;

public interface RentalServiceInterface {

    boolean rentalExists(Rental rental);

    List<Rental> findAll();

    Rental findById(String id);

    Rental findByIdAndReturnDateTimeIsNull(String id);

    Rental findByUserIdAndReturnDateTimeIsNull(String userId);

    Rental findByVehicleId(String vehicleId);

    Rental findByUserId(String userId);

    void add(Rental rental);

    void removeById(String id);
}

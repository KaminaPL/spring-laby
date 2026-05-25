package org.example.services;

import org.example.models.Rental;

import java.util.List;

public interface RentalServiceInterface {

    boolean rentalWithUserIdExist(String userId);

    boolean rentalWithVehicleIdExists(String vehicleId);

    List<Rental> getAll();

    Rental findById(String id);

    Rental findByIdAndReturnDateIsNull(String id);

    Rental findByVehicleId(String vehicleId);

    Rental findByUserId(String userId);

    void add(Rental rental);

    void removeById(String id);

    void save();
}

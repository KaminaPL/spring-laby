package org.example.springlab.services;

import org.example.springlab.models.Rental;

import java.util.List;

public interface RentalServiceInterface {

    boolean activeRentalWithUserIdExists(String userId);

    boolean activeRentalWithVehicleIdExists(String vehicleId);

    List<Rental> getAll();

    Rental findById(String id);

    Rental findByIdAndReturnDateIsNull(String id);

    Rental findByVehicleId(String vehicleId);

    Rental findByUserId(String userId);

    void add(Rental rental);

    void removeById(String id);

    void save();
}

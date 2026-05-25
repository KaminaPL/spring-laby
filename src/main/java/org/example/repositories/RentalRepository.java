package org.example.repositories;

import org.example.models.Rental;

import java.util.List;
import java.util.Optional;

public interface RentalRepository
{
    List<Rental> getAll();

    Optional<Rental> findById(String id);

    Optional<Rental> findByVehicleId(String id);

    Optional<Rental> findByUserId(String id);

    Optional<Rental> findByIdAndReturnDateIsNull(String id);

    void add(Rental rental);

    void removeById(String id);

    void save();

}

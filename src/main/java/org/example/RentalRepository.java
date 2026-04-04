package org.example;

import java.util.List;
import java.util.Optional;

public interface RentalRepository
{
    List<Rental> getAll();

    Optional<Rental> findById(String id);

    Rental save(Rental rental);

    void deleteById(String id);

    Optional<Rental> findByIdAndReturnDateIsNull(String id);
}

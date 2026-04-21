package org.example.services;

import org.example.models.Rental;
import org.example.repositories.RentalJsonRepository;
import org.example.repositories.RentalRepository;

import java.util.List;

public class RentalService
{
    private RentalRepository rentalRepository;

    public RentalService(String filename)
    {
        rentalRepository = new RentalJsonRepository(filename);
    }

    public List<Rental> getAll()
    {
        return rentalRepository.getAll();
    }

    public Rental findById(String id)
    {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No rental with such id: " + id));
    }

    public Rental findByIdAndReturnDateIsNull(String id)
    {
        return rentalRepository.findByIdAndReturnDateIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("No active rental with such id: " + id));
    }

    public Rental findByVehicleId(String id)
    {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No rental with such vehicle id: " + id));
    }

    public void add(Rental rental)
    {
        rentalRepository.add(rental);
    }

    public void removeById(String id)
    {
        rentalRepository.removeById(id);
    }
}

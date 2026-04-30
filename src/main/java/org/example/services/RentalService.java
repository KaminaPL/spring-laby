package org.example.services;

import org.example.models.Rental;
import org.example.repositories.RentalRepository;

import java.util.List;

public class RentalService {

    private RentalRepository repository;


    public RentalService(RentalRepository repository) {
        this.repository = repository;
    }

    public boolean rentalWithUserIdExists(String id) {
        return repository.findByUserId(id).isPresent();
    }

    public boolean rentalWithVehicleIdExists(String id) {
        return repository.findByVehicleId(id).isPresent();
    }

    public List<Rental> getAll()
    {
        return repository.getAll();
    }

    public Rental findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No rental with such id: " + id));
    }

    public Rental findByIdAndReturnDateIsNull(String id) {
        return repository.findByIdAndReturnDateIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("No active rental with such id: " + id));
    }

    public Rental findByVehicleId(String id) {
        return repository.findByVehicleId(id)
                .orElseThrow(() -> new IllegalArgumentException("No rental with such vehicle id: " + id));
    }

    public Rental findByUserId(String id) {
        return repository.findByUserId(id)
                .orElseThrow(() -> new IllegalArgumentException("No rental with such user id: " + id));
    }

    public Rental add(Rental rental) {
        return repository.add(rental);
    }

    public void removeById(String id) {
        repository.removeById(id);
    }

    public void save() { repository.save(); }
}

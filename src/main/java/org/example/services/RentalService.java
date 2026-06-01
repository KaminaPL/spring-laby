package org.example.services;

import org.example.models.Rental;
import org.example.models.Vehicle;
import org.example.repositories.RentalRepository;

import java.util.List;
import java.util.Optional;

public class RentalService implements RentalServiceInterface {

    private RentalRepository repository;


    public RentalService(RentalRepository repository) {
        this.repository = repository;
    }

    public boolean activeRentalWithUserIdExists(String userId) {
        return repository.findByUserId(userId).isPresent();
    }

    public boolean activeRentalWithVehicleIdExists(String vehicleId) {
        return repository.findByVehicleId(vehicleId).isPresent();
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

    public Rental findByVehicleId(String vehicleId) {
        return repository.findByVehicleId(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("No rental with such vehicle id: " + vehicleId));
    }

    public Rental findByUserId(String userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No rental with such user id: " + userId));
    }

    public void add(Rental rental)
    {
        repository.add(rental);
    }

    public void removeById(String id)
    {
        repository.removeById(id);
    }

    public void save() { repository.save(); }
}

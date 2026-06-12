package org.example.springlab.services;


import org.example.springlab.models.Rental;
import org.example.springlab.repositories.RentalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class RentalService implements RentalServiceInterface {

    private RentalRepository repository;


    public RentalService(RentalRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean activeRentalWithUserIdExists(String userId) {
        return repository.findByUserId(userId).isPresent();
    }

    @Override
    public boolean activeRentalWithVehicleIdExists(String vehicleId) {
        return repository.findByVehicleId(vehicleId).isPresent();
    }

    @Override
    public List<Rental> getAll()
    {
        return repository.getAll();
    }

    @Override
    public Rental findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No rental with such id: " + id));
    }

    @Override
    public Rental findByIdAndReturnDateIsNull(String id) {
        return repository.findByIdAndReturnDateIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("No active rental with such id: " + id));
    }

    @Override
    public Rental findByVehicleId(String vehicleId) {
        return repository.findByVehicleId(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("No rental with such vehicle id: " + vehicleId));
    }

    @Override
    public Rental findByUserId(String userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No rental with such user id: " + userId));
    }

    @Override
    public void add(Rental rental)
    {
        repository.add(rental);
    }

    @Override
    public void removeById(String id)
    {
        repository.removeById(id);
    }

    @Override
    public void save() { repository.save(); }
}

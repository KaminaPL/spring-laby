package org.example.springlab.repositories;

import org.example.springlab.models.Rental;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("jpa")
public class RentalJpaRepositoryAdapter implements RentalRepository {

    private final RentalJpaRepository delegate;

    public RentalJpaRepositoryAdapter(RentalJpaRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean rentalExists(Rental rental) {
        return delegate.exists(Example.of(rental));
    }

    @Override
    public List<Rental> findAll() {
        return delegate.findAll();
    }

    @Override
    public Optional<Rental> findById(String id) {
        return delegate.findById(id);
    }

    @Override
    public Optional<Rental> findByVehicleId(String vehicleId) {
        return delegate.findByVehicleId(vehicleId);
    }

    @Override
    public Optional<Rental> findByUserId(String userId) {
        return delegate.findByUserId(userId);
    }

    @Override
    public Optional<Rental> findByIdAndReturnDateTimeIsNull(String id) {
        return delegate.findByIdAndReturnDateTimeIsNull(id);
    }

    @Override
    public Optional<Rental> findByUserIdAndReturnDateTimeIsNull(String userId) {
        return delegate.findByUserIdAndReturnDateTimeIsNull(userId);
    }

    @Override
    public void add(Rental rental) {
        if(rental.getId() == null || rental.getId().isEmpty()) {
            rental.setId(UUID.randomUUID().toString());
            while(delegate.findById(rental.getId()).isPresent()) {
                rental.setId(UUID.randomUUID().toString());
            }
        }
        delegate.save(rental);
    }

    @Override
    public void removeById(String id) {
        delegate.deleteById(id);
    }
}

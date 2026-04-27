package org.example.repositories;

import com.google.gson.reflect.TypeToken;
import org.example.db.JsonFileStorage;
import org.example.models.Rental;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class RentalJsonRepository implements RentalRepository {

    private final JsonFileStorage<Rental> storage;
    private List<Rental> rentalList;

    public RentalJsonRepository(String filename) {
        storage = new JsonFileStorage<>(filename, new TypeToken<List<Rental>>() {}.getType());
        rentalList = storage.load();
    }

    @Override
    public List<Rental> getAll()
    {
        return rentalList.stream().map(Rental::copy).toList();
    }

    @Override
    public Optional<Rental> findById(String id) {
        try {
            Rental rental = rentalList.stream().filter(r -> r.getId().equals(id)).toList().getFirst().copy();
            return Optional.of(rental);
        } catch(NoSuchElementException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Rental> findByVehicleId(String id) {
        try {
            Rental rental = rentalList.stream().filter(r -> r.getVehicleId().equals(id)).toList().getFirst().copy();
            return Optional.of(rental);
        } catch(NoSuchElementException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Rental> findByUserId(String id) {
        try {
            Rental rental = rentalList.stream().filter(r -> r.getUserId().equals(id)).toList().getFirst().copy();
            return Optional.of(rental);
        } catch(NoSuchElementException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Rental> findByIdAndReturnDateIsNull(String id) {
        try {
            Rental rental = rentalList.stream().filter(r -> r.getId().equals(id) && r.isActive()).toList().getFirst().copy();
            return Optional.of(rental);
        } catch(NoSuchElementException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void add(Rental rental) {
        List<Rental> appendedList = new ArrayList<>(rentalList);
        appendedList.add(rental);
        rentalList = appendedList;
    }

    @Override
    public void removeById(String id) {
        rentalList = rentalList.stream().filter(r -> r.getId().compareTo(id) != 0).toList();
    }

    @Override
    public void save()
    {
        storage.save(rentalList);
    }
}

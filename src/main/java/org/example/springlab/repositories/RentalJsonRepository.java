package org.example.springlab.repositories;

import com.google.gson.reflect.TypeToken;
import org.example.springlab.db.JsonFileStorage;
import org.example.springlab.models.Rental;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
@Profile("json")
public class RentalJsonRepository implements RentalRepository {

    private final JsonFileStorage<Rental> storage;
    private List<Rental> rentalList;

    public RentalJsonRepository(@Value("${springlab.json.rentals-file}") String filename) {
        storage = new JsonFileStorage<>(filename, new TypeToken<List<Rental>>() {}.getType());
        rentalList = storage.load();
    }

    @Override
    public List<Rental> getAll()
    {
        return rentalList.stream().filter(Rental::isActive).map(Rental::copy).toList();
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
            Rental rental = rentalList.stream().filter(r -> (r.getVehicle().getId().equals(id) && r.isActive()))
                    .toList().getFirst().copy();
            return Optional.of(rental);
        } catch(NoSuchElementException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

   @Override
    public Optional<Rental> findByUserId(String id) {
        try {
            Rental rental = rentalList.stream().filter(r -> (r.getUser().getId().equals(id) && r.isActive()))
                    .toList().getFirst().copy();
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
        if(rental.getId().isBlank()) {
            rental.setId(UUID.randomUUID().toString());
            while(findById(rental.getId()).isPresent()) rental.setId(UUID.randomUUID().toString());
        } else {
            removeById(rental.getId());
        }
        List<Rental> appendedList = new ArrayList<>(rentalList);
        appendedList.add(rental);
        rentalList = appendedList;
    }

   @Override
    public void removeById(String id) {
        findById(id).ifPresent(
                r -> {
                    r.setReturnDateTime(LocalDate.now().toString());
                    add(r);
                }
        );
    }

    @Override
    public void save() {
        storage.save(rentalList);
    }

}

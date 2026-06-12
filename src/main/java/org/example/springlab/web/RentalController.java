package org.example.springlab.web;

import org.example.springlab.models.Rental;
import org.example.springlab.services.RentalServiceInterface;
import org.example.springlab.services.UserServiceInterface;
import org.example.springlab.services.VehicleServiceInterface;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalServiceInterface rentalService;
    private final UserServiceInterface userService;
    private final VehicleServiceInterface vehicleService;

    public RentalController(
            RentalServiceInterface rentalService,
            UserServiceInterface userService,
            VehicleServiceInterface vehicleService
    ) {
        this.rentalService = rentalService;
        this.userService = userService;
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public List<Rental> getAll() {
        return rentalService.getAll();
    }

    @GetMapping("/users/{userId}")
    public List<Rental> userRentals(@PathVariable String userId) {
        return rentalService.getAll().stream().filter(r -> r.getUser().getId().equals(userId)).toList();
    }

    @PostMapping("/users/{userId}/rent/{vehicleId}")
    public Rental rentVehicle(@PathVariable String userId, @PathVariable String vehicleId) {
        if(rentalService.activeRentalWithUserIdExists(userId)) {
            throw new IllegalStateException("Cannot rent vehicle, user hasn't returned one yet.");
        }
        if(rentalService.activeRentalWithVehicleIdExists(vehicleId)) {
            throw new IllegalStateException("Cannot rent vehicle, it has been already rented.");
        }
        rentalService.add(new Rental("",
                vehicleService.findById(vehicleId),
                userService.findById(userId),
                LocalDateTime.now().toString(),
                "")
        );
        return rentalService.findByUserId(userId);
    }

    @PostMapping("/users/{userId}/return")
    public Rental returnVehicle(@PathVariable String userId) {
        if(!rentalService.activeRentalWithUserIdExists(userId)) {
            throw new IllegalStateException("No rented vehicles found under that user id: " + userId);
        }
        Rental rental = rentalService.findByUserId(userId);
        rentalService.removeById(rental.getId());
        return rental;
    }

}

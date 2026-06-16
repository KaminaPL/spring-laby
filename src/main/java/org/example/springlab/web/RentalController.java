package org.example.springlab.web;

import org.example.springlab.models.Rental;
import org.example.springlab.dto.RentalRequest;
import org.example.springlab.models.User;
import org.example.springlab.models.Vehicle;
import org.example.springlab.services.RentalServiceInterface;
import org.example.springlab.services.UserServiceInterface;
import org.example.springlab.services.VehicleServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PostMapping("/rent")
    public ResponseEntity<Rental> rentVehicle(
            @RequestBody RentalRequest rentalRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String login = userDetails.getUsername();
        User user = userService.findByLogin(login);
        Vehicle vehicle = vehicleService.findById(rentalRequest.getVehicleId());
        Rental rental = new Rental("", vehicle, user, LocalDateTime.now().toString(), "");
        if(!rentalService.activeRentalWithUserIdExists(user.getId()) &&
                !rentalService.activeRentalWithVehicleIdExists(vehicle.getId())) {
            rentalService.add(rental);
            return ResponseEntity.status(HttpStatus.CREATED).body(rental);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }


    @PostMapping("/return")
    public ResponseEntity<Rental> returnVehicle(
            @RequestBody RentalRequest rentalRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String login = userDetails.getUsername();
        User user = userService.findByLogin(login);
        if(rentalService.activeRentalWithUserIdExists(user.getId())) {
            Rental rental = rentalService.findByUserId(user.getId());
            rentalService.removeById(rental.getId());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}

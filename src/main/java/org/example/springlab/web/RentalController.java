package org.example.springlab.web;

import org.example.springlab.dto.RentalRequest;
import org.example.springlab.models.Payment;
import org.example.springlab.models.Rental;
import org.example.springlab.models.User;
import org.example.springlab.models.Vehicle;
import org.example.springlab.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RentalController {

    private final RentalServiceInterface rentalService;
    private final UserServiceInterface userService;
    private final VehicleServiceInterface vehicleService;
    private final PaymentServiceInterface paymentService;


    public RentalController(
            RentalServiceInterface rentalService,
            UserServiceInterface userService,
            VehicleServiceInterface vehicleService,
            PaymentServiceInterface paymentService
    ) {
        this.rentalService = rentalService;
        this.userService = userService;
        this.vehicleService = vehicleService;
        this.paymentService = paymentService;
    }


    @GetMapping("/rentals")
    public List<Rental> findAll() {
        return rentalService.findAll();
    }

    @PostMapping("/rent")
    public ResponseEntity<Rental> rentVehicle(
            @RequestBody RentalRequest rentalRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String login = userDetails.getUsername();
        User user = userService.findByLogin(login);
        Vehicle vehicle = vehicleService.findById(rentalRequest.vehicleId());
        Rental rental = new Rental("", vehicle, user, LocalDateTime.now().toString(), "");
        List<Rental> userActiveRentals = rentalService.findAll().stream()
                .filter(r -> r.isActive() && r.getUser().getId().equals(user.getId())).toList();
        if(!userActiveRentals.isEmpty()) {
            throw new IllegalStateException("Cannot rent vehicle, user has not returned one previously.");
        }
        if(vehicle.isRented()) {
            throw new IllegalStateException("Cannot rent that vehicle because it has been already rented.");
        }
        rentalService.add(rental);
        vehicle.setRented(true);
        vehicle.setAddress(user.getAddress());
        vehicleService.add(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(rental);
    }

    @PostMapping("/return")
    public String returnVehicle(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String login = userDetails.getUsername();
        User user = userService.findByLogin(login);
        Rental rental = rentalService.findByUserIdAndReturnDateTimeIsNull(user.getId());
        Vehicle vehicle = vehicleService.findById(rental.getVehicle().getId());
        if(!vehicle.getAddress().equals("main")) {
            throw new IllegalStateException("Cannot return vehicle because it's not stationed in allowed place.");
        }
        rental.setReturnDateTime(LocalDateTime.now().toString());
        rentalService.add(rental);
        vehicle.setRented(false);
        vehicleService.add(vehicle);
        return ResponseEntity.ok("Successfully returned vehicle.").getBody();
    }

    private double determinePaymentAmount(Rental rental, Vehicle vehicle) {
        LocalDateTime start = LocalDateTime.parse(rental.getRentDateTime());
        LocalDateTime end = LocalDateTime.parse(rental.getReturnDateTime());
        return Math.ceil(ChronoUnit.DAYS.between(start, end)) * vehicle.getPrice();
    }
}

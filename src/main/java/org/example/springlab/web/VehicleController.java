package org.example.springlab.web;

import org.example.springlab.VehicleValidator;
import org.example.springlab.models.Vehicle;
import org.example.springlab.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleServiceInterface vehicleService;
    private final RentalServiceInterface rentalService;
    private final VehicleValidator vehicleValidator;

    public VehicleController(
            VehicleServiceInterface vehicleService,
            RentalServiceInterface rentalService,
            VehicleCategoryConfigServiceInterface configService
    ) {
        this.vehicleService = vehicleService;
        this.rentalService = rentalService;
        this.vehicleValidator = new VehicleValidator(configService);
    }

    @GetMapping
    public List<Vehicle> getAll(
            @RequestParam(name = "available", required = false, defaultValue = "false") boolean available
    ) {
        List<Vehicle> vehicleList = vehicleService.getAll();
        if(available) {
            vehicleList = vehicleList.stream().filter(v -> !rentalService.activeRentalWithVehicleIdExists(v.getId())).toList();
        }
        return vehicleList;
    }

    @GetMapping("/{id}")
    public Vehicle findById(@PathVariable String id) {
        return vehicleService.findById(id);
    }

    @PostMapping
    public void add(@RequestBody Vehicle vehicle) {
        vehicleValidator.validate(vehicle);
        vehicleService.add(vehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable String id) {
        vehicleService.removeById(id);
        return ResponseEntity.noContent().build();
    }
}

package org.example.services;

import org.example.VehicleValidator;
import org.example.models.Vehicle;
import org.example.repositories.VehicleCategoryConfigJsonRepository;
import org.example.repositories.VehicleJsonRepository;
import org.example.repositories.VehicleRepository;

public class VehicleService
{
    private final VehicleRepository vehicleRepository;
    private final VehicleValidator vehicleValidator;

    public VehicleService(String vehiclesRepositoryFilename, String vehicleConfigurationsFilename)
    {
        vehicleRepository = new VehicleJsonRepository(vehicleConfigurationsFilename);
        vehicleValidator = new VehicleValidator(
                new VehicleCategoryConfigService(
                        new VehicleCategoryConfigJsonRepository(vehicleConfigurationsFilename)
                )
        );
    }

    public Vehicle findById(String id)
    {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No vehicle with such id: " + id));
    }

    public void add(Vehicle vehicle)
    {
        try
        {
            vehicleValidator.validate(vehicle);
            vehicleRepository.add(vehicle);
        }
        catch(IllegalStateException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Vehicle is null");
        }
        catch(IllegalArgumentException e)
        {
            e.printStackTrace();
            throw new IllegalArgumentException("Vehicle attribute is missing or invalid");
        }
    }

    public void removeById(String id)
    {
        vehicleRepository.removeById(id);
    }
}

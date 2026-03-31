package org.example;

import java.util.List;

public interface IVehicleRepository
{
    void add(Vehicle vehicle);

    Integer remove(Integer id);

    Vehicle getVehicle(Integer id);

    Integer rentVehicle(Integer id);

    Integer returnVehicle(Integer id);

    List<Vehicle> getVehicles();

}

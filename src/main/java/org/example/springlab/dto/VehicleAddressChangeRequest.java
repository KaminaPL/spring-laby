package org.example.springlab.dto;

public record VehicleAddressChangeRequest(
        String vehicleId,
        String address
) {
}

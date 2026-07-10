package org.example.springlab.dto;

import org.example.springlab.models.Rental;
import org.example.springlab.models.User;

import java.util.List;

public record UserDataResponse(
        User user,
        List<Rental> rentalList
) {
}

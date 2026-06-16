package org.example.springlab.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalRequest {

    private String id;
    private String vehicleId;
}

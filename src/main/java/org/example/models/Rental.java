package org.example.models;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of="id")
@ToString
@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @Column(name = "vehicle_id", nullable = false)
    private String vehicleId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "rent_date", nullable = false)
    private String rentDateTime;

    @Column(name = "return_date")
    private String returnDateTime;

    public Rental copy() {
        return Rental.builder()
                .id(id)
                .vehicleId(vehicleId)
                .userId(userId)
                .rentDateTime(rentDateTime)
                .returnDateTime(returnDateTime)
                .build();
    }

    public boolean isActive()
    {
        return returnDateTime == null || returnDateTime.isEmpty();
    }
}

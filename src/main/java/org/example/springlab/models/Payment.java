package org.example.springlab.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "payments")
public class Payment {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @ManyToOne()
    @JoinColumn(name = "rental_id", nullable = false)
    @JsonIgnore
    private Rental rentalId;

    @Column(nullable = false)
    private double amount;

    @JoinColumn(name = "date_issued", nullable= false)
    private String dateIssued;

    @JoinColumn(name = "date_paid")
    private String datePaid;

}

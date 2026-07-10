package org.example.springlab.repositories;

import org.example.springlab.models.Payment;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Profile("jpa")
public interface PaymentJpaRepository extends JpaRepository<Payment, String> {

    Optional<Payment> findByRentalId(String rentalId);

    Optional<Payment> findByRentalIdAndDatePaidIsNull(String rentalId);
}

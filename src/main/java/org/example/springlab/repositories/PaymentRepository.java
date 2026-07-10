package org.example.springlab.repositories;

import org.example.springlab.models.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {

    List<Payment> findAll();

    Optional<Payment> findById(String id);

    Optional<Payment> findByRentalId(String rentalId);

    Optional<Payment> findByRentalIdAndDatePaidIsNull(String rentalId);

    void add(Payment payment);

    void removeById(String id);
}

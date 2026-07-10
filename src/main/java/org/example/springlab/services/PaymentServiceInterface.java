package org.example.springlab.services;

import org.example.springlab.models.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentServiceInterface {

    List<Payment> findAll();

    Payment findById(String id);

    Payment findByRentalId(String rentalId);

    Payment findByRentalIdAndDatePaidIsNull(String rentalId);

    void add(Payment payment);

    void removeById(String id);

}

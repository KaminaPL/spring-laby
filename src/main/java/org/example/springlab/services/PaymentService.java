package org.example.springlab.services;


import org.example.springlab.models.Payment;
import org.example.springlab.repositories.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentService implements PaymentServiceInterface {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Payment> findAll() {
        return repository.findAll();
    }

    @Override
    public Payment findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No payment with such id recorded."));
    }

    @Override
    public Payment findByRentalId(String rentalId) {
        return repository.findByRentalId(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("No payment with such id recorded."));
    }

    @Override
    public Payment findByRentalIdAndDatePaidIsNull(String rentalId) {
        return repository.findByRentalIdAndDatePaidIsNull(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("No payment with such id recorded."));
    }

    @Override
    public void add(Payment payment) {
        repository.add(payment);
    }

    @Override
    public void removeById(String id) {
        repository.removeById(id);
    }
}

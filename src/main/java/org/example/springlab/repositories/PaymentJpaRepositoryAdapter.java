package org.example.springlab.repositories;

import org.example.springlab.models.Payment;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Profile("jpa")
@Repository
public class PaymentJpaRepositoryAdapter implements PaymentRepository {

    private final PaymentJpaRepository delegate;

    public PaymentJpaRepositoryAdapter(PaymentJpaRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Payment> findAll() {
        return delegate.findAll();
    }

    @Override
    public Optional<Payment> findById(String id) {
        return delegate.findById(id);
    }

    @Override
    public Optional<Payment> findByRentalId(String rentalId) {
        return delegate.findByRentalId(rentalId);
    }

    @Override
    public Optional<Payment> findByRentalIdAndDatePaidIsNull(String rentalId) {
        return delegate.findByRentalIdAndDatePaidIsNull(rentalId);
    }

    @Override
    public void add(Payment payment) {
        if(payment.getId() == null || payment.getId().isEmpty()) {
            payment.setId(UUID.randomUUID().toString());
            while(delegate.findById(payment.getId()).isPresent()) {
                payment.setId(UUID.randomUUID().toString());
            }
        }
        delegate.save(payment);
    }

    @Override
    public void removeById(String id) {
        delegate.deleteById(id);
    }
}

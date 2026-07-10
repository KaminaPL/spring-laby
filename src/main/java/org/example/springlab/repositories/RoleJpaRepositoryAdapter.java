package org.example.springlab.repositories;

import org.example.springlab.models.Role;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Profile("jpa")
@Repository
public class RoleJpaRepositoryAdapter implements RoleRepository {

    private final RoleJpaRepository delegate;

    public RoleJpaRepositoryAdapter(RoleJpaRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return delegate.findByName(name);
    }
}

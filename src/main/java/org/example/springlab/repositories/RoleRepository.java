package org.example.springlab.repositories;

import org.example.springlab.models.Role;

import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findByName(String name);
}

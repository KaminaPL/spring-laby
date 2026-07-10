package org.example.springlab.services;


import org.example.springlab.models.Role;
import org.example.springlab.models.User;
import org.example.springlab.repositories.RoleJpaRepositoryAdapter;
import org.example.springlab.repositories.UserJpaRepositoryAdapter;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class AuthService implements AuthServiceInterface {

    private final UserJpaRepositoryAdapter userRepository;
    private final RoleJpaRepositoryAdapter roleRepository;

    public AuthService(UserJpaRepositoryAdapter userRepository, RoleJpaRepositoryAdapter roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User authenticate(String login, String password) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Login failed: no user with such login: " + login));
        if(!BCrypt.checkpw(password, user.getPassword())) {
            throw new IllegalArgumentException("Login failed: passwords don't match");
        }
        return user;
    }

    @Override
    public void register(String login, String password, String passwordConfirmation, String address) {
        if(password.compareTo(passwordConfirmation) != 0) {
            throw new IllegalArgumentException("Register failed: passwords don't match");
        } else if(userRepository.findByLogin(login).isEmpty()) {
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ROLE_USER").get());
            User user = new User(
                    null,
                    login,
                    BCrypt.hashpw(password, BCrypt.gensalt()),
                    address,
                    roles
            );
            userRepository.add(user);
        } else {
            throw new IllegalArgumentException("Register failed: user with login `" + login + "` already exists");
        }
    }
}

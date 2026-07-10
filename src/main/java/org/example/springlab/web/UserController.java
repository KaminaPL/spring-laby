package org.example.springlab.web;

import org.example.springlab.dto.UserDataResponse;
import org.example.springlab.models.Rental;
import org.example.springlab.models.User;
import org.example.springlab.services.RentalServiceInterface;
import org.example.springlab.services.UserServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@RestController()
@RequestMapping("/api")
public class UserController {

    private final UserServiceInterface userService;
    private final RentalServiceInterface rentalService;

    public UserController(UserServiceInterface userService, RentalServiceInterface rentalService) {
        this.userService = userService;
        this.rentalService = rentalService;
    }

    @GetMapping("/users")
    public List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public UserDataResponse findById(@PathVariable String id) {
        User user = userService.findById(id);
        List<Rental> rentalList = rentalService.findAll().stream().filter(r -> r.getUser().getId().equals(id)).toList();
        return new UserDataResponse(user, rentalList);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDataResponse> currentUserRentals(@AuthenticationPrincipal UserDetails userDetails) {
        String login = userDetails.getUsername();
        User user = userService.findByLogin(login);
        if(user != null) {
            List<Rental> userRentals = rentalService.findAll().stream().filter(r -> r.getUser().getId().equals(user.getId())).toList();
            UserDataResponse response = new UserDataResponse(
                    user,
                    userRentals
            );
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new UserDataResponse(
                        new User("", "", "", "", new HashSet<>()),
                        Collections.emptyList()
                )
        );
    }


}

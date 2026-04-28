package org.example;

import java.time.LocalDate;
import java.util.*;

import lombok.Getter;
import lombok.Setter;
import org.example.models.Rental;
import org.example.models.User;
import org.example.models.Vehicle;
import org.example.models.VehicleCategoryConfig;
import org.example.repositories.*;
import org.example.services.*;

public class Console {

    public enum Status {
        ONGOING,
        TERMINATED
    }

    @Setter
    @Getter
    private Status status;
    private User currentUser;
    private final VehicleCategoryConfigService configService;
    private final VehicleService vehicleService;
    private final RentalService rentalService;
    private final UserService userService;
    private final AuthService authService;

    public Console() {
        status = Status.ONGOING;
        currentUser = null;
        configService =  new VehicleCategoryConfigService(
                new VehicleCategoryConfigJsonRepository("/home/bartosz/code/projects/java/Lab3/vehicle-configs.json")
        );
        vehicleService = new VehicleService(
                new VehicleJsonRepository("/home/bartosz/code/projects/java/Lab3/vehicles.json"),
                new VehicleValidator(configService)
        );
        rentalService = new RentalService(
                new RentalJsonRepository("/home/bartosz/code/projects/java/Lab3/rentals.json")
        );
        userService = new UserService(
                new UserJsonRepository("/home/bartosz/code/projects/java/Lab3/users.json")
        );
        authService = new AuthService(this.userService);
    }

    public void readCommand(String command) {
        System.out.print('\n');
        if(currentUser == null) verifyUser(command);
        else if(currentUser.getRole().equals("User")) parseCommandAsUser(command);
        else parseCommandAsAdmin(command);
        System.out.print('\n');
    }

    private void verifyUser(String command) {
        Scanner sc = new Scanner(System.in);
        switch(command) {
            case "login" -> {
                String login, password;
                System.out.print("Login: ");
                login = sc.nextLine();
                System.out.print("Password: ");
                password = sc.nextLine();
                try {
                    currentUser = authService.authenticate(login, password);
                } catch(IllegalArgumentException e) {
                    e.printStackTrace();
                    System.out.println("Log in failed, please try again.");
                }
            }
            case "register" -> {
                try {
                    String login, password, passwordConfirmation;
                    System.out.print("Login: ");
                    login = sc.nextLine();
                    System.out.print("Password: ");
                    password = sc.nextLine();
                    System.out.print("Confirm password: ");
                    passwordConfirmation = sc.nextLine();
                    authService.register(login, password, passwordConfirmation);
                    System.out.println("Successfully registered new user! Please Log in now.");
                } catch(IllegalArgumentException e) {
                    e.printStackTrace();
                    System.out.println("Error, passwords don't match.");
                }
            }
            case "help" -> {
                System.out.println("login - sign into your account");
                System.out.println("register - create new account");
                System.out.println("leave - exit program");
            }
            default -> System.out.println("Login or create new account first. If you need help type `help`.");
        }
    }

    private void parseCommandAsUser(String command) {
        switch(command) {
            case "items" -> showNotRentedItems();
            case "rent" -> rentItem();
            case "return" -> returnItem();
            case "leave" -> this.setStatus(Status.TERMINATED);
            case "help" -> {
                System.out.println("items - list all items");
                System.out.println("rent - rent an item");
                System.out.println("return - return an item that you've rented");
                System.out.println("leave - exit program");
            }
            default -> System.out.println("Unrecognized command. Please type `help` to get list of commands.");
        }
    }

    private void parseCommandAsAdmin(String command) {
        switch(command) {
            case "items" -> showItems();
            case "add" -> addItem();
            case "rmv" -> removeItem();
            case "rmu" -> removeUser();
            case "leave" -> this.setStatus(Status.TERMINATED);
            case "help" -> {
                System.out.println("items - list all items");
                System.out.println("add - add an item");
                System.out.println("rmv - remove vehicle");
                System.out.println("rmu - remove user");
                System.out.println("leave - exit program");
            }
            default -> System.out.println("Unrecognized command. Please type `help` to get list of commands.");
        }
    }

    private void showItems() {
        List<Vehicle> vehicleList = vehicleService.getAll();
        vehicleList.forEach(v -> {
            try {
                Rental rental = rentalService.findByVehicleId(v.getId());
                System.out.println("id: " + v.getId());
                if(rental.isActive()) System.out.println("rented: false");
                else System.out.println("rented: true");
                System.out.println("category: " + v.getCategory());
                System.out.println("brand: " + v.getBrand());
                System.out.println("model: " + v.getModel());
                System.out.println("year: " + v.getYear());
                System.out.println("price: " + v.getPrice());
                v.getAttributes().forEach((key, value) -> System.out.println(key + ": " + value.toString()));
                System.out.print("\n");
            } catch(IllegalArgumentException e) {
                e.printStackTrace();
            }
        });
    }

    private void showNotRentedItems() {
        List<Vehicle> vehicleList = vehicleService.getAll();
        vehicleList.forEach(v -> {
            try {
                Rental rental = rentalService.findByVehicleId(v.getId());
                if(rental.isActive()) {
                    System.out.println("id: " + v.getId());
                    System.out.println("category: " + v.getCategory());
                    System.out.println("brand: " + v.getBrand());
                    System.out.println("model: " + v.getModel());
                    System.out.println("year: " + v.getYear());
                    System.out.println("price: " + v.getPrice());
                    v.getAttributes().forEach((key, value) -> System.out.println(key + ": " + value.toString()));
                    System.out.print("\n\n");
                }
            } catch(IllegalArgumentException e) {
                e.printStackTrace();
            }
        });
    }

    private void rentItem() {
        List<Rental> rentals = rentalService.getAll();
        rentals = rentals.stream().filter(it -> it.getUserId().equals(currentUser.getId())).toList();
        if(!rentals.isEmpty()) {
            System.out.println("Can't rent another vehicle. You still haven't returned one.");
            return;
        }
        String id;
        Scanner sc = new Scanner(System.in);
        System.out.print("Vehicle id: ");
        id = sc.nextLine();
        try {
            Rental foundRental = rentalService.findByVehicleId(id);
            if(foundRental.isActive()) {
                foundRental.setUserId(currentUser.getId());
                foundRental.setRentDateTime(LocalDate.now().toString());
                foundRental.setReturnDateTime(LocalDate.now().plusMonths(2).toString());
                rentalService.removeById(foundRental.getId());
                rentalService.add(foundRental);
                rentalService.save();
                System.out.println("Item rented.");
            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void returnItem() {
        try {
            List<Rental> rentals = rentalService.getAll();
            Rental rental = rentals.stream().filter(it -> it.getUserId().equals(currentUser.getId())).toList().getFirst();
            rental.setUserId("");
            rental.setRentDateTime("");
            rental.setReturnDateTime("");
            rentalService.removeById(rental.getId());
            rentalService.add(rental);
            rentalService.save();
            System.out.println("Item returned.");
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("No rented item found.");
        }
    }

    private void addItem() {
        String category, brand, model, year, price;
        Scanner sc = new Scanner(System.in);
        System.out.print("Category: ");
        category = sc.nextLine();
        System.out.print("Brand: ");
        brand = sc.nextLine();
        System.out.print("Model: ");
        model = sc.nextLine();
        System.out.print("Year: ");
        year = sc.nextLine();
        System.out.print("Price: ");
        price = sc.nextLine();
        String vehicleId = UUID.randomUUID().toString();
        String rentalId = UUID.randomUUID().toString();
        Map<String, Object> attributes = new HashMap<>();
        VehicleCategoryConfig config = configService.findByCategory(category);
        Map<String, String> expectedAttributes = config.getAttributes();
        expectedAttributes.forEach((key, value) -> {
            System.out.print("Enter value for attribute `" + key + "`: ");
            switch(value) {
                case "string" -> attributes.put(key, sc.nextLine());
                case "number" -> attributes.put(key, sc.nextDouble());
                case "boolean" -> attributes.put(key, sc.nextBoolean());
                case "integer" -> attributes.put(key, sc.nextInt());
            }
        });
        Vehicle vehicle = new Vehicle(vehicleId, category, brand, model, Integer.parseInt(year), Double.parseDouble(price), attributes);
        Rental rental = new Rental(rentalId, vehicleId, "", "", "");
        try {
            vehicleService.add(vehicle);
            rentalService.add(rental);
            vehicleService.save();
            rentalService.save();
        } catch(IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void removeItem() {
        String id;
        Scanner sc = new Scanner(System.in);
        System.out.print("Vehicle id: ");
        id = sc.nextLine();
        try {
            Rental rental = rentalService.findByVehicleId(id);
            if(rental.isActive()) {
                vehicleService.removeById(id);
                rentalService.removeById(rental.getId());
                vehicleService.save();
                rentalService.save();
            } else {
                System.out.println("Can't remove item, it has not been returned yet.");
            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("No vehicle found with such id.");
        }
    }

    private void removeUser() {
        String login;
        Scanner sc = new Scanner(System.in);
        System.out.print("User login: ");
        login = sc.nextLine();
        if(!userService.userExist(login)) {
            System.out.println("User not found.");
            return;
        }
        User user = userService.findByLogin(login);
        if(rentalService.rentalWithUserIdExist(user.getId())) {
            System.out.println("Cannot remove user. User has not returned a vehicle yet.");
            return;
        }
        userService.removeByLogin(login);
        userService.save();
    }
}
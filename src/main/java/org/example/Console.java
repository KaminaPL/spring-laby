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
import org.example.services.AuthService;
import org.example.services.VehicleCategoryConfigService;

public class Console
{
    public enum Status
    {
        ONGOING, TERMINATED
    }

    @Setter
    @Getter
    private Status status;
    private User user;
    private VehicleRepository vehicleRepository;
    private RentalRepository rentalRepository;
    private VehicleCategoryConfigService configService;
    private AuthService auth;

    public Console()
    {
        status = Status.ONGOING;
        vehicleRepository = new VehicleJsonRepository("/home/bartosz/code/projects/java/Lab3/vehicles.json");
        rentalRepository = new RentalJsonRepository("/home/bartosz/code/projects/java/Lab3/rentals.json");
        configService =  new VehicleCategoryConfigService(
                new VehicleCategoryConfigJsonRepository("/home/bartosz/code/projects/java/Lab3/vehicle-configs.json")
        );
        auth = new AuthService();
    }

    public void readCommand(String command)
    {
        System.out.print('\n');
        if(user == null) verifyUser(command);
        else if(user.getRole().equals("USER")) parseCommandAsUser(command);
        else parseCommandAsAdmin(command);
        System.out.print('\n');
    }

    private void verifyUser(String command)
    {
        Scanner sc = new Scanner(System.in);
        switch(command)
        {
            case "login" ->
            {
                String login , password;
                System.out.print("Login: ");
                login = sc.nextLine();
                System.out.print("Password: ");
                password = sc.nextLine();
                Optional<User> loggedUser = auth.authenticate(login, password);
                if(loggedUser.isPresent()) user = loggedUser.get();
                else System.out.println("Error occurred while trying to log in. Try again.");
            }
            case "register" ->
            {
                try
                {
                    String login, password, passwordConfirmation;
                    System.out.print("Login: ");
                    login = sc.nextLine();
                    System.out.print("Password: ");
                    password = sc.nextLine();
                    System.out.print("Confirm password: ");
                    passwordConfirmation = sc.nextLine();
                    auth.register(login, password, passwordConfirmation);
                    System.out.println("Successfully registered new user! Please Log in now.");
                }
                catch(IllegalStateException e)
                {
                    e.printStackTrace();
                    System.out.println("Error, passwords don't match.");
                }
            }
            case "help" ->
            {
                System.out.println("login (name) (password) - sign into your account");
                System.out.println("register (name) (password) (confirm password) - create new account");
            }
            default -> System.out.println("Login or create new account first. If you need help type `help`.");
        }
    }

    private void parseCommandAsUser(String command)
    {
        Scanner sc = new Scanner(System.in);
        switch(command)
        {
            case "items" -> showNotRentedItems();
            case "rent" ->
            {
                System.out.print("Type id of a rental: ");
                rentItem(sc.nextLine());
            }
            case "return" ->
            {
                System.out.print("Type id of a rental: ");
                returnItem(sc.nextLine());
            }
            case "leave" -> this.setStatus(Status.TERMINATED);
            default -> System.out.println("Unrecognized command. Please type `help` to get list of commands.");
        }
    }

    private void parseCommandAsAdmin(String command)
    {
        Scanner sc = new Scanner(System.in);
        switch(command)
        {
            case "items" -> showItems();
            case "add" -> addItem();
            case "remove" ->
            {
                System.out.print("Type id of an item to remove: ");
                removeItem(sc.nextLine());
            }
            case "leave" -> this.setStatus(Status.TERMINATED);
            default -> System.out.println("Unrecognized command. Please type `help` to get list of commands.");
        }
    }

    private void showItems()
    {
        List<Vehicle> itemList = vehicleRepository.getAll();
        itemList.forEach(
                v ->
        {
            Optional<Rental> rental = rentalRepository.findByVehicleId(v.getId());
            System.out.println("id: " + v.getId());
            if(rental.isPresent()) System.out.println("rented: true");
            else System.out.println("rented: false");
            System.out.println("category: " + v.getCategory());
            System.out.println("brand: " + v.getBrand());
            System.out.println("model: " + v.getModel());
            System.out.println("year: " + v.getYear());
            System.out.println("price: " + v.getPrice());
            v.getAttributes().forEach((key, value) -> System.out.println(key + ": " + value.toString()));
            System.out.print("\n\n");
        });
    }

    private void showNotRentedItems()
    {
        List<Vehicle> itemList = vehicleRepository.getAll();
        itemList.forEach(v ->
        {
            Optional<Rental> rental = rentalRepository.findByVehicleId(v.getId());

            if(rental.isPresent() && rental.get().isActive())
            {
                System.out.println("id: " + v.getId());
                System.out.println("category: " + v.getCategory());
                System.out.println("brand: " + v.getBrand());
                System.out.println("model: " + v.getModel());
                System.out.println("year: " + v.getYear());
                System.out.println("price: " + v.getPrice());
                v.getAttributes().forEach((key, value) -> System.out.println(key + ": " + value.toString()));
                System.out.print("\n\n");
            }
        });
    }

    private void rentItem(String id)
    {
        Optional<Rental> foundRental = rentalRepository.findByVehicleId(id);
        if(foundRental.isPresent() && foundRental.get().isActive())
        {
            Rental rental = foundRental.get();
            rental.setUserId(user.getId());
            rental.setRentDateTime(LocalDate.now().toString());
            rental.setReturnDateTime(LocalDate.now().plusMonths(2).toString());
            rentalRepository.removeById(rental.getId());
            rentalRepository.add(rental);
            rentalRepository.save();
        }
    }

    private void returnItem(String id)
    {
        Optional<Rental> foundRental = rentalRepository.findByVehicleId(id);
        if(foundRental.isPresent() && !foundRental.get().isActive() && foundRental.get().getUserId().compareTo(user.getId()) == 0)
        {
            Rental rental = foundRental.get();
            rental.setUserId("");
            rental.setRentDateTime("");
            rental.setReturnDateTime("");
            rentalRepository.removeById(rental.getId());
            rentalRepository.add(rental);
            rentalRepository.save();
        }
    }

    private void addItem()
    {
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
        expectedAttributes.forEach(
                (key, value) ->
                {
                    System.out.print("Enter value for attribute `" + key + "`: ");
                    switch(value)
                    {
                        case "string" -> attributes.put(key, sc.nextLine());
                        case "number" -> attributes.put(key, sc.nextDouble());
                        case "boolean" -> attributes.put(key, sc.nextBoolean());
                        case "integer" -> attributes.put(key, sc.nextInt());
                    }
                }
        );
        Vehicle vehicle = new Vehicle(vehicleId, category, brand, model, Integer.parseInt(year), Double.parseDouble(price), attributes);
        Rental rental = new Rental(rentalId, vehicleId, "", "", "");
        try
        {
            vehicleRepository.add(vehicle);
            rentalRepository.add(rental);
            vehicleRepository.save();
            rentalRepository.save();
        }
        catch(IllegalArgumentException | IllegalStateException e)
        {
            e.printStackTrace();
        }
    }

    private void removeItem(String id)
    {
        List<Rental> rentals = rentalRepository.getAll();
        rentals.forEach(
                r ->
            {
                if(r.getVehicleId().equals(id))
                {
                    vehicleRepository.removeById(r.getVehicleId());
                    rentalRepository.removeById(r.getId());
                    vehicleRepository.save();
                    rentalRepository.save();
               }
            }
        );
    }
}
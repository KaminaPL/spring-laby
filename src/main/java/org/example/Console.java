package org.example;

import java.util.List;

public class Console
{
    public enum Status
    {
        ONGOING, TERMINATED
    }

    private Status status;
    private User user;
    private VehicleRepositoryImpl vehicleRepository;
    private UserRepositoryImpl userRepository;
    private Authentication auth;


    public Console()
    {
        status = Status.ONGOING;
        auth = new Authentication();
    }


    public void setCurrentStatus(Status status)
    {
        this.status = status;
    }

    public Status getStatus()
    {
        return status;
    }

    public void readCommand(String[] args)
    {
        System.out.print('\n');

        if(user == null || user.getLogin() == null)
        {
            if(args[0].compareTo("login") == 0)
            {
                if(args.length < 3)
                {
                    System.out.println("Too few arguments");
                }
                else if(args.length > 3)
                {
                    System.out.println("Too many arguments");
                }
                else
                {
                    user = auth.authenticate(args[1], args[2]);
                }

                if(user != null && user.getLogin() != null)
                {
                    System.out.println("Hello " + user.getLogin() + "! Welcome back!");
                    System.out.println("To open repository type `open`. " +
                            "When your work is done remember to type `leave` to save changes and leave program.");
                }
            }
            else if(args[0].compareTo("register") == 0)
            {
                if(args.length < 4)
                {
                    System.out.println("Too few arguments");
                }
                else if(args.length > 4)
                {
                    System.out.println("Too many arguments");
                }
                else
                {
                   auth.register(args[1], args[2], args[3]);
                }
            }
            else
            {
                System.out.println("Invalid command, try `login (login) (password)` or `register (login) (password) (confirm password)`.");
            }

            System.out.print('\n');

            return;
        }
        else if(vehicleRepository == null)
        {
            if(args[0].compareTo("open") == 0)
            {
                userRepository = new UserRepositoryImpl("/home/bartosz/users.csv");
                vehicleRepository = new VehicleRepositoryImpl("/home/bartosz/vehicles.csv");
                System.out.println("Successfully connected to repository.");
            }
            else
            {
                System.out.println("Connect by typing `open`.");
            }

            System.out.print('\n');

            return;
        }

        if(user.getRole().compareTo("USER") == 0)
        {
            if(args[0].compareTo("leave") == 0)
            {
                vehicleRepository.save();
                userRepository.save();
                setCurrentStatus(Status.TERMINATED);
            }
            else if(args[0].compareTo("list") == 0)
            {
                showItems();
            }
            else if(args[0].compareTo("rent") == 0)
            {
                if(args.length < 2)
                {
                    System.out.println("Too few arguments.");
                }
                else if(args.length > 2)
                {
                    System.out.println("Too many arguments.");
                }
                else
                {
                    rentItem(args[1]);
                }
            }
            else if(args[0].compareTo("return") == 0)
            {
                if(args.length > 1)
                {
                    System.out.println("Too many arguments.");
                }
                else
                {
                    returnItem(user.getRentedVehicleId().toString());
                }
            }
            else if(args[0].compareTo("help") == 0)
            {
                System.out.println("Commands:");
                System.out.println("leave - close program\nopen - connect to repository\nwhoami - display your data");
                System.out.println("rent (id) - rent vehicle with given id\nreturn (id) - return vehicle with given id\n");
            }
            else if(args[0].compareTo("whoami") == 0)
            {
                getUserData(user);
            }
            else
            {
                System.out.println("Command `" + args[0] + "` not found. Type `help` to get list of commands.");
            }
        }
        else
        {
            if(args[0].compareTo("leave") == 0)
            {
                vehicleRepository.save();
                userRepository.save();
                setCurrentStatus(Status.TERMINATED);
            }
            else if(args[0].compareTo("list") == 0)
            {
                showItems();
            }
            else if(args[0].compareTo("users") == 0)
            {
                showUsers();
            }
            else if(args[0].compareTo("whoami") == 0)
            {
                getUserData(user);
            }
            else if(args[0].compareTo("vadd") == 0)
            {
                if(args.length < 6)
                {
                    System.out.println("Too few arguments.");
                }
                else if(args[1].compareTo("MOTORCYCLE") == 0 && args.length < 7)
                {
                    System.out.println("Too few arguments.");
                }
                else if(args.length > 7)
                {
                    System.out.println("Too many arguments.");
                }
                else
                {
                    addItem(args);
                }
            }
            else if(args[0].compareTo("vremove") == 0)
            {
                if(args.length < 2)
                {
                    System.out.println("Too few arguments.");
                }
                else if(args.length > 2)
                {
                    System.out.println("Too many arguments.");
                }
                else
                {
                   removeItem(args[1]);
                }
            }
            else if(args[0].compareTo("uremove") == 0)
            {
                if(args.length < 2)
                {
                    System.out.println("Too few arguments.");
                }
                else if(args.length > 2)
                {
                    System.out.println("Too many arguments.");
                }
                else
                {
                    removeUser(args[1]);
                }
            }
            else if(args[0].compareTo("help") == 0)
            {
                System.out.println("Commands:");
                System.out.println("leave - close program\nopen - connect to repository\nwhoami - display your data");
                System.out.println("list - list all items from repository\nusers - list all users");
                System.out.println("vadd (type) (brand) (model) (year) (price) when vehicle is motorcycle: (license category) - adds vehicle with given data");
                System.out.println("example: add CAR Volkswagen T-Roc 2020 15000\n");
                System.out.println("vremove (id) - removes vehicle with given id\n");
                System.out.println("uremove (login) - removes user with given login\n");
            }
            else
            {
                System.out.println("Command `" + args[0] + "` not found. Type `help` to get list of commands.");
            }
        }

        System.out.print('\n');
    }

    private void addItem(String[] data)
    {
        Integer year, price;

        try
        {
            year = Integer.parseInt(data[4]);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid characters used in year.");
            return;
        }

        try
        {
            price = Integer.parseInt(data[5]);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid characters used in price.");
            return;
        }

        if(data[1].compareTo("CAR") == 0)
        {
            vehicleRepository.add(new Car(
                    -1,
                    data[2],
                    data[3],
                    year,
                    price,
                    false
            ));
        }
        else if(data[1].compareTo("MOTORCYCLE") == 0)
        {
            vehicleRepository.add(new Motorcycle(
                    -1,
                    data[2],
                    data[3],
                    year,
                    price,
                    false,
                    data[6]
            ));
        }
        else
        {
            System.out.println("Invalid vehicle type `" + data[1] +"`.");
            return;
        }

        System.out.println("Successfully added vehicle to repository. Results will be saved after the end of session.");
    }

    private void removeItem(String id)
    {
        Integer convertedId;

        try
        {
            convertedId = Integer.parseInt(id);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid characters used in id.");
            return;
        }

        Integer outcome = vehicleRepository.remove(convertedId);

        if(outcome == 0)
        {
            System.out.println("Successfully removed item from repository. Results will be saved after the end of session.");
        }
        else if(outcome == 1)
        {
            System.out.println("Item not found.");
        }
        else if(outcome == 2)
        {
            System.out.println("Cannot remove item. It has not been returned yet.");
        }
    }

    private void showItems()
    {
        List<Vehicle> vehicleList = vehicleRepository.getVehicles();

        vehicleList.forEach(

                it ->

                {
                    String[] data = it.toCSV().split(";");
                    StringBuilder sb = new StringBuilder();


                    for(int i=1;i<6;++i)
                    {
                        sb.append(data[i] + " ");
                    }

                    if(it.isRented())
                    {
                        sb.append("rented ");
                    }
                    else
                    {
                        sb.append("not rented ");
                    }

                    if(Integer.parseInt(data[0]) == 1)
                    {
                        sb.append(data[7]);
                    }

                    System.out.println(sb);
                }
        );
    }

    private void rentItem(String id)
    {
        Integer convertedId;

        try
        {
            convertedId = Integer.parseInt(id);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid characters used in id.");
            return;
        }

        Integer outcome = vehicleRepository.rentVehicle(convertedId);

        if(outcome == 0)
        {
            System.out.println("Item rented.");
            user.setRentedVehicleId(convertedId);
            userRepository.update(user);
        }
        else if(outcome == 1)
        {
            System.out.println("Item not found.");
        }
        else if(outcome == 2)
        {
            System.out.println("Item is already rented.");
        }
    }

    private void returnItem(String id)
    {
        Integer convertedId;

        try
        {
            convertedId = Integer.parseInt(id);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid characters used in id.");
            return;
        }

        Integer outcome = vehicleRepository.returnVehicle(convertedId);

        if(outcome == 0)
        {
            System.out.println("Item returned.");
            user.setRentedVehicleId(-1);
            userRepository.update(user);
        }
        else if(outcome == 1)
        {
            System.out.println("You haven't rented any vehicle.");
        }
    }

    private void removeUser(String login)
    {
        Integer outcome = userRepository.remove(login);

        if(outcome == 0)
        {
            System.out.println("User removed.");
        }
        else if(outcome == 1)
        {
            System.out.println("User not found.");
        }
        else if(outcome == 2)
        {
            System.out.println("Can't remove user. He/She hasn't returned a vehicle yet.");
        }
    }

    private void getUserData(User user)
    {
        System.out.println("Role: " + user.getRole());
        System.out.println("Name: " + user.getLogin());

        if(user.getRole().compareTo("USER") == 0 && user.getRentedVehicleId() > -1)
        {
            Vehicle vehicle = vehicleRepository.getVehicle(user.getRentedVehicleId());
            String[] data = vehicle.toCSV().split(";");
            StringBuilder sb = new StringBuilder();
            sb.append("Rented vehicle: \n")
                    .append("   Brand: " + data[2])
                    .append("   Model: " + data[3])
                    .append("   Year: " + data[4])
                    .append("   Price: " + data[5]);

            if(Integer.parseInt(data[0]) == 1)
            {
                sb.append("   License Category: " + data[7]);
            }

            System.out.println(sb);
        }

        System.out.print('\n');
    }

    private void showUsers()
    {
        List<User> userList = userRepository.getUsers();

        userList.forEach(user -> getUserData(user));
    }
}

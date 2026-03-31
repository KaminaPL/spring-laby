package org.example;

public class User
{
    private String login, password, role;
    private Integer rentedVehicleId;

    public User(String login, String password, String role, Integer rentedVehicleId)
    {
        this.login = login;
        this.password = password;
        this.role = role;
        this.rentedVehicleId = rentedVehicleId;
    }

    public User(User user)
    {
        this.login = user.login;
        this.password = user.password;
        this.role = user.role;
        this.rentedVehicleId = user.rentedVehicleId;
    }


    public String getLogin()
    {
        return login;
    }

    public String getPassword()
    {
        return password;
    }

    public String getRole()
    {
        return role;
    }

    public Integer getRentedVehicleId()
    {
        return rentedVehicleId;
    }

    public void setRentedVehicleId(Integer id)
    {
        rentedVehicleId = id;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String toCSV()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(login + ';')
                .append(password + ';')
                .append(role + ';')
                .append(rentedVehicleId.toString() + ";\n");

        return sb.toString();
    }

    public String toString()
    {
        return "User";
    }

    public User deepCopy()
    {
        return new User(this);
    }
}

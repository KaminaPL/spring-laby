package org.example;

public abstract class Vehicle
{
    private String brand, model;
    private Integer year, price, id;
    private Boolean isRented;

    public Vehicle(Integer id, String brand, String model, Integer year, Integer price, Boolean isRented)
    {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.isRented = isRented;
    }

    public Vehicle(Vehicle vehicle)
    {
        this.id = vehicle.id;
        this.brand = vehicle.brand;
        this.model = vehicle.model;
        this.year = vehicle.year;
        this.price = vehicle.price;
        this.isRented = vehicle.isRented;
    }


    public abstract String toString();

    public abstract Vehicle deepCopy();

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getBrand()
    {
        return brand;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public Integer getYear()
    {
        return year;
    }

    public void setYear(Integer year)
    {
        this.year = year;
    }

    public Integer getPrice()
    {
        return price;
    }

    public void setPrice(Integer price)
    {
        this.price = price;
    }

    public Boolean isRented()
    {
        return isRented;
    }

    public Boolean isNotRented() { return !isRented; }

    public void setRented(Boolean value)
    {
        isRented = value;
    }

    public String toCSV()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(id.toString() + ';')
                .append(brand + ';')
                .append(model + ';')
                .append(year.toString() + ';')
                .append(price.toString() + ';')
                .append(isRented.toString() + ';');

        return sb.toString();
    }
}

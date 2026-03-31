package org.example;

public class Car extends Vehicle
{

    public Car(Integer id, String brand, String model, Integer year, Integer price, Boolean isRented)
    {
        super(id, brand, model, year, price,isRented);
    }

    public Car(Car car)
    {
        super(car);
    }

    @Override
    public String toCSV()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("0;")
                .append(super.toCSV() + "\n");

        return sb.toString();
    }

    @Override
    public String toString()
    {
        return "Car";
    }

    @Override
    public Vehicle deepCopy()
    {
        return new Car(this);
    }
}

package org.example;

public class Motorcycle extends Vehicle
{
    private String licenseCategory;

    public Motorcycle(Integer id, String brand, String model, Integer year, Integer price, Boolean isRented, String licenseCategory)
    {
        super(id, brand, model, year, price, isRented);
        this.licenseCategory = licenseCategory;
    }

   public Motorcycle(Motorcycle motorcycle)
    {
        super(motorcycle);
        this.licenseCategory = motorcycle.licenseCategory;
    }

    public String getLicenseCategory()
    {
        return licenseCategory;
    }

    public void setLicenseCategory(String licenseCategory)
    {
        this.licenseCategory = licenseCategory;
    }

    @Override
    public String toCSV()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("1;")
            .append(super.toCSV())
            .append(licenseCategory + ";\n");

        return sb.toString();
    }

    @Override
    public String toString()
    {
        return "Motorcycle";
    }

    @Override
    public Vehicle deepCopy()
    {
        return new Motorcycle(this);
    }
}

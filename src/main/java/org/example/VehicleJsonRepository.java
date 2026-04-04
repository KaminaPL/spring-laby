package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepository implements IVehicleRepository
{
    private List<Vehicle> vehicleList;
    private String csvFilePath;
    private Integer latestVehicleId;


    public VehicleRepository(String csvFilePath)
    {
        this.csvFilePath = csvFilePath;
        vehicleList = new ArrayList<>();
        latestVehicleId = 0;
        load();
    }

    @Override
    public Integer rentVehicle(Integer id)
    {
        for(int i=0;i<vehicleList.size();++i)
        {
            if(vehicleList.get(i).getId().equals(id))
            {
                if(vehicleList.get(i).isRented())
                {
                    return 2;
                }
                else
                {
                    vehicleList.get(i).setIsRented(true);
                    return 0;
                }
            }
        }

        return 1;
    }

    @Override
    public Integer returnVehicle(Integer id)
    {
        for(int i=0;i<vehicleList.size();++i)
        {
            if(vehicleList.get(i).getId().equals(id))
            {
                if(vehicleList.get(i).isNotRented())
                {
                    return 2;
                }
                else
                {
                    vehicleList.get(i).setIsRented(false);
                    return 0;
                }
            }
        }

        return 1;
    }

    @Override
    public Vehicle getVehicle(Integer id)
    {
        for(int i=0;i<vehicleList.size();++i)
        {
            if(vehicleList.get(i).getId().equals(id))
            {
                return vehicleList.get(i).copy();
            }
        }

        return new Vehicle(-1, "", "", 0, 0, false);
    }

    @Override
    public List<Vehicle> getVehicles()
    {
        List<Vehicle> copiedVehicleList = new ArrayList<>(vehicleList.size());

        for(Vehicle vehicle : vehicleList)
        {
            copiedVehicleList.add(vehicle.copy());
        }

        return copiedVehicleList;
    }

    @Override
    public void add(Vehicle vehicle)
    {
        latestVehicleId += 1;
        vehicle.setId(latestVehicleId);
        vehicleList.add(vehicle.copy());
    }

    @Override
    public Integer remove(Integer id)
    {
        for(int i=0;i<vehicleList.size();++i)
        {
            if(vehicleList.get(i).getId().equals(id))
            {
                if(vehicleList.get(i).isNotRented())
                {
                    vehicleList.remove(i);
                    return 0;
                }
                else
                {
                    return 2;
                }
            }
        }

        return 1;
    }

    public void load()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            String line;

            while((line = br.readLine()) != null)
            {
                String[] data = line.split(";");

                vehicleList.add(new Vehicle(
                                Integer.parseInt(data[0]),
                                data[1],
                                data[2],
                                Integer.parseInt(data[3]),
                                Integer.parseInt(data[4]),
                                Boolean.parseBoolean(data[5])
                        )
                );
            }

            br.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        for(Vehicle vehicle : vehicleList)
        {
            if(vehicle.getId() > latestVehicleId)
            {
                latestVehicleId = vehicle.getId();
            }
        }
    }

    public void save()
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath));
            StringBuilder sb = new StringBuilder();

            vehicleList.forEach(it -> sb.append(it.toCSV()));

            bw.write(sb.toString());
            bw.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}

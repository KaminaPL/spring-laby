package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository
{
    private List<User> userList;
    private String csvFilePath;


    public UserRepository(String csvFilePath)
    {
        this.csvFilePath = csvFilePath;
        userList = new ArrayList<>();
        load();
    }


    @Override
    public User getUser(String login)
    {
        for(User user : userList)
        {
            if(user.getLogin().compareTo(login) == 0)
            {
                return user.copy();
            }
        }

        return new User(null, null, null, null);
    }

    @Override
    public List<User> getUsers()
    {
        List<User> copiedUserList = new ArrayList<>();

        for(User user : userList)
        {
            copiedUserList.add(user.copy());
        }

        return copiedUserList;
    }

    @Override
    public Integer add(User user)
    {
        for(User u : userList)
        {
            if(u.getLogin().compareTo(user.getLogin()) == 0)
            {
                return 1;
            }
        }

        userList.add(user.copy());
        return 0;
    }

    @Override
    public Integer remove(String login)
    {
        for(User u : userList)
        {
            if(u.getLogin().compareTo(login) == 0)
            {
                if(u.getRentedVehicleId() > -1)
                {
                    return 2;
                }
                else
                {
                    userList.remove(u);
                    return 0;
                }
            }
        }

        return 1;
    }

    public void save()
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath));
            StringBuilder sb = new StringBuilder();

            for(User user : userList)
            {
                sb.append(user.toCSV());
            }

            bw.write(sb.toString());
            bw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
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

                userList.add(new User(data[0], data[1], data[2], Integer.parseInt(data[3])));
            }

            br.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user)
    {
        for(User u : userList)
        {
            if(u.getLogin().compareTo(user.getLogin()) == 0)
            {
                userList.remove(u);
                break;
            }
        }

        userList.add(user.copy());
    }
}

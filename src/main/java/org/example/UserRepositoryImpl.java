package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements IUserRepository
{
    private List<User> userList;
    private String csvFilePath;


    public UserRepositoryImpl(String csvFilePath)
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
                return user.deepCopy();
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
            copiedUserList.add(user.deepCopy());
        }

        return copiedUserList;
    }

    @Override
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

    @Override
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

        userList.add(user.deepCopy());
    }
}

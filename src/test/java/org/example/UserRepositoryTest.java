package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class UserRepositoryTest
{
    @Test
    void getUsersShouldReturnDeepCopy()
    {
        UserRepository repo = new UserJsonRepository("/home/bartosz/users.csv");
        List<User> users1 = repo.getUsers();
        List<User> users2  = repo.getUsers();
        assertNotSame(users1, users2);
        assertNotSame(users1.get(0), users2.get(0));
    }

    @Test
    void addingToReturnedListShouldNotChangeRepository()
    {
        UserRepository repo = new UserJsonRepository("/home/bartosz/users.csv");
        List<User> users = repo.getUsers();
        int repoSizeBefore = repo.getUsers().size();
        users.add(new User("User", "user123", "USER", -1));
        int repoSizeAfter = repo.getUsers().size();
        assertEquals(repoSizeBefore, repoSizeAfter);
    }

    @Test
    void changingReturnedUserShouldNotChangeRepository()
    {
        UserRepository repo = new UserJsonRepository("/home/bartosz/users.csv");
        List<User> users = repo.getUsers();
        User copy = users.get(0);
        copy.setRentedVehicleId(1);
        Integer rentedVehicleId = copy.getRentedVehicleId();
        Integer repoRentedVehicleIdAfterChange = repo.getUsers().get(0).getRentedVehicleId();
        assertNotSame(rentedVehicleId, repoRentedVehicleIdAfterChange);
    }
}

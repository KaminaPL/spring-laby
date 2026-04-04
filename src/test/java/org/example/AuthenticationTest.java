package org.example;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest
{
    @Test
    void loginReturnsCorrectUserData()
    {
        UserRepository repository = new UserJsonRepository("/home/bartosz/users.csv");
        AuthService auth = new AuthService();
        User user = auth.authenticate("User", "user123");
        String hashedPassword = DigestUtils.sha256Hex("user123");
        assertNotNull(user);
        assertEquals(user.getLogin(), "User");
        assertEquals(user.getHashedPassword(), hashedPassword);
        assertEquals(user.getRentedVehicleId(), -1);
    }
}

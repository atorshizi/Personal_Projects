package test.MS2Tests;

import org.junit.jupiter.api.Test;

import main.java.client.LoginScreen;
import test.MockDatabase;

public class ScenarioTest1 {
    
    @Test
    public void MS2ScenarioTest1() {
        //Create a new user in mock db
        MockDatabase db = new MockDatabase();
        db.addUser("test", "test");

        //Login with the new user
        boolean valid = db.validUser("test", "test");
        assert(valid);

        //Test login with invalid credentials
        boolean invalid = db.validUser("null", "null");
        assert(!invalid);
    }
}

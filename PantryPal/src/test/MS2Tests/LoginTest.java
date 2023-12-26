package test.MS2Tests;

import org.junit.jupiter.api.Test;

import test.MockDatabase;

/**
 * Tests for MS2 User Story 1
 * User creation and existing user validation
 */
public class LoginTest {
    MockDatabase db = new MockDatabase();
    @Test
    public void testCreateUser() {
        boolean valid = db.addUser("test", "test");
        boolean invalid = db.addUser(null, null);
        assert(valid);
        assert(!invalid);
    }

    @Test
    public void testValidUser() {
        db.addUser("test", "test");
        boolean valid = db.validUser("test", "test");
        boolean invalid = db.validUser(null, null);
        assert(valid);
        assert(!invalid);
    }
}

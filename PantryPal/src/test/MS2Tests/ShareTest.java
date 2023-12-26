package test.MS2Tests;

import org.junit.jupiter.api.Test;

import test.MockDatabase;
import test.MockShare;

public class ShareTest {
    @Test
    public void testValidShare() {
        MockDatabase db = new MockDatabase();
        db.addUser("test", "test");


        MockShare share = new MockShare(db);
        String res = share.handleGet("test");
        assert(res.equals("localhost:8080/recipe?name=test"));
    }

    @Test
    public void testInvalidShare() {
        MockDatabase db = new MockDatabase();
        db.addUser("test", "test");
        MockShare share = new MockShare(db);

        String res = share.handleGet(null);
        assert(res.equals("error"));
    }
}

package test;

import java.util.HashMap;
import main.java.server.database;

public class MockDatabase extends database{
    HashMap<String, String> users = new HashMap<String, String>();
    @Override
    public boolean addUser(String username, String password) {
        //Check if username and password are null
        if (username == null || password == null) {
            return false;
        }else{
            users.put(username, password);
            return true;
        }
    }

    @Override
    public boolean validUser(String username, String password) {
        //Check if username and password are null
        if (username == null || password == null) {
            return false;
        }else{
            return users.containsKey(username) && users.get(username).equals(password);
        }
    }
}

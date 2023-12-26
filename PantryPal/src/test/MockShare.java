package test;

import main.java.server.ShareHandler;

public class MockShare extends ShareHandler{
    MockDatabase db = new MockDatabase();
    public MockShare(MockDatabase db) {
        super(db);
        this.db = db;
    }

    public String handleGet(String name){
        if (name != null) {
            return "localhost:8080/recipe?name=" + name;
        }else{
            return "error";
        }
    }
}

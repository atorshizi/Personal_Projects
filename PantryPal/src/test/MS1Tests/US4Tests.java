package test.MS1Tests; 
import main.java.server.*; 
import main.java.client.*; 
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import javafx.stage.Stage; 
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class US4Tests {

    private recipeHandler rh;
    private database db;
    recipe rec;

    @BeforeEach 
    void setUp() {
        rh = new recipeHandler("test");
        db = new database();
        rec = new recipe("Dinner", "Recipe1", "Description 1", "imageURL");
    }

    /* 
     * Test for working functionality when database contains prefilled recipes
     */
    @Test
    void testEdit() {
        int beforeCount = rh.getNumRecipes("test");

        String recName = rec.getName();
        String updated = "Updated Description for Recipe1";

        db.editRecipe("test", recName, updated);

        // Assert that no new recipes were created/added to the database, only modified. 
        assertEquals(beforeCount, rh.getNumRecipes("test"));
    }

} 

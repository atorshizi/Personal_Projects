package test.MS1Tests; 
import main.java.server.*; 
import main.java.client.*; 
import org.junit.jupiter.api.Test;

import javafx.stage.Stage;


import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/*
 * Tests displaying all recipes functionality
 */
public class US3Tests {
    private recipeHandler rh;
    private Stage primaryStage; 
    
    @BeforeEach
    void setUp() {
    }
    
    @Test
    void testRecipesCount() {
        rh = new recipeHandler("test");
        assert(rh.getNumRecipes("test") >= 0);
    }

    @Test
    void testAddRecipes() { 
        rh = new recipeHandler("test");
        // rh.getRecipeElements(primaryStage); 
        int currentCount = rh.getNumRecipes("test");
        // load in the same recipe 3 times into the database. 
        for(int i = 0; i < 3; i++) {
            rh.addToDB("test");
        }
        ArrayList<recipe> recipes = rh.getRecipes("test");
        // Assert database accounted for 3 new recipes and total count matches with database count.
        assertEquals(currentCount + 3, rh.getNumRecipes("test"));
        assertEquals(currentCount + 3, recipes.size());
    }
}

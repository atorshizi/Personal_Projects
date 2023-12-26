package test.MS2Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.server.recipe;

import java.util.ArrayList;

/*
 * UNIT TESTS
 * A class to test tasks related to US3 in PantryPal2.
 * Specifically aimed at testing recipe regeneration
 */
public class MS2US3Test {
    ArrayList<recipe> recipes;
    recipe rec1;
    recipe rec2;
    recipe rec3;

    @BeforeEach 
    void setUp() {
        recipes = new ArrayList<>();
        rec1 = new recipe("Recipe1", "Breakfast", "Description 1", "imageURl 1");
        rec2 = new recipe("Recipe2", "Breakfast", "Description 2","imageURl 2");
        rec3 = new recipe("Recipe3", "Breakfast", "Description 3","imageURl 3");
    }

    /* 
     * Simulates the user recording and receiving their first suggested recipe from ChatGPT and automatically adds it to the database.
     */
    @Test
    void testRefresh() {
        recipes.add(rec1);
        // - notifies model to delete initial recipe
        recipes.remove(rec1);
        
        assertEquals(0, recipes.size()); // At this point, recipes have only been suggested and not permanetly added by user. 

        // - notifies model to add a second suggested recipe
        recipes.add(rec2);
        // - user doesn't like this either and clicks "Refresh Recipe" UI button.
        recipes.remove(rec2);
        recipes.add(rec3);
        // ^ - user likes this third recipes and saves it to the database

        assertEquals(1, recipes.size()); // Despite multiple refreshes, user only saves one to the database.
        assertEquals("Recipe3", recipes.get(0).getName()); // Refresh method respects user's mealType desire.
        assertFalse(recipes.contains(rec1)); 
        assertFalse(recipes.contains(rec2)); 
        assertTrue(recipes.contains(rec3)); 
    }  

}

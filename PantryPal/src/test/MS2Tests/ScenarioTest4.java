package test.MS2Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import main.java.server.recipe;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;


/* 
* Behavior Driven Development Test 
* While on the recipe list page, you will notice that beside each recipe 
* is a little tag designating the recipe as either “Breakfast”, “Lunch”, or “Dinner.” 
* At the top of the page you will notice two buttons “Filter” and “Sort” which each 
* show dropdowns allowing you to filter by meal-type or sorting chronologically and 
* alphabetically. Upon either modifier being chosen, the list will update accordingly 
* to show only relevant recipes in the desired order.
*/
public class ScenarioTest4 {
    ArrayList<recipe> recipes;
    recipe rec1;
    recipe rec2;
    recipe rec3;
    recipe rec4;
    recipe rec5;
    recipe rec6;
  

    @BeforeEach 
    void setUp() {
        recipes = new ArrayList<>();
        rec1 = new recipe("Pancakes", "Breakfast", "Description 1", "imageURl 1");
        rec2 = new recipe("Omelette", "Breakfast", "Description 2","imageURl 2");
        rec3 = new recipe("Salad", "Lunch", "Description 1", "imageURl 3");
        rec4 = new recipe("Chicken Sandwich", "Lunch", "Description 2","imageURl 4");
        rec5 = new recipe("Steak", "Dinner", "Description 1", "imageURl 5");
        rec6 = new recipe("Burrito", "Dinner", "Description 2","imageURl 6");
        recipes.add(rec1);
        recipes.add(rec2);
        recipes.add(rec3);
        recipes.add(rec4);
        recipes.add(rec5);
        recipes.add(rec6);
    }

    /* 
     * Simulates the user filtering first and then sorting second.
     */
    @Test
    void MS2ScenarioTest4() {
        // 1st: Filters by Breakfast meal type.
        String mealFilter = "Dinner";
        Iterator<recipe> iterator = recipes.iterator();
        while (iterator.hasNext()) {
            recipe recipe = iterator.next();
            if (!recipe.getMealType().equals(mealFilter)) {
                iterator.remove();
            }
        }
        assertEquals(2, recipes.size());
        assertEquals(mealFilter, recipes.get(0).getMealType());
        assertEquals(mealFilter, recipes.get(1).getMealType());

        // 1st: Sorts by recipe name.
        recipes.sort(Comparator.comparing(recipe::getName));

        assertEquals("Burrito", recipes.get(0).getName());
        assertEquals("Steak", recipes.get(1).getName());

    }  
}

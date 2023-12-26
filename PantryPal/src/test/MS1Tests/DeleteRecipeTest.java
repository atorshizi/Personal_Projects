package test.MS1Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import main.java.server.recipe;

public class DeleteRecipeTest {
     
    @Test
    void testDeleteRecipe() {
        //Represents our db
        //Since db connects to MongoDB, we can't test it directly
        //So we create a fake db to test our editRecipe() method
        //List reflects a collection in MongoDB
        List<recipe> dbFake = new ArrayList<>();

        //Represents the recipe to be deleted
        recipe rec = new recipe("Lunch", "Recipe1", "Description 1", "imageURL");

        //Add recipe to db
        dbFake.add(rec);
        assertEquals(dbFake.get(0).getName(), rec.getName());

        //Delete recipe from db
        dbFake.remove(rec);
        assertEquals(0, dbFake.size());
    }
}

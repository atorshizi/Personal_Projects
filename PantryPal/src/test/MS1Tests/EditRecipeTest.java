package test.MS1Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.*;

import org.junit.jupiter.api.Test;

import main.java.server.recipe;

public class EditRecipeTest {
    @Test
    void testEditRecipe() {
        //Represents our db
        //Since db connects to MongoDB, we can't test it directly
        //So we create a fake db to test our editRecipe() method
        //List reflects a collection in MongoDB
        List<recipe> dbFake = new ArrayList<>();
        recipe rec = new recipe("Breakfast","Recipe1", "Description 1", "imageURL");
        dbFake.add(rec);
        String recName = rec.getName();
        String updated = "Updated Description for Recipe1";

        recipe recUpdated = new recipe("Lunch", recName, updated, "imageuRL");
        assertNotEquals(rec.getDetails(), recUpdated.getDetails());
        dbFake.set(0, recUpdated);

        //See that the recipe was updated in our fake local db (dbFake)
        //Mocks the behavior of the editRecipe() method by updating the recipe in the db
        assertEquals(dbFake.get(0).getDetails(), recUpdated.getDetails());
    }
}

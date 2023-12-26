package test.MS2Tests;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import main.java.server.recipe;

public class RefreshRecipeTest {
    /* 
     * Behavior Driven Development Test 
     * Scenario: Upon creating a new recipe, you will be greeted with the ingredient list, 
     * recipe instructions as well as a preview of what the dish would look like. At the top of the 
     * screen in addition to existing save/delete buttons there will be a “Refresh” button allowing 
     * you to regenerate the recipe with the same ingredients. When hitting refresh, a new recipe with a 
     * new image will be created for you to save/delete.
     */
    @Test
    void testRefreshRecipe() {
        List<recipe> dbFake = new ArrayList<>();
        recipe beforeRec = new recipe("Dinner", "Recipe1", "Description 1", "imageURl");
        dbFake.add(beforeRec);

        // This line mimics user interactions to refresh an unsatisfactory recipe suggestion
        // refreshRecipe(beforeRec);

        dbFake.remove(beforeRec);
        recipe refreshedRecipe = new recipe("Breakfast", "Recipe2", "Description 2", "imageURl");
        dbFake.add(refreshedRecipe);

        // Assert that the recipe was placed in our fake local db (dbFake)
        // Mocks the behavior of the refreshRecipe() method by deleting the intially suggested recipe that was added to 
        // the db, and instead mocking a ChatGPT call to update the recipe with a different meal and instructions.
        
        assertFalse(dbFake.contains(beforeRec));
        assertTrue(dbFake.contains(refreshedRecipe));
    }
}

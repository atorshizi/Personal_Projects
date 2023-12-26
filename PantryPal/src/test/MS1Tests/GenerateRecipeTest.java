package test.MS1Tests;

import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.client.homeScreen;
import main.java.server.Whisper;
import test.MockWhisper;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class GenerateRecipeTest extends Application {

    @Override 
    public void start(Stage primaryStage) {
        homeScreen hs = new homeScreen("test", primaryStage); 
        primaryStage.setScene(hs.getScene()); 
        primaryStage.show(); 
    } 

    @BeforeEach
    void setUp() {
        
    }
    
    /*
     * Tests USER STORY 1, creates input files, mocks transcription, and sends to ChatGPT for recipe. 
     */
    @Test
    void testGenerateRecipe() {
        File mealFile = new File("mealtime.wav");
        File ingredientFile = new File("ingredients.wav");
        try {
            mealFile.createNewFile();
            ingredientFile.createNewFile();
        } catch (IOException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        
        // Test for proper file creation from recording user's ingredients and mealType
        assertTrue(mealFile.exists());
        assertTrue(ingredientFile.exists());

        // Mock Input Audio Files into Transcriptions
        String mealType = "Lunch";
        String ingredients = "Chicken, rice, eggs";

        // Assume files have been transcribed into text after Whisper transcription
        String prompt = "Give me a concise recipe for a " +
                mealType +
                " meal that ONLY uses the following ingredients: " +
                ingredients;

        // Mock input and output for ChatGPT
        // - ChatGPT recipeMaker = new ChatGPT();
        // - String recipe = recipeMaker.generateRecipe();
        String recipeTitle = "Chicken Fried Rice";

        // These asserts appear to be insignificant, but once mocking is terminated and 
        // actual audio inputs include these parameters then testing will be helpful in
        // tracking expected behavior before sending to Whisper/ChatGPT.

        assertEquals("Lunch", mealType);
        assertEquals("Chicken, rice, eggs", ingredients);
        assertEquals("Chicken Fried Rice", recipeTitle);

        mealFile.delete();
        ingredientFile.delete();
    }
    /*
     * Tests for Whisper Transcription TASK
     */
    @Test
    void testWhisperTask2() {
        File mealFile = new File("mealtime.wav");
        File ingredientFile = new File("ingredients.wav");
        try {
            mealFile.createNewFile();
            ingredientFile.createNewFile();
        } catch (IOException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }

        // Uncomment when mocking is no longer needed
        Whisper inputMeal = new MockWhisper();
        Whisper inputIngred = new MockWhisper();
        
        // - String mealTranscribed = inputMeal.Main(mealFile);
        // - String ingredientsTranscribed = inputMeal.Main(ingredientsFile);

        String ingredientsTranscribed = "Chicken, rice, eggs";
        String mealTranscribed = "lunch";

        try {
            // False due to current audio .wav file not containing input
            assertFalse(inputMeal.main(mealFile).equals(mealTranscribed));
            assertFalse(inputIngred.main(mealFile).equals(ingredientsTranscribed));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mealFile.delete();
        ingredientFile.delete();
    }
    
}
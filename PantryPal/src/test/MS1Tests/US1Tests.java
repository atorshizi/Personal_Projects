package test.MS1Tests; 
import main.java.server.*;
import test.MockWhisper;
import main.java.client.*; 
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.stage.Stage;


import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class US1Tests extends Application {

    @Override 
    public void start(Stage primaryStage) {
        homeScreen hs = new homeScreen("test",primaryStage); 
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

        // Assume files have been transcribed into text after Whisper transcription
        IWhisper mock = new MockWhisper();
        String prompt = mock.connect(ingredientFile);

        // Mock input and output for ChatGPT
        IChatGPT recipeMaker = new MockGPT();
        String recipe = recipeMaker.generateRecipe(prompt);
        String fp = "src/test/MS1Tests/US3Mocks/recipes.txt";
        recipeMaker.saveRecipe(fp, recipe);

        int count = 0;
        try {
            File myObj = new File(fp);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                count++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        // One new recipe in recipes.txt
        assertEquals(1, count);
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
        MockWhisper inputMeal = new MockWhisper();
        MockWhisper inputIngred = new MockWhisper();
        
        // - String mealTranscribed = inputMeal.Main(mealFile);
        // - String ingredientsTranscribed = inputMeal.Main(ingredientsFile);

        String ingredientsTranscribed = "Chicken, rice, eggs";
        String mealTranscribed = "lunch";

        try {
            // False due to current audio .wav file not containing input
            assertFalse(inputMeal.connect(mealFile).equals(mealTranscribed));
            assertFalse(inputIngred.connect(mealFile).equals(ingredientsTranscribed));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mealFile.delete();
        ingredientFile.delete();
    }
    
}
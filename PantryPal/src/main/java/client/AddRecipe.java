package main.java.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;

import javax.sound.sampled.*;

import com.sun.javafx.sg.prism.NGShape.Mode;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//Going to remove once mvc is setup
import main.java.server.ChatGPT;
import main.java.server.Whisper;
import main.java.server.imageGenerator;

/**
 * Creates a new recipe from user input and adds it to database.
 * Includes the UI for the user to input the recipe information using voice
 * recordings
 */
public class AddRecipe {
    private Scene currScene;
    private Scene prevScene;
    private Stage parent;
    private Button backButton;
    private Button submitButton;
    private Button recordMealButton;
    private Button recordIngredientButton;
    private Button refreshButton;
    private Button stopButton;
    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;
    private Label recordingLabel;
    private Label recNameMsg;
    private TextArea recLabel;
    private HBox recipeName;
    private Model model;
    private VBox elements;
    private String username;

    /*
     * Constructor for recipe scene that sets UI element info
     */
    public AddRecipe(String username, Scene prevScene, Stage parent) { 
        checkServer(); 
        // Initialize variables
        audioFormat = getAudioFormat();
        this.parent = parent;
        this.prevScene = prevScene;
        BorderPane root = new BorderPane();
        backButton = new Button("Back");
        submitButton = new Button("Submit");
        refreshButton = new Button("Refresh Recipe");
        recordIngredientButton = new Button("Record Ingredients");
        recordMealButton = new Button("Record Meal Type");
        recordingLabel = new Label();
        stopButton = new Button("Stop Recording");
        this.model = new Model();
        addListeners();
        checkFiles();
        this.username = username;

        // elements stacks all the hbox on top of each other
        elements = new VBox();
        HBox header = new HBox(backButton, submitButton);
        HBox recordButtons = new HBox(recordMealButton, recordIngredientButton);
        HBox recordingSign = new HBox(recordingLabel);
        HBox stop = new HBox(stopButton);

        stopButton.setDisable(true);
        // Set UI Positions
        header.setSpacing(250);
        header.setAlignment(Pos.CENTER);

        // Sets positions for record buttons and label
        recordButtons.setAlignment(Pos.CENTER);
        recordButtons.setSpacing(5);
        recordingSign.setAlignment(Pos.CENTER);

        // Center stop button
        stop.setAlignment(Pos.CENTER);

        elements.setAlignment(Pos.CENTER);
        elements.getChildren().addAll(recordButtons, recordingSign);

        // Build borderpane
        root.setTop(header);
        root.setCenter(elements);
        root.setBottom(stop);

        parent.setTitle("Add Recipe");
        this.currScene = new Scene(root, 400, 300);
    }

    /*
     * Return scene to be displayed
     * 
     * 
     * @return scene object to be shown in main stage
     */
    public Scene getScene() {
        return currScene;
    }

    /*
     * Sets the listerners for each of the buttons in the scene
     * When recording disables both record buttons to ensure that only one
     * function is running at a time
     */
    public void addListeners() {
        // Both record buttons enable stopping and make record label visible
        recordMealButton.setOnAction(e -> {
            recordingLabel.setText("Recording Meal Type...");
            recordMealButton.setDisable(true);
            recordIngredientButton.setDisable(true);
            stopButton.setDisable(false);
            captureAudio("mealtime");
        });
        recordIngredientButton.setOnAction(e -> {
            recordingLabel.setText("Recording Ingredients...");
            recordIngredientButton.setDisable(true);
            recordMealButton.setDisable(true);
            stopButton.setDisable(false);
            captureAudio("ingredients");
        });

        // Disables itself and stops recording
        // Disables itself and stops recording
        stopButton.setOnAction(e -> {
            recordingLabel.setText("Record");
            recordIngredientButton.setDisable(false);
            recordMealButton.setDisable(false);
            stopButton.setDisable(true);
            stopCapture();
        });

        // Checks if both mealtime and ingredients were set properly
        submitButton.setOnAction(e -> {
            File meal = new File("mealtime.wav");
            File ingredients = new File("ingredients.wav");
            // Check that both ingredients and meal type were recorded
            if (!meal.exists() && !ingredients.exists()) {
                fileError("meal type and ingredients");
            } else if (!meal.exists()) {
                fileError("meal type");
            } else if (!ingredients.exists()) {
                fileError("ingredients");
            } else {
                model.performRequest("POST", meal, null, "mealTime", null, null, username);
                String ingR = model.performRequest("POST", null, ingredients, "ingredients", null, null, username);
                String nll = model.performRequest("GET", null, null, null, ingR.trim(), null, username);
                String details = nll.substring(nll.indexOf("\n")+1); 
                String mealType = "mealType";
                String imageURL;
                //temp imageURl 
                //String imageURL = "https://oaidalleapiprodscus.blob.core.windows.net/private/org-Sd9bwBmEf5IDns4KIh3k3fXp/user-mlF2UiRIjgQpU5OH9QHus5gd/img-Mxf2GRV2JwmSkO7yzxs2xQz1.png?st=2023-12-05T10%3A32%3A23Z&se=2023-12-05T12%3A32%3A23Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-12-04T16%3A34%3A12Z&ske=2023-12-05T16%3A34%3A12Z&sks=b&skv=2021-08-06&sig=ZrHOLUPfVtALS316YtqbVBwngjn6zAtLgjuJDqGBLAg%3D";
                
                try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/meal.txt"))) {
                    String line; 
                    if ((line = br.readLine()) != null) {mealType = line;}
                } catch (Exception ex) {System.out.println(ex);}

                
                String localImage = ingR+"image.png";
                File imageFile = new File(localImage);
                if(!imageFile.exists()){ 
                    System.out.println("Not exist"); 
                    imageGenerator recipeImage = new imageGenerator(ingR);
                    try{
                        recipeImage.main();
                    } catch (Exception e1){
                        e1.printStackTrace();
                    }
                    imageURL = recipeImage.getImageURL();
                }else{ 
                    System.out.println("Does exist"); 
                    imageURL = "file://"+imageFile.getAbsolutePath(); 
                }

                ConfirmRecipeScreen crs = new ConfirmRecipeScreen(username, parent, ingR, mealType, details, meal, ingredients, imageURL);
                parent.setScene(crs.getScene());
            }
        });
        backButton.setOnAction(e -> {
            File meal = new File("mealtime.wav");
            File ingredients = new File("ingredients.wav");
            // Check if either ingredients and meal type were recorded and deletes them if
            // they do
            if (meal.exists()) {
                meal.delete();
            }
            if (ingredients.exists()) {
                ingredients.delete();
            }
            parent.setScene(prevScene);
        });
    }

    /**
     * Sets audio format into and returns the audio format information
     * 
     * @return the audio format used for the recording
     */
    private AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    /*
     * Method to capture audio pulled from Lab 5
     */
    private void captureAudio(String type) {
        Thread t = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // the format of the TargetDataLine
                            DataLine.Info dataLineInfo = new DataLine.Info(
                                    TargetDataLine.class,
                                    audioFormat);
                            // the TargetDataLine used to capture audio data from the microphone
                            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
                            targetDataLine.open(audioFormat);
                            targetDataLine.start();
                            recordingLabel.setVisible(true);

                            // the AudioInputStream that will be used to write the audio data to a file
                            AudioInputStream audioInputStream = new AudioInputStream(
                                    targetDataLine);

                            // the file that will contain the audio data
                            File audioFile = new File(type + ".wav");
                            AudioSystem.write(
                                    audioInputStream,
                                    AudioFileFormat.Type.WAVE,
                                    audioFile);
                            recordingLabel.setVisible(false);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        t.start();
    }

    /*
     * Method to stop capture from Lab 5
     */
    private void stopCapture() {
        targetDataLine.stop();
        targetDataLine.close();
    }

    /*
     * Method for showing error popup when missing
     * mealtime or ingredient
     */
    private void fileError(String missing) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Missing Component");
        alert.setHeaderText("Missing " + missing + " recording!");
        alert.setContentText("Please make sure all aspects are recorded before submitting.");

        alert.showAndWait();
    } 

    private void checkFiles(){
        File meal = new File("mealtime.wav");
        File ingredients = new File("ingredients.wav");
        if(meal.exists()){
            meal.delete();
        }
        if(ingredients.exists()){
            ingredients.delete();
        }
    }
    private void checkServer(){ 
        Model model = new Model(); 
        String response = model.performRequest("GET", null, null, null, null, null, username); 
        if(response.contains("java.net.ConnectException")){ 
            serverError(); 
            response = model.performRequest("GET", null, null, null, null, null, username); 
        } 
    } 
    private void serverError() { 
        //Stop program is server isn't running
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Missing Server"); 
        alert.setHeaderText("Server Not Active!"); 
        alert.setContentText("Please Load Up Server."); 
        alert.showAndWait(); 
        System.exit(0);
    } 
}

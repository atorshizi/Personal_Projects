package main.java.client;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.server.database;
import main.java.server.imageGenerator;
import main.java.server.recipe;

public class ConfirmRecipeScreen {
    private Scene scene;
    private Label title;
    private Label recNameMsg;
    private Label mealFilter;
    private TextArea recLabel;
    private Button saveButton;
    private Button deleteButton; 
    private Button refreshButton;
    private ScrollPane detailedRecipe;
    private Stage primaryStage;
    private Model model;
    private String mealType;
    private String name;
    private String details;
    private File meal;
    private File ingredients;
    private String username;
    private String imageURL;
    private ImageView imageView;

    public ConfirmRecipeScreen(String username, Stage primaryStage, String name, String mealType, String details,  File meal, File ingredients, String imageURL) {
        checkServer(); 
        StackPane root = new StackPane();
        title = new Label("PantryPal");
        mealFilter = new Label(mealType);
        recNameMsg = new Label(name);
        saveButton = new Button("Save"); 
        deleteButton = new Button("Delete"); 
        refreshButton = new Button("Refresh Recipe"); 
        this.mealType = mealType;
        this.primaryStage = primaryStage;
        this.model = new Model();
        this.name = name;
        this.details = details;
        this.meal = meal;
        this.ingredients = ingredients;
        this.username = username;
        this.imageURL = imageURL;

         
        if(imageURL != null && !imageURL.isEmpty()){
            this.imageView = new ImageView(new Image(imageURL));
            this.imageView.setFitWidth(250);
            this.imageView.setFitHeight(250);
        }
        

        HBox r_buttons = new HBox(saveButton, deleteButton, refreshButton); 
        HBox heading = new HBox(title, r_buttons); 
        heading.setAlignment(Pos.CENTER);
        heading.setSpacing(80);

        HBox subHeading = new HBox(recNameMsg);
        subHeading.setAlignment(Pos.TOP_LEFT);
        VBox text = new VBox(heading, subHeading);
        text.setAlignment(Pos.CENTER);
        this.recLabel = new TextArea(details);
        this.recLabel.setEditable(false);
        // recLabel.setWrapText(true);
        detailedRecipe = new ScrollPane(recLabel);
        detailedRecipe.setFitToWidth(true); 
        detailedRecipe.setFitToHeight(true); 
        

        BorderPane detailedScreen = new BorderPane(); 
        detailedScreen.setTop(text); 
        detailedScreen.setCenter(detailedRecipe); 
        
        if(this.imageView != null){
            VBox imageContainer = new VBox(imageView);
            imageContainer.setAlignment(Pos.CENTER);
            detailedScreen.setRight(imageContainer);
        }
        root.getChildren().addAll(detailedScreen); 
        this.scene = new Scene(root, 1000, 600); 
        addListeners();
    }

    public Scene getScene() {
        return this.scene;
    }

    private void addListeners() {
        deleteButton.setOnAction(e -> { 
            System.out.println("Deleting: " + name);
            String response = model.performRequest("DELETE", null, null, null, name.trim(), null, username);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Recipe deleted!");
            alert.showAndWait();
            System.out.println(response);
            //Go Back to recipesScreen
            recipesScreen rs = new recipesScreen(username, primaryStage);
            primaryStage.setScene(rs.getScene());
        });  

        saveButton.setOnAction(e -> {
            Alert saved = new Alert(Alert.AlertType.INFORMATION, "Recipe saved!");
            saved.showAndWait();
            recipesScreen rs = new recipesScreen(username, primaryStage);
            primaryStage.setScene(rs.getScene());
        });
        refreshButton.setOnAction(e -> {
            refreshRecipe(meal, ingredients);
        });

    }
    public void refreshRecipe(File meal, File ingredients) {
        model.performRequest("DELETE", null, null, null, name.trim(), null, username);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Refreshing recipe:" + name.trim());
        alert.showAndWait();
        model.performRequest("POST", meal, null, "mealTime", null, null, username);
        
        String ingR = model.performRequest("POST", null, ingredients, "ingredients", null, null, username);
        String response = model.performRequest("GET", null, null, null, ingR.trim(), null, username);
        String det = response.substring(response.indexOf("\n")+1);

        imageGenerator recipeImage = new imageGenerator(ingR);
        try{
            recipeImage.main();
        } catch (Exception e1){
            e1.printStackTrace();
        }
        String refreshImageURL = recipeImage.getImageURL();

        recLabel.setText(det);
        recNameMsg.setText(ingR);
        name = ingR;
        details = det;
        imageURL = refreshImageURL;

        if(imageURL != null && !imageURL.isEmpty()){
            this.imageView.setImage(new Image(imageURL));
        }

        addListeners();
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

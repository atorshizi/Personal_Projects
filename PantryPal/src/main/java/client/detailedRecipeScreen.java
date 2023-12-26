package main.java.client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.server.database;
import main.java.server.recipe;

public class detailedRecipeScreen {
    private Scene scene;
    private Label title;
    private Label recNameMsg;
    private TextArea recLabel;
    private Button editButton;
    private Button backButton; 
    private Button deleteButton; 
    private Button shareButton;
    private ScrollPane detailedRecipe;
    private Stage primaryStage;
    private boolean editing;
    private Model model;
    private String name;
    private String details;
    private String username;
    private String imageURL;
    private ImageView imageView;

    public detailedRecipeScreen(String username, Stage primaryStage, String name, String details, String imageURL) { 
        checkServer(); 
        StackPane root = new StackPane();
        title = new Label("PantryPal");
        recNameMsg = new Label(name);
        backButton = new Button("Back");
        editButton = new Button("Edit"); 
        deleteButton = new Button("Delete"); 
        shareButton = new Button("Share");
        this.primaryStage = primaryStage;
        editing = false;
        this.model = new Model();
        this.name = name;
        this.details = details;
        this.username = username;
        this.imageURL = imageURL;

         
        if(imageURL != null && !imageURL.isEmpty()){
            this.imageView = new ImageView(new Image(imageURL));
            this.imageView.setFitWidth(250);
            this.imageView.setFitHeight(250);
        }
        

        HBox r_buttons = new HBox(shareButton, editButton, deleteButton); 
        HBox heading = new HBox(backButton, title, r_buttons); 
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
        backButton.setOnAction(e -> {
            if (editing) {
                editing = false;
                editButton.setText("Edit");
                backButton.setText("Back");
                // Disable editing for detailedRecipe and reset text
                recLabel.setText(details);
                recLabel.setEditable(false);
            } else {
                recipesScreen rs = new recipesScreen(username, primaryStage);
                primaryStage.setScene(rs.getScene());
            }
        }); 
        deleteButton.setOnAction(e -> { 
            System.out.println("Deleting: " + name);
            String response = model.performRequest("DELETE", null, null, null, name, null, username);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Recipe deleted!");
            alert.showAndWait();
            System.out.println(response);
            //Go Back to recipesScreen
            recipesScreen rs = new recipesScreen(username, primaryStage);
            primaryStage.setScene(rs.getScene());
        });  

        editButton.setOnAction(e -> {
            if (!editing) {
                editing = true; 
                editButton.setText("Save");
                backButton.setText("Cancel");
                // Enable editing for detailedRecipe
                recLabel.setEditable(true);
            } else {
                editing = false;
                editButton.setText("Edit");
                backButton.setText("Back");
                recLabel.setEditable(false);
                model.performRequest("PUT", null, null, null, name, recLabel.getText(), username);
            }
        });

        shareButton.setOnAction(e -> {
            String link = model.shareRequest(username, name);
            if (link.contains("Invalid") || link.contains("Error")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to share recipe!");
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Recipe Link Generated and Copied to Clipboard: \n" + link);
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(link);
                clipboard.setContent(content);
                alert.showAndWait();
            }
        });
    } 
    private void checkServer(){ 
        Model model = new Model(); 
        String response = model.performRequest("GET", null, null, null, null, null,username); 
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

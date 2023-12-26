package test.MS2Tests;

import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.server.database;
import main.java.server.recipe;
import main.java.client.detailedRecipeScreen;
import main.java.client.Model;
import test.MockDatabase;

public class ScenarioTest5 {

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
    private String name2;
    private String details;
    private String imageURL;
    private ImageView imageView;
    boolean shared = false; //link has been shared and copied to clipboard
    boolean clickShare = true; //the share button has been pressed
    String createAlert = ""; //alert has been created

    public ScenarioTest5(){
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
        this.name2 = name2;
        this.details = details;
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
    @Test
    private void addListeners() {

        shareButton.setOnAction(e -> {
            String link = model.shareRequest(name, name2);
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
                shared = true;
            }
        });
        assert(shared);
    }
    
   /*  public void MS2ScenarioTest5()   { 
        String link = model.shareRequest("Sour Cream and Onion Quesadilla:");
            if(clickShare == true){
                createAlert = "Recipe Link Generated and Copied to Clipboard: \n" + link;
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(link);
                clipboard.setContent(content);
                //alert.showAndWait();
                shared = true;
            }
        assert(shared);
    } */
}


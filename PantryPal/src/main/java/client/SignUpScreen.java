package main.java.client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignUpScreen {
    private Button createButton; 
    private Button backButton; 
    private Label createMsg; 
    private TextField user; 
    private TextField pass; 
    private Label title; 
    private Scene scene;
    private Stage primaryStage;

    public SignUpScreen(Stage primaryStage) { 
        checkServer(); 
        this.primaryStage = primaryStage;
        user = new TextField(); 
        user.setPromptText("Username: "); 
        pass = new TextField(); 
        pass.setPromptText("Password: "); 
        createButton = new Button("Create Account"); 
        backButton = new Button("Back"); 
        createMsg = new Label("Create A New Login! "); 
        title = new Label("PantyPal 2.0"); 
        HBox buttonsBox = new HBox(createButton,backButton);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setSpacing(100);
        VBox vbT = new VBox(title);
        vbT.setAlignment(Pos.CENTER);
        VBox vb = new VBox(createMsg, user, pass, buttonsBox);
        vb.setAlignment(Pos.CENTER);
        BorderPane createScreen = new BorderPane();
        createScreen.setTop(vbT);
        createScreen.setCenter(vb);
        StackPane root = new StackPane();
        root.getChildren().addAll(createScreen);
        this.scene = new Scene(root, 400, 300);
        addListeners();
    }

    private void addListeners(){
        backButton.setOnAction(e -> {
            LoginScreen ls = new LoginScreen(primaryStage);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(ls.getScene());
        });

        createButton.setOnAction(e -> {
            String username = user.getText();
            String password = pass.getText();
            
            //Check if username and password are filled in
            if (username.equals("") || password.equals("")) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Missing Information");
                alert.setHeaderText("Missing Username or Password");
                alert.setContentText("Please enter a username and password");
                alert.showAndWait();
            } else {
                Model model = new Model();
                String response = model.performLoginRequest("POST", username, password);
                if (response.contains("java.net.ConnectException")) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Server Offline");
                    alert.setHeaderText("Request Failed");
                    alert.setContentText("Please start the server");
                    alert.showAndWait();
                } else if (response.equals("Failed to add user")) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Create Account Failed");
                    alert.setHeaderText("Failed to Create Account");
                    alert.setContentText("Please try again");
                    alert.showAndWait();
                } else if (response.equals("User already exists")) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Create Account Failed");
                    alert.setHeaderText("Failed to Create Account");
                    alert.setContentText("Username already exists");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Account Created");
                    alert.setHeaderText("Account Created");
                    alert.setContentText("Your login has been created");
                    alert.showAndWait();
                    LoginScreen ls = new LoginScreen(primaryStage);
                    Stage stage = (Stage) createButton.getScene().getWindow();
                    stage.setScene(ls.getScene());
                }
            }
        });
    }

    public Scene getScene() {
        return this.scene;
    } 
    private void checkServer(){ 
        Model model = new Model(); 
        String response = model.performRequest("GET", null, null, null, null, null, "test"); 
        if(response.contains("java.net.ConnectException")){ 
            serverError(); 
            response = model.performRequest("GET", null, null, null, null, null,"test"); 
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

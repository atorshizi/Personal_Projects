package main.java.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScreen{ 
    private Button signupButton; 
    private Button loginButton; 
    private CheckBox rememberMe;
    private Label loginMsg; 
    private TextField user; 
    private TextField pass; 
    private Label title; 
    private Scene scene;
    private Stage primaryStage;

    public LoginScreen(Stage primaryStage) { 
        checkServer(); 
        this.primaryStage = primaryStage;
        user = new TextField(); 
        user.setPromptText("Username: "); 
        pass = new TextField(); 
        pass.setPromptText("Password: "); 
        checkRemember();
        primaryStage.setTitle("PantryPal"); 
        loginButton = new Button("Log In"); 
        signupButton = new Button("Sign Up"); 
        rememberMe = new CheckBox("Remember Me");
        HBox buttons = new HBox(loginButton,signupButton,rememberMe); 
        buttons.setAlignment(Pos.CENTER); 
        buttons.setSpacing(80); 
        loginMsg = new Label("Login Information: "); 
        title = new Label("PantyPal"); 
        VBox vbT = new VBox(title); 
        vbT.setAlignment(Pos.CENTER); 
        VBox vb = new VBox(loginMsg, user, pass, buttons);  
        vb.setAlignment(Pos.CENTER); 
        BorderPane logscreen = new BorderPane(); 
        logscreen.setTop(vbT); 
        logscreen.setCenter(vb); 
        StackPane root = new StackPane(); 
        root.getChildren().addAll(logscreen);
        this.scene = new Scene(root, 400, 300); 
        addListeners();
    }

    public void addListeners() {
        loginButton.setOnAction(e -> {
            String username = user.getText();
            String password = pass.getText();
            
            //Check if username and password are filled in
            if (username.equals("") || password.equals("")) {
                Alert alert = new Alert(AlertType.ERROR, "Please enter a username and password", ButtonType.OK);
                alert.show();
            }else{
                Model model = new Model();
                String response = model.performLoginRequest("GET", username, password);
                if (response.contains("Invalid")) {
                    Alert alert = new Alert(AlertType.ERROR, "Invalid Username or Password", ButtonType.OK);
                    alert.show();
                    
                }else{
                    //Check remember me
                    if (rememberMe.isSelected()) {
                        //Save username and password
                        //Save info to local .dat file
                        File file = new File("src/main/java/client/user.dat");
                        if (!file.exists()) {
                            try {
                                file.createNewFile();
                            } catch (Exception e1) {
                                System.out.println("Error: " + e1);
                            }
                        }
                        //Save username and password to file
                        model.saveUser(username, password);
                    }
                    //Open home screen
                    homeScreen hs = new homeScreen(username, primaryStage);
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.setScene(hs.getScene());
                }
            }
        });

        signupButton.setOnAction(e -> {
            SignUpScreen ss = new SignUpScreen(primaryStage);
            Stage stage = (Stage) signupButton.getScene().getWindow();
            stage.setScene(ss.getScene());
        });

    }

    public Scene getScene() {
        return this.scene;
    } 

    private boolean checkRemember(){
        File file = new File("src/main/java/client/user.dat");
        if (file.exists()) {
            //Read file
            try{
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String username = br.readLine();
                String password = br.readLine();
                br.close();
                Model model = new Model();
                String response = model.performLoginRequest("GET", username, password);
                if (response.contains("Invalid")) {
                    Alert alert = new Alert(AlertType.ERROR, "Invalid Username or Password Saved", ButtonType.OK);
                    alert.show();
                    return false;
                }else{
                    user.setText(username);
                    pass.setText(password);
                    return true;
                }
            }catch(Exception e){
                System.out.println("Error: " + e);
                Alert alert = new Alert(AlertType.ERROR, "Error Reading Saved Username/Password Data", ButtonType.OK);
                alert.show();
                return false;
            }
        }else{
            return false;
        }
    }
    private void checkServer(){ 
        Model model = new Model(); 
        String response = model.performRequest("GET", null, null, null, null, null,"test"); 
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

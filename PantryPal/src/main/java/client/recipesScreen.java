package main.java.client;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane; 
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.java.server.imageGenerator; 
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


public class recipesScreen {
    private Stage primaryStage;
    private Scene scene;
    private Label title;
    private Label welcomeMsg;
    private MenuButton sortButton; 
    private MenuButton filterButton;
    private Button backButton;
    private ScrollPane recipes;
    private Model model; 
    private String username;
    private String filter = "all"; 
    private int sort = 1; 

    public recipesScreen(String username, Stage primaryStage) { 
        checkServer(); 
        StackPane root = new StackPane();
        title = new Label("PantryPal");
        welcomeMsg = new Label("Saved Recipes");
        backButton = new Button("Back");
        this.model = new Model();
        this.primaryStage = primaryStage;
        this.username = username;

        filterButton = new MenuButton("Filter by Meal");
        MenuItem all = new MenuItem("All");
        MenuItem breakfast = new MenuItem("Breakfast");
        MenuItem lunch = new MenuItem("Lunch");
        MenuItem dinner = new MenuItem("Dinner"); 
        filterButton.getItems().addAll(all, breakfast, lunch, dinner); 

        sortButton = new MenuButton("Sort"); 
        MenuItem name = new MenuItem("name");
        MenuItem time1 = new MenuItem("Time (new)"); 
        MenuItem time2 = new MenuItem("Time(old)"); 
        sortButton.getItems().addAll(name, time1, time2); 

        backButton.setOnAction(e -> {
            homeScreen hs = new homeScreen(username, primaryStage);
            primaryStage.setScene(hs.getScene());
        });

        HBox heading = new HBox(backButton, title, sortButton, filterButton); 
        heading.setAlignment(Pos.CENTER);
        heading.setSpacing(80);
        VBox text = new VBox(heading, welcomeMsg);
        text.setAlignment(Pos.CENTER);

        VBox uiElement = new VBox(); 
        setList(uiElement, "all"); 
        recipes = new ScrollPane(uiElement);

        BorderPane recipeListScreen = new BorderPane();
        recipeListScreen.setTop(text);
        recipeListScreen.setCenter(recipes);
        root.getChildren().addAll(recipeListScreen);
        this.scene = new Scene(root, 700, 300); 

        name.setOnAction(e -> { 
            this.sort = 1; 
            sortBy(recipeListScreen,this.sort); 
        }); 
        time1.setOnAction(e -> { 
            this.sort = 2; 
            sortBy(recipeListScreen,this.sort); 
        }); 
        time2.setOnAction(e -> { 
            this.sort = 3; 
            sortBy(recipeListScreen,this.sort); 
        }); 


        all.setOnAction(e -> { 
            this.filter = "all"; 
            // VBox uiE = new VBox();
            // setList(uiE, filter); 
            sortBy(recipeListScreen,this.sort); 
            // recipes = new ScrollPane(uiE);
            // recipeListScreen.setCenter(recipes); 
            // primaryStage.setScene(this.scene); 
        }); 
        breakfast.setOnAction(e -> { 
            this.filter = "Breakfast"; 
            sortBy(recipeListScreen,this.sort); 
            // VBox uiE = new VBox();
            // setList(uiE, filter); 
            // recipes = new ScrollPane(uiE);
            // recipeListScreen.setCenter(recipes);
            // primaryStage.setScene(this.scene); 
        });
        lunch.setOnAction(e -> { 
            this.filter = "Lunch"; 
            sortBy(recipeListScreen,this.sort); 
            // VBox uiE = new VBox();
            // setList(uiE, filter); 
            // recipes = new ScrollPane(uiE);
            // recipeListScreen.setCenter(recipes); 
            // primaryStage.setScene(this.scene); 
        });
        dinner.setOnAction(e -> { 
            this.filter = "Dinner"; 
            sortBy(recipeListScreen,this.sort); 
            // VBox uiE = new VBox();
            // setList(uiE, filter);
            // recipes = new ScrollPane(uiE);
            // recipeListScreen.setCenter(recipes); 
            // primaryStage.setScene(this.scene); 
        }); 
    } 
    public Scene getScene() {
        return this.scene;
    } 
    private void sortBy(BorderPane recipeListScreen, int type){ 
        if (type == 3){ 
            VBox uiE = new VBox();
            setList(uiE, this.filter); 
            ObservableList<Node> suiE = uiE.getChildren(); 
            VBox SorteduiE = new VBox(); 
            SorteduiE.getChildren().addAll(suiE); 
            recipes = new ScrollPane(SorteduiE); 
            recipeListScreen.setCenter(recipes); 
            primaryStage.setScene(this.scene); 
        } else if (type == 2){ 
            VBox uiE = new VBox();
            setList(uiE, this.filter); 
            ObservableList<Node> suiE = uiE.getChildren(); 
            ArrayList<Node> suiEr = new ArrayList<>(); 
            for (int i=suiE.size()-1; i>=0; i--){ 
                Node n = suiE.get(i); 
                suiEr.add(n); 
            } 
            VBox SorteduiE = new VBox(); 
            SorteduiE.getChildren().addAll(suiEr); 
            recipes = new ScrollPane(SorteduiE); 
            recipeListScreen.setCenter(recipes); 
            primaryStage.setScene(this.scene); 
        } else{ 
            VBox uiE = new VBox(); 
            setList(uiE, this.filter); 
            // SortedList<Node> suiE = uiE.getChildren().sorted((a,b) -> (((Button)(((HBox)a).getChildren().get(1))).getText().compareTo(((Button)(((HBox)b).getChildren().get(1))).getText()))); 
            ObservableList<Node> suiE = uiE.getChildren(); 
            ArrayList<Node> suiEr = new ArrayList<>(); 
            for (int i=0; i<suiE.size(); i++){ 
                Node n = suiE.get(i); 
                suiEr.add(n); 
            } 
            suiEr.sort((a,b) -> (((Button)(((HBox)a).getChildren().get(1))).getText().compareTo(((Button)(((HBox)b).getChildren().get(1))).getText()))); 
            
            VBox SorteduiE = new VBox(); 
            SorteduiE.getChildren().addAll(suiEr); 
            recipes = new ScrollPane(SorteduiE); 
            recipeListScreen.setCenter(recipes); 
            primaryStage.setScene(this.scene); 
        }
    } 


    public void setList(VBox list, String mealFilter) {
        String response = model.performRequest("GET", null, null, null, "ALL", null, username);
        String[] recipes = response.split("\\|");
        System.out.println(recipes.length);
        for (int i = 0; i < recipes.length - 1; i++) {
            System.out.println(i);
            Button rec = new Button(recipes[i]);
            String recResponse = model.performRequest("GET", null, null, null, recipes[i], null, username);
            System.out.println("recresponse: ");
            System.out.println(recResponse);
            System.out.println("done");
            String[] mealDetails = recResponse.split("\\$");
            String mealType = mealDetails[0];
            String details = mealDetails[1];
            String imageURL;

            //String imageURL = mealDetails[2];
            //need to get imageURL from mongoDB JSON object
            //String imageURL = "https://oaidalleapiprodscus.blob.core.windows.net/private/org-Sd9bwBmEf5IDns4KIh3k3fXp/user-mlF2UiRIjgQpU5OH9QHus5gd/img-Mxf2GRV2JwmSkO7yzxs2xQz1.png?st=2023-12-05T10%3A32%3A23Z&se=2023-12-05T12%3A32%3A23Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-12-04T16%3A34%3A12Z&ske=2023-12-05T16%3A34%3A12Z&sks=b&skv=2021-08-06&sig=ZrHOLUPfVtALS316YtqbVBwngjn6zAtLgjuJDqGBLAg%3D";
           
           
           //regenerates image each time if it does not exist already locally
           //not saving in mongoDb since imageURL expires after around 2hrs
           
           
            String localImage = recipes[i]+"image.png";
            File imageFile = new File(localImage);
            if(!imageFile.exists()){ 
                System.out.println("Not exist"); 
                imageGenerator recipeImage = new imageGenerator(recipes[i]);
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
                       
            System.out.println(recipes[i]);
            if (mealType.equals(mealFilter) || mealFilter.equals("all")) {
                Label mealLabel = new Label(mealType);
                switch (mealType) {
                    case "Breakfast":
                        mealLabel.setTextFill(Color.color(0, 0, 1));
                        break;
                    case "Lunch":
                        mealLabel.setTextFill(Color.color(1, 0, 0));
                        break;
                    case "Dinner":
                        mealLabel.setTextFill(Color.color(0, 1, 0));
                        break;
                    default:
                        mealLabel.setTextFill(Color.color(0, 1, 1));
                        break;
                }

                ImageView imageView = new ImageView(new Image(imageURL));
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);

                String name = recipes[i];
                rec.setStyle("-fx-background-color: #00000000; ");
                rec.setMaxHeight(10);
                rec.setMaxWidth(list.getMaxWidth());
                rec.setOnAction(e -> {
                    detailedRecipeScreen dsr = new detailedRecipeScreen(username, primaryStage, name, details, imageURL);
                    primaryStage.setScene(dsr.getScene());
                });
                HBox hb = new HBox(imageView, rec, mealLabel);
                hb.setAlignment(Pos.CENTER_LEFT);
                list.getChildren().add(hb);
            }
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
    public static void sortRec(String by, ArrayList<String> list){ 
        switch (by) { 
            case "name": 
                list.sort((a,b) -> a.compareTo(b)); 
                break;
            case "time (new)": 
                // ArrayList<String> listR = new ArrayList<>(); 
                // for (int i = list.size()-1 ; i <= 0 ; i--){ 
                //     listR.add(list.get(i)); 
                // } 
                // list.clear(); 
                // for (String s : listR){ 
                //     list.add(s); 
                // }
                Collections.reverse(list);
                break; 
            case "time (old)": 
                list = list; 
                break; 
            default:
                break;
        }
    } 
}

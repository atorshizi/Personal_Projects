# **Pantry Pal**
---
An app to allow users to keep track of their recipes using the ingredients they have on hand. 

Instructions on how to run app: 

Once the repository has been cloned, open using vscode. Build gradle in the same directory. Create a launch.json file in the debug panel with the following configuration included : 


{
    "type": "java",
    "name": "PantryPal",
    "request": "launch",
    "mainClass": "main.java.Main",
    "vmArgs": "--module-path '<FILEPATH>' --add-modules javafx.controls,javafx.fxml"
} 


where FILEPATH points to the install location of the javafx libraries. If javafx has not been installed, refer to their documentation to download the libraries. Open the server.java file and run in vscode (or alternatively add to configs to start) to start the server. Then in the debug panel, select PantryPal and run. This will launch the GUI - if prompted to select, press fix and select main.java.client.Main. Then a new recipe can be generated in the new recipe button in which you can record the meal type and ingredients and save to the database. Previously saved recipies can be found in the saved recipes button. Here, there is also the option to view in detail by pressing on a recipe name in which you can also edit or delete a recipe from the database all together. When all done using the app, press the close button and shut down the local server by pressing the stop symbol in the vscode pop-up. 

---
Alternatively, the app can be set up by running

`./gradlew run`

which builds and runs the application.


The HttpServer needs to then be started by navigating to 
`src\main\java\server\Server.java` which then needs to be started in order to handle the necessary API requests.
package main.java.server;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Updates.*;


import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class database {
    private String uri;

    public database() {
        // Replace the placeholder with your MongoDB deployment's connection string
        this.uri = "mongodb+srv://pantryPal:team41@pantrypal.2gh1r0b.mongodb.net/?retryWrites=true&w=majority";
        /*
         * Document doc = collection.find(eq("title", "Back to the Future")).first();
         * if (doc != null) {
         * System.out.println(doc.toJson());
         * } else {
         * System.out.println("No matching documents found.");
         * }
         */
    }

    public void addFileToDb(String user) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("Recipes");
            MongoCollection<Document> collection = database.getCollection(user);
            addRecipeFile(user, collection, "src/main/java/recipe.txt");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Iterator<Document> getAll(String user) {
        String uri = "mongodb+srv://pantryPal:team41@pantrypal.2gh1r0b.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("Recipes");
            MongoCollection<Document> collection = database.getCollection(user);
            return collection.find().iterator();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private void addRecipeFile(String user, MongoCollection<Document> collection, String fp) {

            String mealType = "mealType";
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/meal.txt"))) {
                String line; 
                if ((line = br.readLine()) != null) {
                    mealType = line;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        
            ArrayList<String> lines = (ArrayList<String>) processFile(user, fp);
            String title = lines.get(0);
            String ingredients = lines.get(1);
            String instructions = lines.get(2);

            imageGenerator recipeImage = new imageGenerator(title);
            try{
                recipeImage.main();
            } catch (Exception e){
                e.printStackTrace();
            }
    
            String generatedImageURL = recipeImage.getImageURL(); 

            Document doc = new Document();
            doc.append("title", title);
            doc.append("mealType", mealType);
            doc.append("ingredients", ingredients);
            doc.append("instructions", instructions);           
            doc.append("imageURL", generatedImageURL);

            collection.insertOne(doc);
            System.out.println("data added to mongoDB");
  
    }

    public void clearDB(String user) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("Recipes");
            MongoCollection<Document> collection = database.getCollection(user);
            collection.deleteMany(new Document());
            System.out.println("Cleared Database");
        } catch (Exception e) {
            System.out.println(e);
        } 
    }

    public void editRecipe(String user, String recipeName, String updated){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("Recipes");
            MongoCollection<Document> collection = database.getCollection(user);
            ArrayList<String> lines = (ArrayList<String>) processEdit(user, updated);
            Bson filter = eq("title", recipeName);
            Bson updateIng = set("ingredients", lines.get(0));
            Bson updateIns = set("instructions", lines.get(1));
            Bson combined = combine(updateIng, updateIns);
            UpdateResult result = collection.updateOne(filter, combined);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<String> processFile(String user, String fp) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fp))) {
            StringBuilder title = new StringBuilder();
            StringBuilder ingredients = new StringBuilder();
            StringBuilder instructions = new StringBuilder();
            String line;

            boolean isIngredients = false;
            boolean isInstructions = false;
            while ((line = br.readLine()) != null) {
                if (line.contains("Ingredient")) {
                    isIngredients = true;
                    isInstructions = false;
                } else if (line.contains("Instruction")) {
                    isIngredients = false;
                    isInstructions = true;
                } else if (isIngredients) {
                    ingredients.append(line).append("\n");
                } else if (isInstructions) {
                    instructions.append(line).append("\n");
                } else {
                    title.append(line).append("\n");
                }
            }
            lines.add(title.toString().trim());
            lines.add(ingredients.toString().trim());
            lines.add(instructions.toString().trim());

            

        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        
        return lines;
    }


    private List<String> processEdit(String user, String edited){
        ArrayList<String> lines = new ArrayList<>();
        String[] split = edited.split("\n");
        StringBuilder ingredients = new StringBuilder();
        StringBuilder instructions = new StringBuilder();
        boolean isIngredients = false;
        boolean isInstructions = false;
        for (String line : split) {
            if (line.contains("Ingredients")) {
                isIngredients = true;
                isInstructions = false;

            } else if (line.equals("Instructions")) {
                isIngredients = false;
                isInstructions = true;
            } else if (isIngredients) {
                ingredients.append(line).append("\n");
            } else if (isInstructions) {
                instructions.append(line).append("\n");
            }
        } 
        lines.add(ingredients.toString().trim());
        lines.add(instructions.toString().trim());
        return lines;
    }

    public recipe getRecipe(String user, String title){
        Iterator<Document> itr = this.getAll(user);
        while(itr.hasNext()){
            Document doc = itr.next();
            if(doc.getString("title").equals(title)){
                return new recipe(doc.getString("title"), doc.getString("mealType"), "Ingredients: \n" + doc.getString("ingredients") + "\nInstructions: \n" + doc.getString("instructions"), doc.getString("imageURL"));
            }
        }
        return null;
    } 
    public boolean deleteRecipe(String user, String title){ 
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("Recipes");
            MongoCollection<Document> collection = database.getCollection(user);
            collection.deleteOne(new Document().append("title", title)); 
            System.out.println("Deleted " + title); 
            return true; 
        } catch (Exception e) { 
            System.out.println(e);
        } 
        return false; 
    }

    public boolean validUser(String username, String password){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("Users");
            MongoCollection<Document> collection = database.getCollection("users");
            Document doc = collection.find(eq("username", username)).first();
            if(doc != null){
                if(doc.getString("password").equals(password)){
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean addUser(String username, String password){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("Users");
            MongoCollection<Document> collection = database.getCollection("users");
            Document doc = collection.find(eq("username", username)).first();
            if(doc == null){
                Document newUser = new Document();
                newUser.append("username", username);
                newUser.append("password", password);
                collection.insertOne(newUser);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean clearRecipes(String user){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("Recipes");
            MongoCollection<Document> collection = database.getCollection(user);
            collection.drop();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean containsUser(String username){
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("Users");
            MongoCollection<Document> collection = database.getCollection("users");
            Document doc = collection.find(eq("username", username)).first();
            if(doc != null){
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}

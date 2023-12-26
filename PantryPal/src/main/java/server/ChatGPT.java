package main.java.server;

import java.io.FileWriter;
import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChatGPT {

    private static final String MODEL = "text-davinci-003";
    private static final String API_KEY = "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";
    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";

    // private String mealType;
    // private String ingredients;
    private String prompt;
    private String mealType;

    public ChatGPT(String prompt, String mealType) {
        this.mealType = mealType;
        this.prompt = prompt;
    }

    public void main() throws Exception {

        // String recipe = generateRecipe(mealType, ingredients);
        String recipe = generateRecipe(prompt);

        saveRecipe("src/main/java/recipe.txt","src/main/java/meal.txt", recipe, mealType);
    }

    // generates recipe from chatGPT
    private static String generateRecipe(String prompt) throws Exception {
        /*
         * String prompt = "Give me a concise recipe for a " +
         * mealType +
         * " meal that ONLY uses the following ingredients: " +
         * ingredients;
         */

        int maxTokens = 250;

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 1.0);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(new URI(API_ENDPOINT))
                .header("Content-Type", "application/json")
                .header("Authorization", String.format("Bearer %s", API_KEY))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();

        JSONObject responseJSON = new JSONObject(responseBody);
        JSONArray choices = responseJSON.getJSONArray("choices");
        String recipeGPT = choices.getJSONObject(0).getString("text");

        return recipeGPT;
    }

    // saves recipe to a file
    private static void saveRecipe(String fp, String fpMeal, String recipe, String mealType) {
        try {
            recipe = recipe.trim();
            FileWriter fw = new FileWriter(fp);
            FileWriter fm = new FileWriter(fpMeal);
            fm.write(mealType.trim());
            fw.write(recipe);
            fw.close();
            fm.close();
            System.out.println("Recipe saved to " + fp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    public String getResponse(){ 
        String response = null; 
        return response; 
    }

}

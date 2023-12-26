package main.java.server;
import java.io.FileWriter;
import java.io.IOException;

public class MockGPT implements IChatGPT{

    @Override
    public String generateRecipe(String prompt) {
        String recipe = "Chicken Burrito 1. Heat up a tortilla 2. Cook 200g of Chicken Breast 3. Cook 1 cup of rice 4. Cook up 1 cup of black beans";
        return recipe;
    }

    @Override
    public void saveRecipe(String fp, String recipe) {
        try {
            FileWriter fw = new FileWriter(fp);
            fw.write(recipe);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

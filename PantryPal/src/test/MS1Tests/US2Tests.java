package test.MS1Tests;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import main.java.server.database;

/*
 * A class to test tasks related to US2.
 * Specifically aimed at testing saving recipes into db.
 */
public class US2Tests {
    @Test
    void testRecipeFileExists() {
        File recipe = new File("src/main/java/recipe.txt");
        assert(recipe.exists());
    }

    @Test
    void testConnectToDB() {
        database db = new database();
        assert(db != null);
    }

    @Test
    void testFileReading() {
        database db = new database();
        File tempFile = new File("src/main/temp.txt");
        try{
            tempFile.createNewFile();
            FileWriter writer = new FileWriter(tempFile);
            writer.write("Test Recipe\n");
            writer.write("Ingredients:\n Chicken\n Rice\n Eggs");
            writer.write("Instructions:\n Cook Chicken\n Cook Rice\n Cook Eggs");
            writer.close();
            List<String> recipe = db.processFile("test",tempFile.toPath().toString());
            assert(recipe.size() == 3);
            assert(recipe.get(0).length() > 0);
            assert(recipe.get(1).length() > 0);
            assert(recipe.get(2).length() > 0);
            tempFile.delete();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}

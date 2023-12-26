package test.MS2Tests; 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List; 
import org.junit.jupiter.api.Test; 
import javafx.stage.Stage;
import main.java.client.LoginScreen;
import main.java.client.recipesScreen;
import main.java.server.Server; 
import main.java.client.Main; 

public class US6Testing { 
    @Test
    void testSortName() throws IOException { 
        String test1 = "Rec Test 1"; 
        String test3 = "Rec Test 3"; 
        String test2 = "Rec Test 2"; 
        ArrayList<String> mockDB = new ArrayList<>(); 
        mockDB.add(test1); 
        mockDB.add(test3); 
        mockDB.add(test2); 
        recipesScreen.sortRec("name", mockDB); 
        ArrayList<String> actual = new ArrayList<>(); 
        actual.add(test1); 
        actual.add(test2); 
        actual.add(test3); 
        assertEquals(mockDB, actual); 
    } 

    @Test
    void testSortTime1() throws IOException { 
        String test1 = "Rec Test 1"; 
        String test3 = "Rec Test 3"; 
        String test2 = "Rec Test 2"; 
        ArrayList<String> mockDB = new ArrayList<>(); 
        mockDB.add(test1); 
        mockDB.add(test3); 
        mockDB.add(test2); 
        recipesScreen.sortRec("time (old)", mockDB); 
        ArrayList<String> actual = new ArrayList<>(); 
        actual.add(test1);  
        actual.add(test3); 
        actual.add(test2); 
        assertEquals(mockDB, actual); 
    } 

    @Test
    void testSortTime2() throws IOException { 
        String test1 = "Rec Test 1"; 
        String test3 = "Rec Test 3"; 
        String test2 = "Rec Test 2"; 
        ArrayList<String> mockDB = new ArrayList<>(); 
        mockDB.add(test1); 
        mockDB.add(test3); 
        mockDB.add(test2); 
        recipesScreen.sortRec("time (new)", mockDB); 
        ArrayList<String> actual = new ArrayList<>(); 
        actual.add(test2);  
        actual.add(test3); 
        actual.add(test1); 
        assertEquals(mockDB, actual); 
    } 
}

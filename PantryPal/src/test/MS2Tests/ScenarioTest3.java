package test.MS2Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.client.Main;
import main.java.server.Server;
import main.java.server.recipe;

import java.io.IOException;
import java.util.ArrayList;

public class ScenarioTest3 { 
    /* 
     * When openning app without sever running, get error message, start sever then try again and it works 
     */
    @Test
    void testScenario() throws IOException { 
        // no server running 
        Server sv = new Server(); 
        Main mn = new Main(); 
        assertEquals(mn.displayError(), 0); // error message is shown and given exit code 0 
        // start server 
        sv.startServer(null); 
        Main mn2 = new Main(); 
        assertEquals(mn2.displayError(), 1); // error message is now shown 
        sv.endServer(null); 
    } 
}

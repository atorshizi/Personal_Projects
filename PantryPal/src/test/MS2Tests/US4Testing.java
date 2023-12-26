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
import main.java.server.Server; 
import main.java.client.Main; 

public class US4Testing { 
    @Test
    void testServerNotRunning() throws IOException { 
        Server sv = new Server(); 
        // sv.startServer(null); 
        Main mn = new Main(); 
        assertEquals(mn.displayError(), 0); 
        // sv.endServer(null); 
    } 

    @Test
    void testServerRunning() throws IOException { 
        Server sv = new Server(); 
        sv.startServer(null); 
        Main mn = new Main(); 
        assertEquals(mn.displayError(), 1); 
        sv.endServer(null); 
    } 
}

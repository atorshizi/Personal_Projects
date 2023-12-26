package test.MS2Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.server.IDallE;
import main.java.server.recipe;
import test.MockImageGenerate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;


public class MS2US2Test {
 
    @Test
    void testGenerateImage(){
        String prompt = "mock recipe";
        String name = "image test";
        IDallE recImage = new MockImageGenerate();
        String imageURL = recImage.generateImage(prompt);
        String localImage = recImage.saveImage(imageURL, name);

        assertEquals("src/test/MS2Tests/imageMock.png", localImage);
    }
       
}

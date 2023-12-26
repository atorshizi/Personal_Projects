package test;

import main.java.server.IDallE;

public class MockImageGenerate implements IDallE{

    @Override
    public String generateImage(String imagePrompt){
        String imageURL = "imageURL";
        return imageURL;
    }

    public String saveImage(String imageURL, String name){
        String savedImage = "src/test/MS2Tests/imageMock.png";
        return savedImage;

    }

    
}

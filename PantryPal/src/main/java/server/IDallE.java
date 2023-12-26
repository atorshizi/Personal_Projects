package main.java.server;

public interface IDallE {
    public String generateImage(String imagePrompt);
    public String saveImage(String generatedImageURL, String name);
    
}

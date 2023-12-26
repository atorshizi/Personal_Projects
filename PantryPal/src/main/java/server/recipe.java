package main.java.server;

public class recipe {
    private String name;
    private String recDetails;
    private String mealType;
    private String imageURL;

    public recipe(String recName, String mealType, String details, String imageURL) {
        this.name = recName;
        this.mealType = mealType;
        this.recDetails = details;
        this.imageURL = imageURL;
    }

    public String getName() {
        return this.name;
    }

    public String getDetails() {
        return this.recDetails;
    }

    public String getMealType() {
        return this.mealType;
    }

    public String getImageURL(){
        return this.imageURL;
    }


    @Override
    public String toString() {
        return this.name + " " + this.recDetails;
    }
}

package FeedMe.Auth.Authorization.models;

import java.util.List;

public class Recipe {

    private String label;
    private String image;
    private String url;
    private List<String> ingredientLines;
    private String cuisineType;
    private String mealType;
    private String dishType;

    public Recipe(String label,
                  String image,
                  String url,
                  List<String> ingredientLines,
                  String cuisineType,
                  String mealType,
                  String dishType) {
        this.label = label;
        this.image = image;
        this.url = url;
        this.ingredientLines = ingredientLines;
        this.cuisineType = cuisineType;
        this.mealType = mealType;
        this.dishType = dishType;
    }

    public String getLabel() {
        return label;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getIngredientLines() {
        return ingredientLines;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public String getMealType() {
        return mealType;
    }

    public String getDishType() {
        return dishType;
    }

}

package FeedMe.Auth.Authorization.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {

    private String label;
    private String image;
    private String url;
    private List<String> ingredientLines;
    private String cuisineType;
    private String mealType;
    private String dishType;

    public Recipe(){}

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

    public void setLabel(String label) {
        this.label = label;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIngredientLines(List<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

}

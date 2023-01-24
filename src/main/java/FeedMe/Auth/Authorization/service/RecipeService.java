package FeedMe.Auth.Authorization.service;

import FeedMe.Auth.Authorization.EdamamKeys;
import FeedMe.Auth.Authorization.models.dto.EdamamQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecipeService {

    @Autowired
    private RestTemplate template = new RestTemplate();

    @Autowired
    private EdamamKeys edamamKeys;

    public EdamamQuote findBySearchIngredients(String searchIngredients) {
        return template.getForObject
                (String.format("https://api.edamam.com/api/recipes/v2?type=public&beta=false&" +
                        "q=%s&" +
                        "app_id=%s&" +
                        "app_key=%s&" +
                        "random=true&" +
                        "field=uri&" +
                        "field=label&" +
                        "field=image&" +
                        "field=url&" +
                        "field=ingredientLines&" +
                        "field=cuisineType&" +
                        "field=mealType&" +
                        "field=dishType", searchIngredients, edamamKeys.getAppId(), edamamKeys.getAppKey()), EdamamQuote.class);
    }

    public EdamamQuote findBySearchHealthTags(String searchHealthRequirements) {
        return template.getForObject
                (String.format("https://api.edamam.com/api/recipes/v2?type=public&beta=false&" +
                        "app_id=%s&" +
                        "app_key=%s&" +
                        "health=%s&" +
                        "random=true&" +
                        "field=uri&" +
                        "field=label&" +
                        "field=image&" +
                        "field=url&" +
                        "field=ingredientLines&" +
                        "field=cuisineType&" +
                        "field=mealType&" +
                        "field=dishType", edamamKeys.getAppId(), edamamKeys.getAppKey(), searchHealthRequirements), EdamamQuote.class);
    }

    public EdamamQuote findBySearchIngredientsAndHealthTags(String searchIngredients, String searchHealth) {
        return template.getForObject
                (String.format("https://api.edamam.com/api/recipes/v2?type=public&beta=false&" +
                        "q=%s&" +
                        "app_id=%s&" +
                        "app_key=%s&" +
                        "health=%s&" +
                        "random=true&" +
                        "field=uri&" +
                        "field=label&" +
                        "field=image&" +
                        "field=url&" +
                        "field=ingredientLines&" +
                        "field=cuisineType&" +
                        "field=mealType&" +
                        "field=dishType", searchIngredients, edamamKeys.getAppId(), edamamKeys.getAppKey(), searchHealth), EdamamQuote.class);
    }

}

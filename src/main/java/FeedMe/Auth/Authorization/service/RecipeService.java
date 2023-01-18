package FeedMe.Auth.Authorization.service;

import FeedMe.Auth.Authorization.EdamamKeys;
import FeedMe.Auth.Authorization.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecipeService {

    @Autowired
    RestTemplate template = new RestTemplate();

    private String app_id = EdamamKeys.getApp_id();
    private String app_key = EdamamKeys.getApp_key();

    public Recipe[] findBySearchTerms(String searchTerms) {
        return template.getForObject
                ("https://api.edamam.com/api/recipes/v2?type=public&beta=false&" +
                        "q=" +
                        searchTerms +
                        "&app_id=15a9aaa0" +
                        "&app_key=2ed89ec87d7b69f8e0f97a69ff68d790" +
                        "&random=true&" +
                        "field=label&" +
                        "field=image&" +
                        "field=url&" +
                        "field=ingredientLines&" +
                        "field=cuisineType&" +
                        "field=mealType&" +
                        "field=dishType", Recipe[].class);
    }

}

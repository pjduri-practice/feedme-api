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

    private static final String app_id = EdamamKeys.getApp_id();
    private static final String app_key = EdamamKeys.getApp_key();

    public EdamamQuote findBySearchTerms(String searchTerms) {
        return template.getForObject
                (String.format("https://api.edamam.com/api/recipes/v2?type=public&beta=false&" +
                        "q=%s" +
                        "&app_id=%s" +
                        "&app_key=%s" +
                        "&random=true&" +
                        "field=uri&" +
                        "field=label&" +
                        "field=image&" +
                        "field=url&" +
                        "field=ingredientLines&" +
                        "field=cuisineType&" +
                        "field=mealType&" +
                        "field=dishType", searchTerms, app_id, app_key), EdamamQuote.class);
    }

}

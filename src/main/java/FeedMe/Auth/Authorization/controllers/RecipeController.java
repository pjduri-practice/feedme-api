package FeedMe.Auth.Authorization.controllers;

import FeedMe.Auth.Authorization.models.dto.EdamamQuote;
import FeedMe.Auth.Authorization.models.Recipe;
import FeedMe.Auth.Authorization.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/public/")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/recipes/search")
    public List<Object> searchForRecipe(String searchTerms) {
        searchTerms = "chicken";

        ResponseEntity<EdamamQuote> edamamResponse =
                new ResponseEntity<>(recipeService.findBySearchTerms(searchTerms), HttpStatus.OK);
        List<Object> recipeList = Objects.requireNonNull(edamamResponse.getBody()).getHits();
        return recipeList;
    }

}

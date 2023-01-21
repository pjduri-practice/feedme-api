package FeedMe.Auth.Authorization.controllers;

import FeedMe.Auth.Authorization.models.dto.EdamamQuote;
import FeedMe.Auth.Authorization.models.Recipe;
import FeedMe.Auth.Authorization.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/search/ingredients/{ingredients}")
    public List<Object> searchForRecipeByIngredients(@PathVariable("ingredients") String ingredients) {

        ResponseEntity<EdamamQuote> edamamResponse =
                new ResponseEntity<>(recipeService.findBySearchTerms(ingredients), HttpStatus.OK);
        List<Object> recipeList = Objects.requireNonNull(edamamResponse.getBody()).getHits();
        return recipeList;
    }

}

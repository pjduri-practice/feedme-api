package FeedMe.Auth.Authorization.controllers;

import FeedMe.Auth.Authorization.models.Recipe;
import FeedMe.Auth.Authorization.service.EdamamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/public/")
public class RecipeController {

    @Autowired
    EdamamService edamamService;

    @GetMapping("/recipes/search")
    public ResponseEntity<Recipe[]> searchForRecipe(String searchTerms) {
        searchTerms = "chicken";
        return new ResponseEntity<>(edamamService.findBySearchTerms(searchTerms), HttpStatus.OK);
    }

}

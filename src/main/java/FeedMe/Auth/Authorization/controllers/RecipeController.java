package FeedMe.Auth.Authorization.controllers;

import FeedMe.Auth.Authorization.models.Recipe;
import FeedMe.Auth.Authorization.service.RecipeService;
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
    RecipeService recipeService;

    @GetMapping("/recipes/search")
    public ResponseEntity<Recipe[]> searchForRecipe(String searchTerms, Recipe recipe) {
        searchTerms = "chicken";
        return new ResponseEntity<>(recipeService.findBySearchTerms(searchTerms), HttpStatus.OK);
    }

}


//    @GetMapping("/admin/choiceColumns")
//    public ResponseEntity<List<ChoiceColumn>> getAllChoiceColumns(@RequestParam(required = false) String name) {
//        try {
//            List<ChoiceColumn> choiceColumns = new ArrayList<ChoiceColumn>();
//
//            if (name == null)
//                choiceColumnRepository.findAll().forEach(choiceColumns::add);
//            else
//                choiceColumns.addAll(choiceColumnRepository.findByNameContaining(name));
//
//            if (choiceColumns.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//
//            return new ResponseEntity<>(choiceColumns, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


//    @GetMapping("/choiceColumns/{id}")
//    public ResponseEntity<ChoiceColumn> getChoiceColumnById(@PathVariable("id") int id) {
//        Optional<ChoiceColumn> choiceColumnData = choiceColumnRepository.findById(id);
//
//        return choiceColumnData.map(choiceColumn ->
//                new ResponseEntity<>(choiceColumn, HttpStatus.OK)).orElseGet(() ->
//                new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

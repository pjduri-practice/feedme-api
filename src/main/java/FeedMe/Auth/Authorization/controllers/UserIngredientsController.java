package FeedMe.Auth.Authorization.controllers;

import FeedMe.Auth.Authorization.data.UserIngredientsRepository;
import FeedMe.Auth.Authorization.data.UserRepository;
import FeedMe.Auth.Authorization.models.ChoiceColumn;
import FeedMe.Auth.Authorization.models.User;
import FeedMe.Auth.Authorization.models.UserIngredients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/user-ingredients")
public class UserIngredientsController {

    @Autowired
    private UserIngredientsRepository userIngredientsRepository;

    @Autowired
    private UserRepository userRepository;

    public User getLoggedInUser(Authentication authentication) {
        String username = null;
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }

        if (username != null) {

            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent()) {

                User userEntity = user.get();
                return userEntity;
            }
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<List<UserIngredients>> getUserIngredientsByUser(Authentication authentication) {
        try {
            User user = getLoggedInUser(authentication);
            List<UserIngredients> userIngredients = userIngredientsRepository.findByUser(user);

            if (userIngredients.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(userIngredients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("{id}")
    public ResponseEntity<UserIngredients> getUserIngredientsByUserAndId(@PathVariable("id") int id,
                                                                   Authentication authentication) {
        User user = getLoggedInUser(authentication);
        Optional<UserIngredients> userIngredientsData = userIngredientsRepository.findByUserAndId(user, id);

        return userIngredientsData.map(choiceColumn ->
                new ResponseEntity<>(choiceColumn, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<UserIngredients> createUserIngredients(@RequestBody UserIngredients userIngredients,
                                                           Authentication authentication) {
        try {
            UserIngredients _userIngredients = userIngredientsRepository
                    .save(new UserIngredients(userIngredients.getName(),
                            getLoggedInUser(authentication)));
            return new ResponseEntity<>(_userIngredients, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<UserIngredients> updateUserIngredients(@PathVariable("id") int id,
                                                           @RequestBody UserIngredients userIngredients,
                                                           Authentication authentication) {
        User user = getLoggedInUser(authentication);
        Optional<UserIngredients> userIngredientsData = userIngredientsRepository.findByUserAndId(user, id);

        if (userIngredientsData.isPresent()) {
            UserIngredients _userIngredients = userIngredientsData.get();
            _userIngredients.setName(userIngredients.getName());

            return new ResponseEntity<>(userIngredientsRepository.save(_userIngredients), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUserIngredients(@PathVariable("id") int id,
                                                         Authentication authentication) {
        User user = getLoggedInUser(authentication);
        Optional<UserIngredients> userIngredients = userIngredientsRepository.findById(id);
        try {
            if (userIngredients.isPresent()) {
                if (!userIngredients.get().getUser().equals(user)) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            userIngredientsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

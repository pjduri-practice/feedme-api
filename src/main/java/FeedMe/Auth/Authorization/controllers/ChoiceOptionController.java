package FeedMe.Auth.Authorization.controllers;

import FeedMe.Auth.Authorization.data.ChoiceColumnRepository;
import FeedMe.Auth.Authorization.data.ChoiceOptionRepository;
import FeedMe.Auth.Authorization.data.ColumnLayoutRepository;
import FeedMe.Auth.Authorization.data.UserRepository;
import FeedMe.Auth.Authorization.models.ChoiceOption;
import FeedMe.Auth.Authorization.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/choice-options")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, allowCredentials = "true")
public class ChoiceOptionController {

    @Autowired
    private ChoiceOptionRepository choiceOptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChoiceColumnRepository choiceColumnRepository;

    @Autowired
    private ColumnLayoutRepository columnLayoutRepository;

    public User getLoggedInUser(Authentication authentication) {
        String username = null;
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }

        if (username != null) {
            Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
            if (user.isPresent()) return user.get();
        }

        return null;
    }

    @GetMapping
    public ResponseEntity<List<ChoiceOption>> getChoiceOptionsByUser(Authentication authentication) {
        try {
            User user = getLoggedInUser(authentication);
            if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            List<ChoiceOption> choiceOptions = choiceOptionRepository.findByUser(user);

            if (choiceOptions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(choiceOptions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ChoiceOption> getChoiceOptionById(Authentication authentication, @PathVariable("id") int id) {
        try {
            User user = getLoggedInUser(authentication);
            if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            Optional<ChoiceOption> choiceOptionData = choiceOptionRepository.findByUserAndId(user, id);

            return choiceOptionData.map(choiceOption -> new ResponseEntity<>(choiceOption, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ChoiceOption> createChoiceOption(Authentication authentication, @RequestBody ChoiceOption choiceOption) {
        try {
            User user = getLoggedInUser(authentication);
            if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            choiceOption.setUser(user);
            choiceOptionRepository.save(choiceOption);

            return new ResponseEntity<>(choiceOption, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ChoiceOption> updateChoiceOption(Authentication authentication, @RequestBody ChoiceOption choiceOption) {
        try {
            User user = getLoggedInUser(authentication);
            if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            choiceOption.setUser(user);
            choiceOptionRepository.save(choiceOption);

            return new ResponseEntity<>(choiceOption, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deleteChoiceOption(Authentication authentication, @PathVariable("id") int id) {
        try {
            User user = getLoggedInUser(authentication);
            if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            Optional<ChoiceOption> choiceOptionData = choiceOptionRepository.findById(id);

            choiceOptionData.ifPresent(choiceOption -> choiceOptionRepository.delete(choiceOption));

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

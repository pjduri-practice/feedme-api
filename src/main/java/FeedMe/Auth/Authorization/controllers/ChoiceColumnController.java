package FeedMe.Auth.Authorization.controllers;

import FeedMe.Auth.Authorization.data.ChoiceColumnRepository;
import FeedMe.Auth.Authorization.data.UserRepository;
import FeedMe.Auth.Authorization.models.ChoiceColumn;
import FeedMe.Auth.Authorization.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/choice-columns")
public class ChoiceColumnController {

    @Autowired
    private ChoiceColumnRepository choiceColumnRepository;
    @Autowired
    private UserRepository userRepository;

    public User getLoggedInUser(Authentication authentication) {
        String username = null;
        authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }

        if (username != null) {

            Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
            if (user.isPresent()) {

                User userEntity = user.get();
                return userEntity;
            }
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<List<ChoiceColumn>> getChoiceColumnsByUser(Authentication authentication) {
        try {
            User user = getLoggedInUser(authentication);
            List<ChoiceColumn> choiceColumns = choiceColumnRepository.findByUser(user);

            if (choiceColumns.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(choiceColumns, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ChoiceColumn> getChoiceColumnByUserAndId(@PathVariable("id") int id,
                                                                   Authentication authentication) {
        User user = getLoggedInUser(authentication);
        Optional<ChoiceColumn> choiceColumnData = choiceColumnRepository.findByUserAndId(user, id);

        return choiceColumnData.map(choiceColumn ->
                new ResponseEntity<>(choiceColumn, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ChoiceColumn> createChoiceColumn(@RequestBody ChoiceColumn choiceColumn,
                                                           Authentication authentication) {
        try {
            ChoiceColumn _choiceColumn = choiceColumnRepository
                    .save(new ChoiceColumn(choiceColumn.getName(),
                            choiceColumn.getItems(),
                            getLoggedInUser(authentication),
                            choiceColumn.getColumnLayout()));
            return new ResponseEntity<>(_choiceColumn, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ChoiceColumn> updateChoiceColumn(@PathVariable("id") int id,
                                                           @RequestBody ChoiceColumn choiceColumn,
                                                           Authentication authentication) {
        User user = getLoggedInUser(authentication);
        Optional<ChoiceColumn> choiceColumnData = choiceColumnRepository.findByUserAndId(user, id);

        if (choiceColumnData.isPresent()) {
            ChoiceColumn _choiceColumn = choiceColumnData.get();
            _choiceColumn.setName(choiceColumn.getName());
            _choiceColumn.setItems(choiceColumn.getItems());
            return new ResponseEntity<>(choiceColumnRepository.save(_choiceColumn), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteChoiceColumn(@PathVariable("id") int id,
                                                         Authentication authentication) {
        User user = getLoggedInUser(authentication);
        Optional<ChoiceColumn> choiceColumn = choiceColumnRepository.findById(id);
        try {
            if (choiceColumn.isPresent()) {
                if (!choiceColumn.get().getUser().equals(user)) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            choiceColumnRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


//  TODO: add roles and refactor this or move to an admin controller later

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

//    @PutMapping("/choiceColumns/{id}")
//    public ResponseEntity<ChoiceColumn> updateChoiceColumn(@PathVariable("id") int id,
//                                                           @RequestBody ChoiceColumn choiceColumn) {
//        Optional<ChoiceColumn> choiceColumnData = choiceColumnRepository.findById(id);
//
//        if (choiceColumnData.isPresent()) {
//            ChoiceColumn _choiceColumn = choiceColumnData.get();
//            _choiceColumn.setName(choiceColumn.getName());
//            _choiceColumn.setItems(choiceColumn.getItems());
//            return new ResponseEntity<>(choiceColumnRepository.save(_choiceColumn), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


// TODO: decide if we want to keep the delete all ChoiceColumns method

//    @DeleteMapping("/admin/choiceColumns")
//    public ResponseEntity<HttpStatus> deleteAllChoiceColumns() {
//        try {
//            choiceColumnRepository.deleteAll();
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
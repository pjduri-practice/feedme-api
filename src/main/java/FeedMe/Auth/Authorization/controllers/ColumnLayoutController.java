package FeedMe.Auth.Authorization.controllers;

import FeedMe.Auth.Authorization.data.ColumnLayoutRepository;
import FeedMe.Auth.Authorization.data.UserRepository;
import FeedMe.Auth.Authorization.models.ColumnLayout;
import FeedMe.Auth.Authorization.models.User;
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
@RequestMapping("/api/column-layouts")
public class ColumnLayoutController {
    @Autowired
    private ColumnLayoutRepository columnLayoutRepository;

    @Autowired
    private UserRepository userRepository;

    public User getLoggedInUser() {
        String username = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) username = authentication.getName();

        if (username != null) {
            Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
            if (user.isPresent()) return user.get();
        }

        return null;
    }

    @GetMapping
    public ResponseEntity<List<ColumnLayout>> getColumnLayoutsByUser() {
        try {
            User user = getLoggedInUser();
            List<ColumnLayout> columnLayouts = columnLayoutRepository.findByUser(user);

            if (columnLayouts.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(columnLayouts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ColumnLayout> getColumnLayoutByUserAndId(@PathVariable("id") int id) {
        User user = getLoggedInUser();
        Optional<ColumnLayout> columnLayoutData = columnLayoutRepository.findByUserAndId(user, id);

        return columnLayoutData.map(columnLayout ->
                new ResponseEntity<>(columnLayout, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ColumnLayout> createColumnLayout(@RequestBody ColumnLayout columnLayout) {
        try {
            ColumnLayout _columnLayout = columnLayoutRepository
                    .save(new ColumnLayout(columnLayout.getName(),
                            getLoggedInUser()));
            return new ResponseEntity<>(_columnLayout, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ColumnLayout> updateColumnLayout(@PathVariable("id") int id,
                                                           @RequestBody ColumnLayout columnLayout) {
        User user = getLoggedInUser();
        Optional<ColumnLayout> columnLayoutData = columnLayoutRepository.findByUserAndId(user, id);

        if (columnLayoutData.isPresent()) {
            ColumnLayout _columnLayout = columnLayoutData.get();
            _columnLayout.setName(columnLayout.getName());
            return new ResponseEntity<>(columnLayoutRepository.save(_columnLayout), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteColumnLayout(@PathVariable("id") int id) {
        User user = getLoggedInUser();
        Optional<ColumnLayout> columnLayout = columnLayoutRepository.findById(id);
        try {
            if (columnLayout.isPresent()) {
                if (!columnLayout.get().getUser().equals(user)) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            columnLayoutRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

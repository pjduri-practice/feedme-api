package FeedMe.Auth.Authorization.controllers;

import FeedMe.Auth.Authorization.data.ChoiceColumnRepository;
import FeedMe.Auth.Authorization.models.ChoiceColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ChoiceColumnController {

    @Autowired
    private ChoiceColumnRepository choiceColumnRepository;

    static HashMap<String, String> columnChoices = new HashMap<>();

    public ChoiceColumnController () {

        columnChoices.put("all", "All");

    }

    @GetMapping("/choiceColumns")
    public ResponseEntity<List<ChoiceColumn>> getAllChoiceColumns(@RequestParam(required = false) String name) {
        try {
            List<ChoiceColumn> choiceColumns = new ArrayList<ChoiceColumn>();

            if (name == null)
                choiceColumnRepository.findAll().forEach(choiceColumns::add);
            else
                choiceColumns.addAll(choiceColumnRepository.findByNameContaining(name));

            if (choiceColumns.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(choiceColumns, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/choiceColumns/{id}")
    public ResponseEntity<ChoiceColumn> getChoiceColumnById(@PathVariable("id") int id) {
        Optional<ChoiceColumn> choiceColumnData = choiceColumnRepository.findById(id);

        return choiceColumnData.map(choiceColumn ->
                new ResponseEntity<>(choiceColumn, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/choiceColumns")
    public ResponseEntity<ChoiceColumn> createChoiceColumn(@RequestBody ChoiceColumn choiceColumn) {
        try {
            ChoiceColumn _choiceColumn = choiceColumnRepository
                    .save(new ChoiceColumn(choiceColumn.getName(), choiceColumn.getItems()));
            return new ResponseEntity<>(_choiceColumn, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/choiceColumns/{id}")
    public ResponseEntity<ChoiceColumn> updateChoiceColumn(@PathVariable("id") int id, @RequestBody ChoiceColumn choiceColumn) {
        Optional<ChoiceColumn> choiceColumnData = choiceColumnRepository.findById(id);

        if (choiceColumnData.isPresent()) {
            ChoiceColumn _choiceColumn = choiceColumnData.get();
            _choiceColumn.setName(choiceColumn.getName());
            _choiceColumn.setItems(choiceColumn.getItems());
            return new ResponseEntity<>(choiceColumnRepository.save(_choiceColumn), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/choiceColumns/{id}")
    public ResponseEntity<HttpStatus> deleteChoiceColumn(@PathVariable("id") int id) {
        try {
            choiceColumnRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/choiceColumns")
    public ResponseEntity<HttpStatus> deleteAllChoiceColumns() {
        try {
            choiceColumnRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
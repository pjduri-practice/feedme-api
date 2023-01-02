package FeedMe.Auth.Authorization.controllers;

import FeedMe.Auth.Authorization.data.ChoiceColumnRepository;
import FeedMe.Auth.Authorization.models.ChoiceColumn;
import FeedMe.Auth.Authorization.models.ChoiceColumnData;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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

    @RequestMapping("")
    public String list(Model model) {
        model.addAttribute("choiceColumns", choiceColumnRepository.findAll());
        return "list";
    }

    @RequestMapping(value = "choiceColumns")
    public String listChoiceColumnsByColumnAndValue(Model model, @RequestParam String column, @RequestParam String value) {
        Iterable<ChoiceColumn> choiceColumns;
        if (column.toLowerCase().equals("all")){
            choiceColumns = choiceColumnRepository.findAll();
            model.addAttribute("title", "All Choice Columns");
        } else {
            choiceColumns = ChoiceColumnData.findByColumnAndValue(column, value, choiceColumnRepository.findAll());
            model.addAttribute("title", "ChoiceColumns with " + columnChoices.get(column) + ": " + value);
        }
        model.addAttribute("choiceColumns", choiceColumns);

        return "list-choiceColumns";
    }
}
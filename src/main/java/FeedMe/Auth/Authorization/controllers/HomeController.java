package FeedMe.Auth.Authorization.controllers;

import FeedMe.Auth.Authorization.data.ChoiceColumnRepository;
import FeedMe.Auth.Authorization.models.ChoiceColumn;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class HomeController {

    @Autowired
    private ChoiceColumnRepository choiceColumnRepository;


    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "My Jobs");
        model.addAttribute("choiceColumns", choiceColumnRepository.findAll());
        return "index";
    }

    @GetMapping("add")
    public String displayAddChoiceColumnForm(Model model) {
        model.addAttribute("title", "Add Choice Column");
        model.addAttribute(new ChoiceColumn());
        return "add";
    }

    @PostMapping("add")
    public String processAddChoiceColumnForm(@ModelAttribute @Valid ChoiceColumn newChoiceColumn,
                                             Errors errors,
                                             Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Choice Column");
            model.addAttribute(new ChoiceColumn());
            return "add";
        }

        choiceColumnRepository.save(newChoiceColumn);

        return "redirect:";
    }

}
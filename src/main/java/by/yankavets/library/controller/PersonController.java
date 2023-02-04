package by.yankavets.library.controller;


import by.yankavets.library.model.Person;
import by.yankavets.library.service.BookService;
import by.yankavets.library.service.PersonService;
import by.yankavets.library.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;
    private final BookService bookService;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonService personService, BookService bookService, PersonValidator personValidator) {
        this.personService = personService;
        this.bookService = bookService;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String people(Model model) {
        List<Person> people = personService.findAll();
        model.addAttribute("people", people);
        return "people/index";
    }


    @GetMapping("/{id}")
    public String findPersonById(@PathVariable long id, Model model) {
        Optional<Person> personOptional = personService.findPersonById(id);

        if (personOptional.isPresent()) {
            model.addAttribute("person", personOptional.get());
            model.addAttribute("books", personService.findBooksByPersonId(id));
            return "people/person";
        }

        model.addAttribute("message", "Such user no exists");
        return "people/index";
    }

    @GetMapping("/new")
    public String createPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new-person";
    }

    @PostMapping
    public String createPerson(@ModelAttribute @Valid Person person,
                               BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new-person";
        }

        personService.save(person);
        return "redirect: /people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        Optional<Person> person = personService.findPersonById(id);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
            return "people/edit";
        }

        model.addAttribute("message", "Such user no exists");
        return "redirect: /people";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") long id) {

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personService.update(id, person);
        return "redirect: /people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        personService.delete(id);
        return "redirect: /people";
    }

}

package Tracker.person;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {

    final private PersonService personService;

    @GetMapping
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/people/index");
        List<Person> people = personService.findAll();
        modelAndView.addObject("people", people);
        return modelAndView;
    }

    @GetMapping("/new")
    ModelAndView newPerson() {
        ModelAndView modelAndView = new ModelAndView("/people/new");

        Person person = new Person();
        modelAndView.addObject("person", person);

        return modelAndView;
    }

    @PostMapping("/save")
    ModelAndView save(@ModelAttribute @Valid Person person, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/people");

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("people/new");
            modelAndView.addObject("person", person);
            return modelAndView;
        }

        person.setDateCreated(LocalDateTime.now());
        personService.save(person);
        return modelAndView;
    }
}
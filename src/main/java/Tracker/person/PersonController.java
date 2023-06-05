package Tracker.person;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.Normalizer;
import java.util.regex.Pattern;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {

    final private PersonService personService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

        // Encrypt the password
        String encryptedPassword = bCryptPasswordEncoder.encode(person.getPassword());
        person.setPassword(encryptedPassword);

        // Set username (login) -> first letter of name + . + full last name (no special characters)
        String username = removeSpecialCharacters(person.getLastName().toLowerCase())
                                .substring(0, 1)
                                .toLowerCase()
                            + "."
                            + removeSpecialCharacters(person.getLastName().toLowerCase());
        person.setUsername(username);

        person.setDateCreated(LocalDateTime.now());
        
        personService.save(person);
        return modelAndView;
    }

    private static String removeSpecialCharacters(String str) {
        String normalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedString).replaceAll("")
                .replaceAll("[^a-zA-Z0-9]", "");
    }
}
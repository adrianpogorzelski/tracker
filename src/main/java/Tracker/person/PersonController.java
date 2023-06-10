package Tracker.person;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    final private MessageSource messageSource;

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
        String username = removeSpecialCharacters(person.getFirstName().toLowerCase())
                                .substring(0, 1)
                                .toLowerCase()
                            + "."
                            + removeSpecialCharacters(person.getLastName().toLowerCase());
        person.setUsername(username);

        // Set date to now and mark the account as enabled
        person.setDateCreated(LocalDateTime.now());
        person.setEnabled(true);
        
        personService.save(person);
        return modelAndView;
    }

    private static String removeSpecialCharacters(String str) {
        String normalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedString).replaceAll("")
                .replaceAll("[^a-zA-Z0-9]", "");
    }

    @GetMapping("/{id}")
    ModelAndView details(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/people/details");
        Optional<Person> personOptional = personService.findById(id);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            modelAndView.addObject("person", person);
        } else {
            String errorMessage = messageSource.getMessage("error.invalidProjectId", null, LocaleContextHolder.getLocale());
            modelAndView.addObject("errorMessage", errorMessage);
        }

        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/people/edit");
        Optional<Person> personOptional = personService.findById(id);
        
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            modelAndView.addObject("person", person);

        } else {
            String errorMessage = messageSource.getMessage("error.invalidProjectId", null, LocaleContextHolder.getLocale());
            modelAndView.addObject("errorMessage", errorMessage);
        }
        
        return modelAndView;
    }
    
    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute("project") Person updatedPerson) {
        Optional<Person> optional = personService.findById(id);

        if (optional.isPresent()) {
            Person person = optional.get();
            person.setFirstName(updatedPerson.getFirstName());
            person.setLastName(updatedPerson.getLastName());
            person.setUsername(updatedPerson.getUsername());
            if (updatedPerson.getPassword() != null) {
                String encryptedPassword = bCryptPasswordEncoder.encode(updatedPerson.getPassword());
                person.setPassword(encryptedPassword);
            
            person.setEmail(updatedPerson.getEmail());}
            personService.save(person);
        }

        return "redirect:/people";
    }

    // Delete the account and all related tasks and projects
    // The idea is to make this available only to admins to delete old unused accounts (and as a workaround to cascade deleting problem...)
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        personService.delete(id);
        return "redirect:/";
    }

    // Set the account to disabled - cannot log in, but tasks and projects are NOT deleted
    @GetMapping("/{id}/disable")
    public String disable(@PathVariable Long id, @ModelAttribute("project") Person updatedPerson) {
        Optional<Person> optional = personService.findById(id);

        if (optional.isPresent()) {
            Person person = optional.get();
            person.setEnabled(false);
            personService.save(person);
        }

        return "redirect:/"; 
    }

}
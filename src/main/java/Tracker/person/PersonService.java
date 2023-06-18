package Tracker.person;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import Tracker.authority.Authority;
import Tracker.authority.AuthorityName;
import Tracker.authority.AuthorityRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

    final private PersonRepository personRepository;
    final private BCryptPasswordEncoder bCryptPasswordEncoder;
    final private AuthorityRepository authorityRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public void save(@Valid Person person) {
        personRepository.save(person);
    }
    
    public void saveAdmin() {
        String username = "admin";
        String password = "123";

        Optional<Person> person = personRepository.findByUsername(username);

        System.out.println("\n*** Creating admin profile...");

        if (person.isPresent()) {
            System.out.println("\n*** Admin already exists, aborting");
            return;
        }

        Person admin = new Person();
        admin.setUsername(username);
        admin.setDateCreated(LocalDateTime.now());
        admin.setEmail(username);
        admin.setFirstName(username);
        admin.setLastName(username);
        admin.setManagedProjects(null);
        admin.setTasks(null);

        // Fetch all available authorities from the database
        List<Authority> allAuthorities = authorityRepository.findAll();

        Set<Authority> authorities = new HashSet<>();
        for (Authority authority : allAuthorities) {
            authorities.add(authority);
        }

        admin.setAuthorities(authorities);

        String hashedPassword = bCryptPasswordEncoder.encode(password);
        admin.setPassword(hashedPassword);

        personRepository.save(admin);

        System.out.println("Admin profile created");
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    public Optional<Person> findByUsername(String username) {
        return personRepository.findByUsername(username);
    }

}

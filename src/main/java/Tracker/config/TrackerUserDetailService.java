package Tracker.config;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Tracker.person.Person;
import Tracker.person.PersonRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TrackerUserDetailService implements UserDetailsService {
    
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

        // Only allow enabled users to log in
        if (!person.getEnabled()) {
            throw new UsernameNotFoundException(username);
        }

        return new User(username, person.getPassword(), Collections.emptyList());
    }
}

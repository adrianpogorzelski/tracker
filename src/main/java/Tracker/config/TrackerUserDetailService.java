package Tracker.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Tracker.authority.Authority;
import Tracker.person.Person;
import Tracker.person.PersonRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TrackerUserDetailService implements UserDetailsService {
    
    private PersonRepository personRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

        // Only allow enabled users to log in
        if (!person.getEnabled()) {
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> authorities = getUserAuthorities(person);
        return new User(username, person.getPassword(), authorities);
    }

    private List<GrantedAuthority> getUserAuthorities(Person person) {
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    for (Authority authority : person.getAuthorities()) {
        grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName().toString()));
    }
    return new ArrayList<>(grantedAuthorities);
    }
}

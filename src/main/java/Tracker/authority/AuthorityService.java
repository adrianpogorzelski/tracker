package Tracker.authority;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    
 public void saveAuthorities() {
    System.out.println("*** Creating authorities...");
        for (AuthorityName authorityName : AuthorityName.values()) {
            Optional<Authority> existingAuthority = authorityRepository.findByName(authorityName);

            if (!existingAuthority.isPresent()) {
                Authority authority = new Authority();
                authorityRepository.save(authority);
            }
        }
    System.out.println("*** Authorities created");
    }
}

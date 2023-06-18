package Tracker.authority;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    
 public void saveAuthorities() {
        for (AuthorityName authorityName : AuthorityName.values()) {
            if (!authorityRepository.findByName(authorityName).isPresent()) {
                Authority authority = new Authority();
                authority.setName(authorityName);
                authorityRepository.save(authority);
            }
        }
    }
}

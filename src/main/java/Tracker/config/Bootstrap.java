package Tracker.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import Tracker.authority.AuthorityService;
import Tracker.person.PersonService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class Bootstrap implements InitializingBean {

    private PersonService personService;
    private AuthorityService authorityService;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("\n*** Application starting...");

        authorityService.saveAuthorities();
        personService.saveAdmin();

        System.out.println("\n*** Application ready");

    }
}
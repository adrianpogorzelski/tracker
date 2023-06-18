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
        System.out.println("Application starting...");

        personService.saveAdmin();
        authorityService.saveAuthorities();

        System.out.println("Application ready");

    }
}
package Tracker.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import Tracker.person.PersonService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class Bootstrap implements InitializingBean {

    private PersonService personService;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Application starting...");

        personService.saveAdmin();

        System.out.println("Application ready");

    }
}
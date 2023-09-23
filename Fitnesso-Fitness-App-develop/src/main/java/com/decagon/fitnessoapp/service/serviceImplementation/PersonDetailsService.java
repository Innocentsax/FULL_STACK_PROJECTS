package com.decagon.fitnessoapp.service.serviceImplementation;

import com.decagon.fitnessoapp.model.user.Person;
import com.decagon.fitnessoapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonDetailsService implements UserDetailsService{
    private final PersonRepository personRepository;

    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByUserName(userName);
        person.orElseThrow(()-> new UsernameNotFoundException("Not Found: " + userName));
        return person.map(PersonDetails::new).get();
    }
}

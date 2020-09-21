package com.bgokce.eventmanagement.usecases.manageperson;

import com.bgokce.eventmanagement.common.ResponseMessages;
import com.bgokce.eventmanagement.model.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final AuthorityRepository authorityRepository;

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public Person getPersonById(String id){
        return personRepository.findById(id).orElse(null);
    }

    public Person findByTcNoWithEventPerson(String tcNo) {
        return personRepository.findByTcNoWithEventPerson(tcNo);
    }

    public Person findByTcNo(String tcNo){
        return personRepository.findByTcNo(tcNo);
    }

    @CacheEvict(value = "person", key = "#person.username")
    public MessageResponse createPerson(Person person) {
        Boolean isUsernameUsed = personRepository.existsByUsername(person.getUsername());
        if (isUsernameUsed) {
            return new MessageResponse(ResponseMessages.ERROR,ResponseMessages.USERNAME_ALREADY_EXISTS,null);
        }
        Authority authority = authorityRepository.findByAuthority("USER");
        person.getAuthorities().add(authority);
        Person savedPerson = personRepository.save(person);
        return new MessageResponse(ResponseMessages.SUCCESS,ResponseMessages.ACCOUNT_CREATED_SUCCESSFULLY, savedPerson);
    }

    public void createAdmin(String username, String password){
        Authority authority = authorityRepository.findByAuthority("ADMIN");
        Person person = new Person();
        person.setTcNo("46837657376");
        person.setUsername(username);
        person.setPassword(password);
        person.setEmail("admin@gmail.com");
        person.setNameSurname(username);
        person.setFirebaseToken("");
        person.setAttendings(new HashSet<>());
        person.setAuthorities(new HashSet<>());
        personRepository.save(person);
        person.getAuthorities().add(authority);
        personRepository.save(person);
    }


}

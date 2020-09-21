package com.bgokce.eventmanagement.usecases.manageperson;

import com.bgokce.eventmanagement.CommonFunctions;
import com.bgokce.eventmanagement.common.ResponseMessages;
import com.bgokce.eventmanagement.model.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    PersonRepository personRepository;
    @Mock
    AuthorityRepository authorityRepository;

    @InjectMocks
    PersonService personService;

    Person person;

    @BeforeEach
    void setUp() {
        person = CommonFunctions.getValidPerson();
    }

    @Test
    void getAllPeople() {
        when(personRepository.findAll()).thenReturn(List.of(person));

        List returnedList = personService.getAllPeople();

        assertEquals(List.of(person),returnedList);
        assertEquals(1,returnedList.size());
    }

    @Test
    void getPersonById() {
        when(personRepository.findById(any())).thenReturn(Optional.of(person));

        Person returnedPerson = personService.getPersonById("id");

        assertEquals(person.getTcNo(),returnedPerson.getTcNo());
    }

    @Test
    void findByTcNoWithEventPerson() {
        when(personRepository.findByTcNoWithEventPerson(any())).thenReturn(person);

        Person returnedPerson = personService.findByTcNoWithEventPerson("tcNo");

        assertEquals(person.getTcNo(),returnedPerson.getTcNo());
    }

    @Test
    void findByTcNo() {
        when(personRepository.findByTcNo(any())).thenReturn(person);

        Person returnedPerson = personService.findByTcNo("tcNo");

        assertEquals(person.getTcNo(),returnedPerson.getTcNo());
    }

    @Test
    void createPerson() {
        when(personRepository.existsByUsername(any())).thenReturn(false);
        when(authorityRepository.findByAuthority(any())).thenReturn(new Authority("USER"));
        when(personRepository.save(any())).thenReturn(person);

        MessageResponse response = personService.createPerson(person);

        assertEquals(ResponseMessages.SUCCESS, response.getType());
        assertEquals(ResponseMessages.ACCOUNT_CREATED_SUCCESSFULLY,response.getMessage());
        assertEquals(person.getTcNo(),((Person)response.getBody()).getTcNo());
    }

    @Test
    void createPersonAlreadyExistingUsernameTest() {
        when(personRepository.existsByUsername(any())).thenReturn(true);

        MessageResponse response = personService.createPerson(person);

        assertEquals(ResponseMessages.ERROR, response.getType());
        assertEquals(ResponseMessages.USERNAME_ALREADY_EXISTS, response.getMessage());
        assertNull(response.getBody());
    }

    @Test
    void createAdmin() {
        when(authorityRepository.findByAuthority(any())).thenReturn(new Authority("ADMIN"));
        when(personRepository.save(any())).thenReturn(person);

        personService.createAdmin("admin","admin");

        verify(personRepository, times(2)).save(any());
    }
}
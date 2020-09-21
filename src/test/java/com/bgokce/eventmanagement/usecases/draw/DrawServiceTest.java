package com.bgokce.eventmanagement.usecases.draw;

import com.bgokce.eventmanagement.CommonFunctions;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonService;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DrawServiceTest {

    @Mock
    EventPersonService eventPersonService;

    @InjectMocks
    DrawService drawService;

    Person person;

    @BeforeEach
    void setUp() {
        person = CommonFunctions.getValidPerson();
    }

    @Test
    void getAttendedPeople() {
        when(eventPersonService.getPeopleAttendedTheEvent(any())).thenReturn(Set.of(person));

        Set<Person> people = drawService.getAttendedPeople(1L);

        assertEquals(1,people.size());
        assertEquals(Set.of(person),people);
    }

    @Test
    void draw() {
        when(eventPersonService.getPeopleAttendedTheEvent(any())).thenReturn(Set.of(person));

        Person returnedPerson = drawService.draw(1L);

        assertEquals(person,returnedPerson);
    }

    @Test
    void drawEmptySetTest() {
        when(eventPersonService.getPeopleAttendedTheEvent(any())).thenReturn(new HashSet<>());

        Person returnedPerson = drawService.draw(1L);

        assertNull(returnedPerson);
    }
}
package com.bgokce.eventmanagement.usecases.manageattending;

import com.bgokce.eventmanagement.CommonFunctions;
import com.bgokce.eventmanagement.DTO.EventForAdminDTO;
import com.bgokce.eventmanagement.DTO.EventForUserDTO;
import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.usecases.manageevents.EventService;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import com.bgokce.eventmanagement.usecases.manageperson.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventPersonServiceTest {

    @Mock
    EventPersonRepository eventPersonRepository;
    @Mock
    PersonService personService;
    @Mock
    EventService eventService;
    @Mock
    EventPersonService eventPersonServiceMock;

    @InjectMocks
    EventPersonService eventPersonService;

    EventPerson eventPerson;
    Event event;
    Person person;

    @BeforeEach
    void setUp() {
        event = CommonFunctions.getValidEvent();
        eventPerson = CommonFunctions.getValidEventPerson();
        person = CommonFunctions.getValidPerson();
    }

    @Test
    void getAllEventsForAdminTest() {
        when(eventPersonRepository.getAttendingForAdmin()).thenReturn(Set.of(eventPerson));
        when(eventService.getAllEventsWithPeople()).thenReturn(Set.of(event));

        List<EventForAdminDTO> events = eventPersonService.getAllEventsForAdmin();

        assertEquals(1,events.size());
        assertEquals(event.getId(),events.get(0).getId());
    }

    @Test
    void getAllEventsForAdminNotFoundTest() {
        when(eventPersonRepository.getAttendingForAdmin()).thenReturn(null);
        when(eventService.getAllEventsWithPeople()).thenReturn(Set.of(event));

        List<EventForAdminDTO> events = eventPersonService.getAllEventsForAdmin();

        assertEquals(0,events.size());
    }

    @Test
    void getAllCurrentEventsForUserTest() {
        when(personService.findByTcNo(anyString())).thenReturn(person);
        when(eventPersonRepository.getAttendingForPerson(anyString())).thenReturn(Set.of(eventPerson));
        when(eventService.getAllEventsWithPeople()).thenReturn(Set.of(event));

        List<EventForUserDTO> events = eventPersonService.getAllCurrentEventsForUser("tcNo");

        assertEquals(1,events.size());
        assertEquals(event.getId(),events.get(0).getId());
    }

    @Test
    void getAllCurrentEventsForUserNotFoundTest() {
        when(personService.findByTcNo(anyString())).thenReturn(null);
        when(eventPersonRepository.getAttendingForPerson(anyString())).thenReturn(Set.of(eventPerson));
        when(eventService.getAllEventsWithPeople()).thenReturn(Set.of(event));

        List<EventForUserDTO> events = eventPersonService.getAllCurrentEventsForUser("tcNo");

        assertEquals(0,events.size());
    }


    @Test
    void setCompletedSurvey() {
        when(eventPersonRepository.getAttending(1L,"tc")).thenReturn(eventPerson);

        eventPersonService.setCompletedSurvey(1L,"tc");

        verify(eventPersonRepository).save(any());
    }

    @Test
    void createEventPerson() {
        when(eventPersonRepository.save(any())).thenReturn(eventPerson);

        EventPerson returnedEventPerson = eventPersonService.createEventPerson(eventPerson);

        assertEquals(eventPerson, returnedEventPerson);
    }

    @Test
    void getPeopleAttendedTheEvent() {
        when(eventPersonRepository.getPeopleAttendedTheEvent(1L)).thenReturn(Set.of(person));

        Set<Person> people = eventPersonService.getPeopleAttendedTheEvent(1L);

        assertEquals(1, people.size());
        assertEquals(Set.of(person), people);
    }
}
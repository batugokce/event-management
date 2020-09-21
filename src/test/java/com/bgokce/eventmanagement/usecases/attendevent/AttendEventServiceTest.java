package com.bgokce.eventmanagement.usecases.attendevent;

import com.bgokce.eventmanagement.CommonFunctions;
import com.bgokce.eventmanagement.common.ResponseMessages;
import com.bgokce.eventmanagement.model.MessageResponse;
import com.bgokce.eventmanagement.usecases.manageattending.Answer;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonService;
import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.usecases.manageevents.EventService;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import com.bgokce.eventmanagement.usecases.manageperson.PersonService;
import com.bgokce.eventmanagement.usecases.managewebsocket.WebSocketService;
import com.bgokce.eventmanagement.utilities.EmailSender;
import com.bgokce.eventmanagement.utilities.Utilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AttendEventServiceTest {

    @Mock
    WebSocketService webSocketService;
    @Mock
    EmailSender emailSender;
    @Mock
    EventPersonService eventPersonService;
    @Mock
    AnswerService answerService;
    @Mock
    EventService eventService;
    @Mock
    PersonService personService;
    @Mock(lenient = true)
    AttendEventService attendEventServiceMock;

    @InjectMocks
    AttendEventService attendEventService;

    EventPerson eventPerson;
    Person person;
    Event event;
    Answer answer;

    @BeforeEach
    void setUp() {
        event = CommonFunctions.getValidEvent();
        person = CommonFunctions.getValidPerson();
        eventPerson = CommonFunctions.getValidEventPerson();
    }

    @Test
    void attendEventSuccessTest() throws Exception {
        when(personService.findByTcNoWithEventPerson(anyString())).thenReturn(person);
        when(eventService.getEventByIdWithPeople(any())).thenReturn(event);
        when(attendEventServiceMock.isPersonAttended(any(),any())).thenReturn(false);
        when(eventPersonService.createEventPerson(any())).thenReturn(eventPerson);

        MessageResponse response = attendEventService.attendEvent(1L,"tcNo",new ArrayList<>());

        assertEquals(ResponseMessages.SUCCESS, response.getType());
        assertNotNull(response.getBody());
        verify(webSocketService).sendParticipantDetails(any(),any());
        verify(eventPersonService).createEventPerson(any());
    }

    @Test
    void attendEventNullTest() throws Exception {
        when(personService.findByTcNoWithEventPerson(anyString())).thenReturn(null);
        when(eventService.getEventByIdWithPeople(any())).thenReturn(event);

        MessageResponse response = attendEventService.attendEvent(1L,"tcNo",new ArrayList<>());

        assertEquals(ResponseMessages.ERROR, response.getType());
        assertEquals(ResponseMessages.EVENT_OR_PERSON_NOT_FOUND,response.getMessage());
        assertNull(response.getBody());
    }

    @Test
    void attendEventWarnTest() throws Exception {
        eventPerson.setEvent(event);
        person.setAttendings(Set.of(eventPerson));
        when(personService.findByTcNoWithEventPerson(anyString())).thenReturn(person);
        when(eventService.getEventByIdWithPeople(any())).thenReturn(event);

        MessageResponse response = attendEventService.attendEvent(1L,"tcNo",new ArrayList<>());

        assertEquals(ResponseMessages.WARNING, response.getType());
        assertEquals(ResponseMessages.PERSON_ALREADY_ATTENDED_EVENT,response.getMessage());
        assertNotNull(response.getBody());
    }

    @Test
    void attendEventFullEventTest() throws Exception {
        event.setIsFull(true);
        when(personService.findByTcNoWithEventPerson(anyString())).thenReturn(person);
        when(eventService.getEventByIdWithPeople(any())).thenReturn(event);

        MessageResponse response = attendEventService.attendEvent(1L,"tcNo",new ArrayList<>());

        assertEquals(ResponseMessages.ERROR, response.getType());
        assertEquals(ResponseMessages.CAPACITY_IS_FULL,response.getMessage());
        assertNull(response.getBody());
    }

    @Test
    void attendEventWithQuestionsTest() throws Exception {
        event.setHasQuestion(true);
        when(personService.findByTcNoWithEventPerson(anyString())).thenReturn(person);
        when(eventService.getEventByIdWithPeople(any())).thenReturn(event);
        when(answerService.createAnswer(any())).thenReturn(new Answer());

        MessageResponse response = attendEventService.attendEvent(1L,"tcNo",List.of("a1","a2","a3"));

        assertEquals(ResponseMessages.SUCCESS, response.getType());
        assertEquals(ResponseMessages.ATTEND_IS_SUCCESSFUL,response.getMessage());
        assertNotNull(response.getBody());
    }

    @Test
    void getAttendedEvents() {
        eventPerson.setEvent(event);
        Set<EventPerson> eventPersonSet = Set.of(eventPerson);

        List<Event> events = attendEventService.getAttendedEvents(eventPersonSet);

        assertEquals(1,events.size());
        assertEquals(event, events.get(0));
    }
}
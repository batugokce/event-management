package com.bgokce.eventmanagement.usecases.manageevents;

import com.bgokce.eventmanagement.CommonFunctions;
import com.bgokce.eventmanagement.common.ResponseMessages;
import com.bgokce.eventmanagement.model.MessageResponse;
import com.bgokce.eventmanagement.model.Question;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.bgokce.eventmanagement.usecases.manageeventquestions.QuestionService;
import com.bgokce.eventmanagement.usecases.sendNotification.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    EventRepository eventRepository;
    @Mock
    QuestionService questionService;
    @Mock
    NotificationService notificationService;

    @InjectMocks
    EventService eventService;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;


    Event validEvent;
    EventPerson eventPerson;

    @BeforeEach
    void setUp(){
        validEvent = CommonFunctions.getValidEvent();
        eventPerson = CommonFunctions.getValidEventPerson();
    }

    @Test
    void getAllEventsWithPeopleTest(){
        Set<Event> eventSet = Set.of(validEvent);
        when(eventRepository.getAllEventsWithPeople()).thenReturn(eventSet);

        Set<Event> foundSet = eventService.getAllEventsWithPeople();

        assertEquals(eventSet, foundSet);
        assertEquals(1,foundSet.size());
        verify(eventRepository).getAllEventsWithPeople();
    }

    @Test
    void getEventByIdTest() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(validEvent));

        Event foundEvent = eventService.getEventById(1L);

        assertNotNull(foundEvent);
        verify(eventRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).findById(anyLong());
    }

    @Test
    void getEventByIdWithPeopleTest(){
        when(eventRepository.getEventByIdWithPeople(longArgumentCaptor.capture())).thenReturn(validEvent);

        Event returnedEvent = eventService.getEventByIdWithPeople(1L);

        assertEquals(1L, longArgumentCaptor.getValue());
        assertEquals(validEvent.getEventName(), returnedEvent.getEventName());
    }

    @Test
    void getEventByIdWithPeopleWithLambdaTest(){
        Event event = new Event();
        event.setEventName("isim");

        Event savedEvent = new Event();
        savedEvent.setId(1L);

        when(eventRepository.getEventByIdWithPeople(argThat(argument -> argument.equals(1L)))).thenReturn(savedEvent);

        Event returnedEvent = eventService.getEventByIdWithPeople(1L);

        assertNotNull(returnedEvent);
    }

    @Test
    void createEventWithQuestionsEmptyQuestionsTest() {
        List list = List.of("","","");
        Question question = new Question(list);
        when(questionService.prepareQuestionFromList(list)).thenReturn(question);
        when(eventRepository.save(any())).thenReturn(validEvent);
        when(notificationService.setNotification(any(),any())).thenReturn(true);

        Event returnedEvent = eventService.createEventWithQuestions(validEvent,list);

        assertEquals(returnedEvent.getEventName(),validEvent.getEventName());
    }

    @Test
    void createEventWithQuestionsTest() {
        List list = List.of("q1","q2","q3");
        Question question = new Question(list);
        when(questionService.prepareQuestionFromList(list)).thenReturn(question);
        when(eventRepository.save(any())).thenReturn(validEvent);
        when(notificationService.setNotification(any(),any())).thenReturn(true);

        Event returnedEvent = eventService.createEventWithQuestions(validEvent,list);

        assertEquals(returnedEvent.getEventName(),validEvent.getEventName());
    }

    @Test
    void updateEventTest() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(validEvent));
        when(eventRepository.save(any())).thenReturn(validEvent);

        MessageResponse response = eventService.updateEvent(validEvent,1L);

        assertEquals(ResponseMessages.SUCCESS, response.getType());
    }

    @Test
    void updateEventNotFoundTest() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        MessageResponse response = eventService.updateEvent(validEvent,1L);

        assertEquals(ResponseMessages.ERROR, response.getType());
        assertEquals(ResponseMessages.EVENT_NOT_FOUND,response.getMessage());
    }

    @Test
    void updateEventLowerCapacityTest() {
        Event eventToChange = CommonFunctions.getValidEvent();
        eventToChange.setCapacity(120);
        eventToChange.setPersonCount(110);
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(eventToChange));

        MessageResponse response = eventService.updateEvent(validEvent,1L);

        assertEquals(ResponseMessages.ERROR, response.getType());
        assertEquals(ResponseMessages.CAPACITY_CANNOT_BE_LOWER_THAN_PARTICIPANTS,response.getMessage());
    }

    @Test
    void deleteEventTest() {
        when(eventRepository.getEventByIdWithPeopleAndSurveyResults(anyLong())).thenReturn(validEvent);
        MessageResponse response = eventService.deleteEvent(102L);

        verify(eventRepository, times(1)).getEventByIdWithPeopleAndSurveyResults(102L);
        assertEquals(ResponseMessages.SUCCESS,response.getType());
    }

    @Test
    void deleteEventNotFoundTest() {
        when(eventRepository.getEventByIdWithPeopleAndSurveyResults(anyLong())).thenReturn(null);
        MessageResponse response = eventService.deleteEvent(102L);

        assertEquals(ResponseMessages.ERROR,response.getType());
    }

    @Test
    void takePersonToEventSuccessTest() {
        when(eventRepository.save(any())).thenReturn(validEvent);
        eventService.takePersonToEvent(eventPerson, validEvent);
    }

    @Test
    void takePersonToEventFullTest() {
        validEvent.setPersonCount(validEvent.getCapacity()-1);
        when(eventRepository.save(any())).thenReturn(validEvent);
        eventService.takePersonToEvent(eventPerson, validEvent);
    }


}
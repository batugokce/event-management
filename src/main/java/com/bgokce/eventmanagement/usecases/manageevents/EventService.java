package com.bgokce.eventmanagement.usecases.manageevents;

import com.bgokce.eventmanagement.DTO.EventForAdminDTO;
import com.bgokce.eventmanagement.DTO.EventForUserDTO;
import com.bgokce.eventmanagement.common.ResponseMessages;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonRepository;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonService;
import com.bgokce.eventmanagement.usecases.manageperson.AuthorityRepository;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import com.bgokce.eventmanagement.usecases.manageperson.PersonService;
import com.bgokce.eventmanagement.usecases.managepersonquestions.PersonQuestion;
import com.bgokce.eventmanagement.mapper.EventMapper;
import com.bgokce.eventmanagement.model.*;
import com.bgokce.eventmanagement.usecases.manageeventquestions.QuestionService;
import com.bgokce.eventmanagement.usecases.sendNotification.NotificationService;
import com.bgokce.eventmanagement.utilities.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final QuestionService questionService;
    private final NotificationService notificationService;

    public Set<Event> getAllEventsWithPeople(){
        return eventRepository.getAllEventsWithPeople();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public Event getEventByIdWithPeople(Long id) {
        return eventRepository.getEventByIdWithPeople(id);
    }

    public Event createEventWithQuestions(Event event, List<String> list) {
        Question question = questionService.prepareQuestionFromList(list);
        Event eventToReturn;

        if (question.getCounter() == 0){
            event.setQuestion(null);
            event.setHasQuestion(false);
        }
        else {
            questionService.createQuestion(question);
            event.setQuestion(question);
            event.setHasQuestion(true);
        }
        eventToReturn = eventRepository.save(event);

        notificationService.setNotification(eventToReturn.getStartDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),event.getId());

        return eventToReturn;
    }

    public MessageResponse updateEvent(Event event, long id) {
        Event eventToChange = eventRepository.findById(id).orElse(null);
        if (eventToChange != null) {
            if (event.getCapacity() < eventToChange.getPersonCount()){
                return new MessageResponse(ResponseMessages.ERROR,ResponseMessages.CAPACITY_CANNOT_BE_LOWER_THAN_PARTICIPANTS,null);
            }
            eventToChange.setEventName(event.getEventName());
            eventToChange.setCity(event.getCity());
            eventToChange.setCapacity(event.getCapacity());
            eventToChange.setStartDate(event.getStartDate());
            eventToChange.setEndDate(event.getEndDate());
            eventRepository.save(eventToChange);
            return new MessageResponse(ResponseMessages.SUCCESS,ResponseMessages.EVENT_UPDATED_SUCCESSFULLY,null);
        }
        return new MessageResponse(ResponseMessages.ERROR,ResponseMessages.EVENT_NOT_FOUND,null);
    }

    public MessageResponse deleteEvent(long id) {
        Event event = eventRepository.getEventByIdWithPeopleAndSurveyResults(id);
        if (event != null) {
            eventRepository.delete(event);
            return new MessageResponse(ResponseMessages.SUCCESS,ResponseMessages.EVENT_DELETED_SUCCESSFULLY, null);
        }
        return new MessageResponse(ResponseMessages.ERROR,ResponseMessages.EVENT_NOT_FOUND, null);
    }

    public void takePersonToEvent(EventPerson eventPerson, Event event) {
        event.getAttendedPeople().add(eventPerson);
        event.setPersonCount(event.getPersonCount() + 1);
        if (event.getPersonCount() == event.getCapacity()){
            event.setIsFull(true);
        }
        eventRepository.save(event);
    }

}

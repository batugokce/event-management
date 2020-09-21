package com.bgokce.eventmanagement;

import com.bgokce.eventmanagement.DTO.EventDTO;
import com.bgokce.eventmanagement.usecases.handlesurveyresults.SurveyResults;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import com.bgokce.eventmanagement.usecases.managepersonquestions.PersonQuestion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CommonFunctions {

    public static Event getValidEvent() {
        Event event = new Event();
        event.setId(1L);
        event.setEventName("Etkinlik ismi");
        event.setCapacity(100);
        event.setStartDate(LocalDateTime.now());
        event.setPersonCount(0);
        event.setAttendedPeople(new HashSet<>());
        event.setIsFull(false);
        event.setHasQuestion(false);
        event.setEndDate(LocalDateTime.now().plusDays(1));
        return event;
    }

    public static EventPerson getValidEventPerson() {
        EventPerson eventPerson = new EventPerson();
        eventPerson.setTcNo("11111111110");
        eventPerson.setEventId(1L);
        eventPerson.setUsersQuestions(new HashSet<>());
        eventPerson.setIsCompletedSurvey(false);
        return eventPerson;
    }

    public static Person getValidPerson(){
        Person person = new Person();
        person.setNameSurname("batu gokce");
        person.setId(1L);
        person.setTcNo("11111111110");
        person.setAuthorities(new HashSet<>());
        person.setAttendings(new HashSet<>());
        return person;
    }

    public static PersonQuestion getValidPersonQuestion() {
        PersonQuestion personQuestion = new PersonQuestion();
        personQuestion.setQuestion("this is question");
        personQuestion.setAnswer("this is answer");
        personQuestion.setOwnerAttending(null);
        return personQuestion;
    }

    public static SurveyResults getValidSurveyResults() {
        SurveyResults surveyResults = new SurveyResults(1,2,3,4,5,6,7,null);
        return surveyResults;
    }

    public static EventDTO getValidEventDTO() {
        return new EventDTO("asdas","sdad",100, LocalDateTime.now(), LocalDateTime.now(),new ArrayList<>(),"","" );
    }

    public static EventDTO getInvalidEventDTO() {
        return new EventDTO("","",100, null, LocalDateTime.now(),new ArrayList<>(),"","" );
    }

    public static ObjectWriter getObjectWriter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper.writer().withDefaultPrettyPrinter();
    }

    public static HttpEntity getRequestEntityWithoutBody(String jwt){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer " + jwt);
        HttpEntity requestEntity = new HttpEntity<>(headers);
        return requestEntity;
    }
}

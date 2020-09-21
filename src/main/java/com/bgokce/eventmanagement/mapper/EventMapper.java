package com.bgokce.eventmanagement.mapper;

import com.bgokce.eventmanagement.DTO.EventDTO;
import com.bgokce.eventmanagement.DTO.EventForAdminDTO;
import com.bgokce.eventmanagement.DTO.EventForUserDTO;
import com.bgokce.eventmanagement.DTO.PersonDTO;
import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import com.bgokce.eventmanagement.usecases.managepersonquestions.PersonQuestion;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private static EventPersonRepository eventPersonService;
    //private final EventPersonService eventPersonService;

    public static Event mapToEntity(EventDTO eventDTO){
        Event event = new Event();
        event.setEventName(eventDTO.getEventName());
        event.setPersonCount(0);
        event.setAttendedPeople(new HashSet<>());
        event.setCapacity(eventDTO.getCapacity());
        event.setCity(eventDTO.getCity());
        event.setStartDate(eventDTO.getStartDate());
        event.setEndDate(eventDTO.getEndDate());
        event.setLatitude(eventDTO.getLatitude());
        event.setLongitude(eventDTO.getLongitude());
        return event;
    }

    public static EventForUserDTO mapToEventForUser(Event event, Person person, Map<Long, List> map, Set<EventPerson> attending){
        Set<PersonQuestion> personQuestions = (Set<PersonQuestion>) map.get(event.getId()).get(0);
        Boolean isCompletedSurvey = (Boolean) map.get(event.getId()).get(1);
        return new EventForUserDTO(
                event.getId(),event.getEventName(),event.getCity(),event.getCapacity(),event.getPersonCount(),
                event.getStartDate(),event.getEndDate(),event.getQuestion(),event.getLatitude(),event.getLongitude(),
                attending.stream().anyMatch(item -> item.equals(new EventPerson(event.getId(),person.getTcNo())))
                , isCompletedSurvey , personQuestions
        );
    }

    public static EventForAdminDTO mapToEventForAdmin(Event event, Map<Long,Set<PersonQuestion>> map) {
        Set<PersonDTO> participants = event.getAttendedPeople()
                                    .stream()
                                    .map(item -> PersonMapper.mapToDTO(item.getPerson()))
                                    .collect(Collectors.toSet());
        return new EventForAdminDTO(
                event.getId(),event.getEventName(),event.getCity(),event.getCapacity(),event.getPersonCount(),
                event.getStartDate(),event.getEndDate(),event.getLatitude(),event.getLongitude(),participants,
                map.get(event.getId())
        );
    }
}

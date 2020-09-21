package com.bgokce.eventmanagement.usecases.manageattending;

import com.bgokce.eventmanagement.DTO.EventForAdminDTO;
import com.bgokce.eventmanagement.DTO.EventForUserDTO;
import com.bgokce.eventmanagement.mapper.EventMapper;
import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.usecases.manageevents.EventService;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import com.bgokce.eventmanagement.usecases.manageperson.PersonService;
import com.bgokce.eventmanagement.usecases.managepersonquestions.PersonQuestion;
import com.bgokce.eventmanagement.utilities.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventPersonService {

    private final EventPersonRepository eventPersonRepository;
    private final PersonService personService;
    private final EventService eventService;

    public List<EventForAdminDTO> getAllEventsForAdmin() {
        Set<EventPerson> allParticipation = eventPersonRepository.getAttendingForAdmin();
        Set<Event> allEvents = eventService.getAllEventsWithPeople();
        if (allParticipation == null || allEvents == null) {
            return new ArrayList<>();
        }
        Map<Long, Set<PersonQuestion>> map = Utilities.getQuestionMapping(allParticipation, allEvents);

        List list =  allEvents.stream()
                .map(event -> EventMapper.mapToEventForAdmin(event,map))
                .collect(Collectors.toList());
        return list;
    }

    public List<EventForUserDTO> getAllCurrentEventsForUser(String tcNo) {
        Person person = personService.findByTcNo(tcNo);
        Set<EventPerson> attendingOfPerson = getAttendingForPerson(tcNo);
        Set<Event> allEvents = eventService.getAllEventsWithPeople();
        if (person == null || attendingOfPerson == null || allEvents == null) {
            return new ArrayList<>();
        }

        Map<Long, List > map = Utilities.getQuestionAndSurveyCompletionMapping(attendingOfPerson, allEvents);

        return allEvents.stream()
                .filter(item -> item.getEndDate().isAfter(LocalDateTime.now()))
                .map(event -> EventMapper.mapToEventForUser(event, person, map,attendingOfPerson))
                .collect(Collectors.toList());
    }

    public Set<EventPerson> getAttendingForPerson(String tcNo) {
        return eventPersonRepository.getAttendingForPerson(tcNo);
    }

    public EventPerson getAttending(Long eventId, String tcNo){
        return eventPersonRepository.getAttending(eventId, tcNo);
    }

    public void setCompletedSurvey(Long eventId, String tcNo){
        EventPerson eventPerson = eventPersonRepository.getAttending(eventId,tcNo);
        eventPerson.setIsCompletedSurvey(true);
        eventPersonRepository.save(eventPerson);
    }

    public EventPerson createEventPerson(EventPerson eventPerson){
        return eventPersonRepository.save(eventPerson);
    }

    public Set<Person> getPeopleAttendedTheEvent(Long id) {
        return eventPersonRepository.getPeopleAttendedTheEvent(id);
    }
}

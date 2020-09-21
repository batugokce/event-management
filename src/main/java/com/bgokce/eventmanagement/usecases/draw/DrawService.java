package com.bgokce.eventmanagement.usecases.draw;

import com.bgokce.eventmanagement.mapper.PersonMapper;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonService;
import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.bgokce.eventmanagement.usecases.manageevents.EventService;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import com.bgokce.eventmanagement.usecases.manageperson.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrawService {

    private final EventPersonService eventPersonService;

    public Set<Person> getAttendedPeople(Long id){
        Set<Person> set = eventPersonService.getPeopleAttendedTheEvent(id);
        return set;
    }

    public Person draw(long id) {
        Set<Person> people = getAttendedPeople(id);
        int nOfPeople = people.size();
        if (nOfPeople == 0) {
            return null;
        }
        int index = (int) (Math.random() * nOfPeople);
        return (Person) people.toArray()[index];
    }
}

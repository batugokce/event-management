package com.bgokce.eventmanagement.usecases.attendevent;

import com.bgokce.eventmanagement.common.ResponseMessages;
import com.bgokce.eventmanagement.model.MessageResponse;
import com.bgokce.eventmanagement.usecases.manageattending.Answer;
import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonService;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import com.bgokce.eventmanagement.usecases.manageevents.EventService;
import com.bgokce.eventmanagement.usecases.manageperson.PersonService;
import com.bgokce.eventmanagement.usecases.managewebsocket.WebSocketService;
import com.bgokce.eventmanagement.utilities.EmailSender;
import com.bgokce.eventmanagement.utilities.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AttendEventService {

    private final WebSocketService webSocketService;
    private final EmailSender emailSender;
    private final EventPersonService eventPersonService;
    private final AnswerService answerService;
    private final EventService eventService;
    private final PersonService personService;

    public MessageResponse attendEvent(Long id, String tc, List<String> list) throws Exception {
        Person person = personService.findByTcNoWithEventPerson(tc); //2
        Event event = eventService.getEventByIdWithPeople(id); //1
        Answer answer = null;

        if (event == null || person == null){
            return new MessageResponse(ResponseMessages.ERROR, ResponseMessages.EVENT_OR_PERSON_NOT_FOUND,null);
        }
        else if (isPersonAttended(event,person.getAttendings())){
            return new MessageResponse(ResponseMessages.WARNING,ResponseMessages.PERSON_ALREADY_ATTENDED_EVENT,
                                        Utilities.getAttendingQRCode(event,person));
        }
        else if (event.getIsFull()){
            return new MessageResponse(ResponseMessages.ERROR,ResponseMessages.CAPACITY_IS_FULL,null);
        }

        if (event.getHasQuestion()){
            answer = answerService.createAnswer(list);
        }

        EventPerson eventPerson = Utilities.prepareEventPerson(event,person,answer);
        EventPerson eventPersonReturn = eventPersonService.createEventPerson(eventPerson);

        eventService.takePersonToEvent(eventPersonReturn, event);
        person.getAttendings().add(eventPersonReturn);

        String qrCode = Utilities.getAttendingQRCode(event,person);

        //emailSender.sendMail(person.getEmail());
        //TODO; enter e-mail credentials and comment out above line.

        webSocketService.sendParticipantDetails(person,event);

        return new MessageResponse(ResponseMessages.SUCCESS,ResponseMessages.ATTEND_IS_SUCCESSFUL, qrCode);
    }

    public Boolean isPersonAttended(Event event, Set<EventPerson> eventPersonList){
        List<Event> attendedEvents = getAttendedEvents(eventPersonList);
        return attendedEvents.contains(event);
    }

    public List<Event> getAttendedEvents(Set<EventPerson> eventPersonList) {
        List<Event> result = eventPersonList.stream()
                .map(EventPerson::getEvent)
                .collect(Collectors.toList());
        return result;
    }

}

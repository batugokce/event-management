package com.bgokce.eventmanagement.usecases.managewebsocket;

import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessageSendingOperations messagingTemplate;

    public void sendParticipantDetails(Person person, Event event){
        String text = person.getTcNo() + " TC kimlik numaralı " + person.getNameSurname() +
                    ", " + event.getEventName() + " adlı etkinliğe kaydolmuştur.";
        messagingTemplate.convertAndSend("/queue/reply",text);
    }
}

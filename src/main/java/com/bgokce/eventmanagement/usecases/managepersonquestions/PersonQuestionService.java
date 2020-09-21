package com.bgokce.eventmanagement.usecases.managepersonquestions;

import com.bgokce.eventmanagement.common.ResponseMessages;
import com.bgokce.eventmanagement.model.MessageResponse;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonRepository;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonService;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class PersonQuestionService {

    private final EventPersonService eventPersonService;
    private final PersonQuestionRepository personQuestionRepository;

    public PersonQuestion getByIdWithPerson(Long questionId){
        return personQuestionRepository.getByIdWithPerson(questionId);
    }

    public MessageResponse askQuestion(Long eventId, String tcNo, PersonQuestion personQuestion) {
        EventPerson eventPerson = eventPersonService.getAttending(eventId, tcNo);

        if (eventPerson == null) {
            return new MessageResponse(ResponseMessages.ERROR,ResponseMessages.RECORD_NOT_FOUND,null);
        }
        personQuestion.setOwnerAttending(eventPerson);
        personQuestionRepository.save(personQuestion);
        return new MessageResponse(ResponseMessages.SUCCESS,ResponseMessages.QUESTION_ADDED_SUCCESSFULLY,null);
    }

    public MessageResponse getQuestions(Long eventId, String tcNo) {
        EventPerson eventPerson = eventPersonService.getAttending(eventId, tcNo);
        if (eventPerson == null) {
            return new MessageResponse(ResponseMessages.ERROR,ResponseMessages.RECORD_NOT_FOUND,null);
        }
        return new MessageResponse(ResponseMessages.SUCCESS,null,eventPerson.getUsersQuestions());
    }


    public MessageResponse answerQuestion(Long questionId, PersonQuestion personQuestionWithAnswer) {
        PersonQuestion personQuestion = getByIdWithPerson(questionId);
        if (personQuestion == null){
            return new MessageResponse(ResponseMessages.ERROR,ResponseMessages.RECORD_NOT_FOUND,null);
        }
        personQuestion.setAnswer(personQuestionWithAnswer.getAnswer());
        personQuestionRepository.save(personQuestion);
        return new MessageResponse(ResponseMessages.SUCCESS,ResponseMessages.ANSWER_SAVED_SUCCESSFULLY,null);
    }
}

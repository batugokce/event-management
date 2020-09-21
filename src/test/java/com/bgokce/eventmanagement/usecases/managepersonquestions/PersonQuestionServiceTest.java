package com.bgokce.eventmanagement.usecases.managepersonquestions;

import com.bgokce.eventmanagement.CommonFunctions;
import com.bgokce.eventmanagement.common.ResponseMessages;
import com.bgokce.eventmanagement.model.MessageResponse;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonQuestionServiceTest {

    @Mock
    EventPersonService eventPersonService;
    @Mock
    PersonQuestionRepository personQuestionRepository;

    @InjectMocks
    PersonQuestionService personQuestionService;

    PersonQuestion personQuestion;
    EventPerson eventPerson;

    @BeforeEach
    void setUp() {
        personQuestion = CommonFunctions.getValidPersonQuestion();
        eventPerson = CommonFunctions.getValidEventPerson();
    }

    @Test
    void getByIdWithPerson() {
        when(personQuestionRepository.getByIdWithPerson(any())).thenReturn(personQuestion);

        PersonQuestion returnedQuestion = personQuestionService.getByIdWithPerson(1L);

        assertEquals(personQuestion.getQuestion(),returnedQuestion.getQuestion());
        verify(personQuestionRepository).getByIdWithPerson(any());
    }

    @Test
    void askQuestion() {
        when(eventPersonService.getAttending(anyLong(),anyString())).thenReturn(eventPerson);
        when(personQuestionRepository.save(any(PersonQuestion.class))).thenReturn(personQuestion);

        MessageResponse response = personQuestionService.askQuestion(1L,"tc",personQuestion);

        assertEquals(ResponseMessages.SUCCESS, response.getType());
    }

    @Test
    void askQuestionNotFoundTest() {
        when(eventPersonService.getAttending(anyLong(),anyString())).thenReturn(null);

        MessageResponse response = personQuestionService.askQuestion(1L,"tc",personQuestion);

        assertEquals(ResponseMessages.ERROR, response.getType());
        assertEquals(ResponseMessages.RECORD_NOT_FOUND, response.getMessage());
    }

    @Test
    void getQuestions() {
        when(eventPersonService.getAttending(anyLong(),anyString())).thenReturn(eventPerson);

        MessageResponse response = personQuestionService.getQuestions(1L,"tc");

        assertEquals(ResponseMessages.SUCCESS, response.getType());
    }

    @Test
    void getQuestionsNullTest() {
        when(eventPersonService.getAttending(anyLong(),anyString())).thenReturn(null);

        MessageResponse response = personQuestionService.getQuestions(1L,"tc");

        assertEquals(ResponseMessages.ERROR, response.getType());
        assertEquals(ResponseMessages.RECORD_NOT_FOUND, response.getMessage());
    }

    @Test
    void answerQuestion() {
        when(personQuestionService.getByIdWithPerson(any())).thenReturn(personQuestion);

        MessageResponse response = personQuestionService.answerQuestion(1L,personQuestion);

        assertEquals(ResponseMessages.SUCCESS, response.getType());
    }

    @Test
    void answerQuestionNullTest() {
        when(personQuestionService.getByIdWithPerson(any())).thenReturn(null);

        MessageResponse response = personQuestionService.answerQuestion(1L,personQuestion);

        assertEquals(ResponseMessages.ERROR, response.getType());
        assertEquals(ResponseMessages.RECORD_NOT_FOUND, response.getMessage());
    }
}
package com.bgokce.eventmanagement.usecases.handlesurveyresults;

import com.bgokce.eventmanagement.CommonFunctions;
import com.bgokce.eventmanagement.common.ResponseMessages;
import com.bgokce.eventmanagement.model.MessageResponse;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonService;
import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.usecases.manageevents.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SurveyResultsServiceTest {

    @Mock
    SurveyResultsRepository surveyResultsRepository;
    @Mock
    EventService eventService;
    @Mock
    EventPersonService eventPersonService;

    @InjectMocks
    SurveyResultsService surveyResultsService;

    Event event;
    SurveyResults surveyResults;

    @BeforeEach
    void setUp() {
        event = CommonFunctions.getValidEvent();
        surveyResults = CommonFunctions.getValidSurveyResults();
    }

    @Test
    void completeSurveySuccessTest() {
        when(eventService.getEventById(anyLong())).thenReturn(event);

        MessageResponse response = surveyResultsService.completeSurvey(1L,"tcNo",surveyResults);

        assertEquals(ResponseMessages.SUCCESS, response.getType());
        verify(surveyResultsRepository).save(surveyResults);
        verify(eventPersonService).setCompletedSurvey(anyLong(),anyString());
    }

    @Test
    void completeSurveyNullTest() {
        when(eventService.getEventById(anyLong())).thenReturn(null);

        MessageResponse response = surveyResultsService.completeSurvey(1L,"tcNo",surveyResults);

        assertEquals(ResponseMessages.ERROR, response.getType());
        assertEquals(ResponseMessages.EVENT_NOT_FOUND, response.getMessage());
    }

    @Test
    void getSurveyResultsTest() {
        List<Integer> integers = new ArrayList<>();
        integers.addAll(List.of(1,2,3,4,5,6,7));
        when(eventService.getEventById(anyLong())).thenReturn(event);
        when(surveyResultsRepository.getSurveyResults(event)).thenReturn(List.of(integers));

        MessageResponse response = surveyResultsService.getSurveyResults(1L);

        assertEquals(ResponseMessages.SUCCESS, response.getType());
        assertEquals(integers,response.getBody());
    }

    @Test
    void getSurveyResultsEmptyList() {
        List<Integer> integers = new ArrayList<>();
        integers.add(null);
        when(eventService.getEventById(anyLong())).thenReturn(event);
        when(surveyResultsRepository.getSurveyResults(event)).thenReturn(List.of(integers));

        MessageResponse response = surveyResultsService.getSurveyResults(1L);

        assertEquals(ResponseMessages.ERROR, response.getType());
        assertNull(response.getBody());
    }
}
package com.bgokce.eventmanagement.usecases.handlesurveyresults;

import com.bgokce.eventmanagement.common.ResponseMessages;
import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.model.MessageResponse;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonService;
import com.bgokce.eventmanagement.usecases.manageevents.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SurveyResultsService {

    private final SurveyResultsRepository surveyResultsRepository;
    private final EventService eventService;
    private final EventPersonService eventPersonService;

    public MessageResponse completeSurvey(Long eventId, String tcNo, SurveyResults surveyResults) {
        Event event = eventService.getEventById(eventId);
        if (event == null){
            return new MessageResponse(ResponseMessages.ERROR,ResponseMessages.EVENT_NOT_FOUND,null);
        }
        surveyResults.setOwnerEvent(event);
        surveyResultsRepository.save(surveyResults);
        eventPersonService.setCompletedSurvey(eventId,tcNo);
        return new MessageResponse(ResponseMessages.SUCCESS,ResponseMessages.SURVEY_COMPLETED_SUCCESSFULLY,null);
    }

    public MessageResponse getSurveyResults(Long eventId) {
        Event event = eventService.getEventById(eventId);
        List<List<Integer>> list = surveyResultsRepository.getSurveyResults(event);
        List<Integer> averagePoints = list.get(0);
        if (averagePoints.contains(null)){
            return new MessageResponse(ResponseMessages.ERROR,ResponseMessages.SURVEY_RESULT_NOT_FOUND,null);
        }
        return new MessageResponse(ResponseMessages.SUCCESS,null,averagePoints);
    }
}

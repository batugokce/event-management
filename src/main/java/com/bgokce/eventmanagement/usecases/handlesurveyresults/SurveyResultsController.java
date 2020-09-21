package com.bgokce.eventmanagement.usecases.handlesurveyresults;

import com.bgokce.eventmanagement.model.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SurveyResultsController {
    private final SurveyResultsService surveyResultsService;

    @PostMapping("/completeSurvey/{eventId}/{tcNo}")
    public ResponseEntity<MessageResponse> completeSurvey(@PathVariable Long eventId, @PathVariable String tcNo,
                                                 @RequestBody SurveyResults surveyResults){
        MessageResponse messageResponse = surveyResultsService.completeSurvey(eventId, tcNo,surveyResults);
        return ResponseEntity.ok().body(messageResponse);
    }

    @GetMapping("/admin/surveyResults/{eventId}")
    public ResponseEntity<MessageResponse> getSurveyResults(@PathVariable Long eventId){
        return ResponseEntity.ok().body(surveyResultsService.getSurveyResults(eventId));
    }
}

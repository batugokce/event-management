package com.bgokce.eventmanagement.usecases.managepersonquestions;

import com.bgokce.eventmanagement.model.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PersonQuestionController {

    private final PersonQuestionService personQuestionService;

    @CacheEvict(value = {"eventListAdmin","eventListUser"}, allEntries=true)
    @PostMapping("/askQuestion/{eventId}/{tcNo}")
    public ResponseEntity<MessageResponse> askQuestion(@PathVariable Long eventId, @PathVariable String tcNo,
                                              @RequestBody PersonQuestion personQuestion){
        MessageResponse messageResponse = personQuestionService.askQuestion(eventId,tcNo,personQuestion);
        return ResponseEntity.ok().body(messageResponse);
    }

    @GetMapping("/getQuestions/{eventId}/{tcNo}")
    public ResponseEntity<MessageResponse> getQuestions(@PathVariable Long eventId, @PathVariable String tcNo){
        return ResponseEntity.ok().body(personQuestionService.getQuestions(eventId, tcNo));
    }

    @CacheEvict(value = {"eventListAdmin","eventListUser"}, allEntries=true)
    @PostMapping("/answerQuestion/{questionId}")
    public ResponseEntity<MessageResponse> answerQuestion(@PathVariable Long questionId, @RequestBody PersonQuestion personQuestion){
        return ResponseEntity.ok().body(personQuestionService.answerQuestion(questionId,personQuestion));
    }
}

package com.bgokce.eventmanagement.usecases.attendevent;

import com.bgokce.eventmanagement.usecases.manageattending.Answer;
import com.bgokce.eventmanagement.usecases.manageattending.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer createAnswer(List<String> list){
        Answer answer = new Answer(list);
        return answerRepository.save(answer);
    }
}

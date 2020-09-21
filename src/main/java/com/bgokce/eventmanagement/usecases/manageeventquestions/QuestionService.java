package com.bgokce.eventmanagement.usecases.manageeventquestions;

import com.bgokce.eventmanagement.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question createQuestion(Question question){
        return questionRepository.save(question);
    }

    public Question prepareQuestionFromList(List<String> list){
        Question question = new Question(list);
        return question;
    }
}

package com.bgokce.eventmanagement.usecases.manageattending;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class AnswerTest {

    @Test
    void constructorTest() {
        List<String> stringList = new ArrayList<>();
        stringList.addAll(List.of("answer1","answer2","answer3"));
        Answer answer = new Answer(stringList);

        assertEquals("answer1",answer.getAnswer1());
        assertEquals("answer2",answer.getAnswer2());
        assertEquals("answer3",answer.getAnswer3());
    }
}
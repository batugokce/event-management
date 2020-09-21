package com.bgokce.eventmanagement.DTO;

import com.bgokce.eventmanagement.usecases.managepersonquestions.PersonQuestion;
import com.bgokce.eventmanagement.model.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;


@Builder
@Getter
@AllArgsConstructor
public class EventForUserDTO {
    private final Long id;

    private final String eventName;

    private final String city;

    private final int capacity;

    private final int personCount;

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

    private final Question question;

    private final String latitude;

    private final String longitude;

    private final Boolean isAttended;

    private final Boolean isCompletedSurvey;

    private final Set<PersonQuestion> personQuestions;
}

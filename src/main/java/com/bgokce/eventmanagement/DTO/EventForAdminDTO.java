package com.bgokce.eventmanagement.DTO;

import com.bgokce.eventmanagement.usecases.manageperson.Person;
import com.bgokce.eventmanagement.usecases.managepersonquestions.PersonQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class EventForAdminDTO {
    private final Long id;

    private final String eventName;

    private final String city;

    private final int capacity;

    private final int personCount;

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

    private final String latitude;

    private final String longitude;

    //private final Set<Person> participants;
    private final Set<PersonDTO> participants;

    private final Set<PersonQuestion> personQuestions;
}

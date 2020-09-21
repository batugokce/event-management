package com.bgokce.eventmanagement.DTO;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class AttendingDTO {

    private Long eventId;
    private String personTc;
    private List<String> answers;
}

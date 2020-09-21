package com.bgokce.eventmanagement.usecases.manageevents;

import com.bgokce.eventmanagement.common.BaseEntity;
import com.bgokce.eventmanagement.model.Question;
import com.bgokce.eventmanagement.usecases.handlesurveyresults.SurveyResults;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")*/
@Entity
@Table
@Setter
@Getter
@SequenceGenerator(name = "idgen", sequenceName = "event_seq")
public class Event extends BaseEntity {


    @Column(name = "EVENT_NAME")
    private String eventName;

    @Column(name = "CITY")
    private String city;

    @Column(name = "CAPACITY")
    private int capacity;

    @Column(name = "PERSON_COUNT")
    private int personCount;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "LATITUDE")
    private String latitude;

    @Column(name = "LONGITUDE")
    private String longitude;

    @Column(name = "IS_FULL")
    private Boolean isFull = false;

    @Column(name = "HAS_QUESTION")
    private Boolean hasQuestion = false;

    @JsonIgnore
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<EventPerson> attendedPeople = new HashSet<EventPerson>();

    @OneToOne(cascade = CascadeType.ALL)
    private Question question;

    @JsonIgnore
    @OneToMany(mappedBy = "ownerEvent", cascade = CascadeType.ALL)
    private Set<SurveyResults> surveyResults = new HashSet<>();
}

package com.bgokce.eventmanagement.usecases.handlesurveyresults;

import com.bgokce.eventmanagement.common.BaseEntity;
import com.bgokce.eventmanagement.usecases.manageevents.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Setter
public class SurveyResults extends BaseEntity {
    @Column(name = "QUESTION1")
    private int question1;

    @Column(name = "QUESTION2")
    private int question2;

    @Column(name = "QUESTION3")
    private int question3;

    @Column(name = "QUESTION4")
    private int question4;

    @Column(name = "QUESTION5")
    private int question5;

    @Column(name = "QUESTION6")
    private int question6;

    @Column(name = "QUESTION7")
    private int question7;

    @ManyToOne
    private Event ownerEvent;

}

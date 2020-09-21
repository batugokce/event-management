package com.bgokce.eventmanagement.usecases.managepersonquestions;

import com.bgokce.eventmanagement.common.BaseEntity;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@Setter
@Getter
@SequenceGenerator(name = "idgen", sequenceName = "userQuestion_seq")
public class PersonQuestion extends BaseEntity {

    private String question;

    private String answer;

    @JsonIgnore
    @ManyToOne
    private EventPerson ownerAttending;
}

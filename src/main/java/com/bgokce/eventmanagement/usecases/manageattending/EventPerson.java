package com.bgokce.eventmanagement.usecases.manageattending;

import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import com.bgokce.eventmanagement.usecases.managepersonquestions.PersonQuestion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(EventPersonPK.class)
@EntityListeners(AuditingEntityListener.class)
public class EventPerson implements Serializable {

    @Id
    private Long eventId;

    @Id
    private String tcNo;

    @Column(name = "IS_COMPLETED_SURVEY")
    private Boolean isCompletedSurvey;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventId" , updatable = false, insertable = false, referencedColumnName = "id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_tc", updatable = false, insertable = false, referencedColumnName = "TC")
    private Person person;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "answers")
    private Answer answer;

    @OneToMany(mappedBy = "ownerAttending", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PersonQuestion> usersQuestions;

    @CreatedDate
    @Column(name = "CREATED", updatable = false)
    private LocalDate created;

    public EventPerson(Long eventId, String tcNo){
        this.eventId = eventId;
        this.tcNo = tcNo;
    }

    public boolean equals(EventPerson eventPerson) {
        return (this.eventId.equals(eventPerson.getEventId()) && this.tcNo.equals(eventPerson.getTcNo()));
    }
}

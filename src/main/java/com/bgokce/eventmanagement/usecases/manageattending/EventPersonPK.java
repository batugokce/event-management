package com.bgokce.eventmanagement.usecases.manageattending;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventPersonPK implements Serializable {

    @Column(name = "eventId")
    private Long eventId;

    @Column(name = "PERSON_TC")
    private String tcNo;


    public int hashCode() {
        return (int)(eventId + 1);
    }

    public boolean equals(Object object) {
        if (object instanceof EventPersonPK) {
            EventPersonPK otherId = (EventPersonPK) object;
            return (otherId.eventId == this.eventId)
                    && (otherId.tcNo.equals(this.tcNo));
        }
        return false;
    }
}

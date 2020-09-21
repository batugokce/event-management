package com.bgokce.eventmanagement.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PersonDTO {

    private final String nameSurname;

    private final String tcNo;

    private final String email;

    public PersonDTO(@JsonProperty("nameSurname") String nameSurname,
                     @JsonProperty("tcNo") String tcNo,
                     @JsonProperty("email") String email) {
        this.nameSurname = nameSurname;
        this.tcNo = tcNo;
        this.email = email;
    }
}

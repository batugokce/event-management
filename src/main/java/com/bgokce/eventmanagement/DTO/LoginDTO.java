package com.bgokce.eventmanagement.DTO;

import com.bgokce.eventmanagement.validator.TCKimlikNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class LoginDTO {

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    private final String firebaseToken;

    public LoginDTO(@JsonProperty("username") String username,
                    @JsonProperty("password") String password,
                    @JsonProperty("firebaseToken") String firebaseToken) {
        this.username = username;
        this.password = password;
        this.firebaseToken = firebaseToken;
    }
}

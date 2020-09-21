package com.bgokce.eventmanagement.usecases.manageevents;

import com.bgokce.eventmanagement.CommonFunctions;
import com.bgokce.eventmanagement.DTO.AuthenticationResponse;
import com.bgokce.eventmanagement.DTO.LoginDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private static String jwt;

    @BeforeAll
    void loginTest() {
        LoginDTO loginDTO = new LoginDTO("admin","admin",null);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO,headers);
        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(
                "/api/authenticate",HttpMethod.POST,requestEntity,AuthenticationResponse.class
        );
        assertEquals(HttpStatus.OK,response.getStatusCode());
        jwt =  response.getBody().getJwt();
    }

    @Test
    void getEventByIdTest() {
        HttpEntity requestEntity = CommonFunctions.getRequestEntityWithoutBody(jwt);
        ResponseEntity<Event> response = restTemplate.exchange("/api/events/53", HttpMethod.GET,requestEntity,Event.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
}

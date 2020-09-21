package com.bgokce.eventmanagement.usecases.login;

import com.bgokce.eventmanagement.usecases.manageperson.Person;
import com.bgokce.eventmanagement.usecases.manageperson.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final PersonRepository personRepository;

    void setFirebaseToken(String username, String firebaseToken){
        System.out.println("token: " + firebaseToken);
        if (firebaseToken != null && !firebaseToken.equals("")) {
            personRepository.setFirebaseToken(username, firebaseToken);
        }
    }

}

package com.bgokce.eventmanagement.mapper;

import com.bgokce.eventmanagement.DTO.PersonDTO;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonRepository;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMapper {

    public static PersonDTO mapToDTO(Person person){
        return new PersonDTO(person.getNameSurname(),person.getTcNo(),person.getEmail());
    }
}

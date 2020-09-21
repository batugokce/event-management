package com.bgokce.eventmanagement.usecases.manageperson;

import com.bgokce.eventmanagement.model.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PersonController {

    private final PersonService personService;

    @GetMapping("/admin/people")
    public List<Person> getAllPeople(){
        return personService.getAllPeople();
    }

    @GetMapping("/admin/people/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable String id) {
        Person person = personService.getPersonById(id);
        if (person != null){
            return ResponseEntity.ok().body(person);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/createPerson")
    public ResponseEntity<MessageResponse> createPerson(@Valid @RequestBody Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.ok().body(new MessageResponse("error",message,null));
        }
        return ResponseEntity.ok().body(personService.createPerson(person));
    }

}

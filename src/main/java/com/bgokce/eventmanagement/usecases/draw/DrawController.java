package com.bgokce.eventmanagement.usecases.draw;

import com.bgokce.eventmanagement.DTO.PersonDTO;
import com.bgokce.eventmanagement.mapper.PersonMapper;
import com.bgokce.eventmanagement.usecases.manageperson.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DrawController {

    private final DrawService drawService;

    @GetMapping("/admin/draw/{id}")
    public ResponseEntity<PersonDTO> draw(@PathVariable long id){
        Person person = drawService.draw(id);
        if (person != null){
            return ResponseEntity.ok().body(PersonMapper.mapToDTO(person));
        }
        return ResponseEntity.noContent().build();
    }
}

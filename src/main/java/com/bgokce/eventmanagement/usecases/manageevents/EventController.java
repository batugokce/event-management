package com.bgokce.eventmanagement.usecases.manageevents;

import com.bgokce.eventmanagement.DTO.EventDTO;
import com.bgokce.eventmanagement.DTO.EventForAdminDTO;
import com.bgokce.eventmanagement.DTO.EventForUserDTO;
import com.bgokce.eventmanagement.mapper.EventMapper;
import com.bgokce.eventmanagement.model.*;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonService;
import com.bgokce.eventmanagement.usecases.manageeventquestions.QuestionService;
import com.bgokce.eventmanagement.usecases.manageperson.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;
    private final EventPersonService eventPersonService;

    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id){
        Event event = eventService.getEventById(id);
        if (event != null){
            return ResponseEntity.ok().body(event);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @CacheEvict(value = {"eventListAdmin","eventListUser"}, allEntries=true)
    @PostMapping("/admin/events/create")
    public ResponseEntity<MessageResponse> createEventWithQuestions(@Valid @RequestBody EventDTO eventDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.ok().body(new MessageResponse("error",message,null));
        }
        Event event = EventMapper.mapToEntity(eventDTO);
        eventService.createEventWithQuestions(event, eventDTO.getQuestions());
        return ResponseEntity.ok().body(new MessageResponse("success","Etkinlik başarıyla oluşturuldu",null));
    }

    @Caching(put = @CachePut(value = "eventListAdmin", key = "'eventListAdmin'"),
             evict = @CacheEvict(value = "eventListUser", allEntries = true))
    @PutMapping("/admin/events/{id}")
    public ResponseEntity<MessageResponse> updateEvent(@PathVariable long id, @RequestBody Event event) {
        MessageResponse messageResponse = eventService.updateEvent(event,id);
        messageResponse.setBody(eventPersonService.getAllEventsForAdmin());

        return ResponseEntity.ok().body(messageResponse);
    }

    @Caching(put = @CachePut(value = "eventListAdmin", key = "'eventListAdmin'"),
             evict = @CacheEvict(value = "eventListUser", allEntries = true))
    @DeleteMapping("/admin/events/{id}")
    public ResponseEntity<MessageResponse> deleteEvent(@PathVariable long id) {
        MessageResponse messageResponse = eventService.deleteEvent(id);
        messageResponse.setBody(eventPersonService.getAllEventsForAdmin());

        return ResponseEntity.ok().body(messageResponse);
    }

}

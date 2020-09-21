package com.bgokce.eventmanagement.usecases.attendevent;

import com.bgokce.eventmanagement.DTO.AttendingDTO;
import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.model.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AttendEventController {

    private final AttendEventService attendEventService;

    @CacheEvict(value = {"eventListAdmin","eventListUser"}, allEntries = true)
    @PostMapping("/people/attendEvent")
    public ResponseEntity<MessageResponse> attendEvent(@RequestBody AttendingDTO attendingDTO) throws Exception {
        Long id = attendingDTO.getEventId();
        String tc = attendingDTO.getPersonTc();
        List<String> list = attendingDTO.getAnswers();
        MessageResponse messageResponse = attendEventService.attendEvent(id, tc, list);
        return ResponseEntity.ok().body(messageResponse);
    }
}

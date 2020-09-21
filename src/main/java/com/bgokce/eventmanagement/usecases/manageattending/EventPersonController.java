package com.bgokce.eventmanagement.usecases.manageattending;

import com.bgokce.eventmanagement.DTO.EventForAdminDTO;
import com.bgokce.eventmanagement.DTO.EventForUserDTO;
import com.bgokce.eventmanagement.model.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EventPersonController {

    private final EventPersonService eventPersonService;

    @GetMapping("/admin/events")
    @Cacheable(value = "eventListAdmin", key = "'eventListAdmin'")
    public ResponseEntity<MessageResponse> getAllEvents() {
        List list = eventPersonService.getAllEventsForAdmin();
        return ResponseEntity.ok().body(new MessageResponse("success",null,list));
    }

    @Cacheable(value = "eventListUser", key = "#tcNo")
    @GetMapping("/events/for/{tcNo}")
    public ResponseEntity<MessageResponse> getAllCurrentEvents(@PathVariable String tcNo) {
        List<EventForUserDTO> list = eventPersonService.getAllCurrentEventsForUser(tcNo);
        return ResponseEntity.ok().body(new MessageResponse("success",null,list));
    }

}

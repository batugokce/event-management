package com.bgokce.eventmanagement.usecases.sendNotification;

import com.bgokce.eventmanagement.model.MessageResponse;
import lombok.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/admin/sendNotification/{eventId}")
    public ResponseEntity<MessageResponse> sendNotification(@PathVariable Long eventId) throws URISyntaxException {
        MessageResponse messageResponse = notificationService.sendNotification(eventId);
        return ResponseEntity.ok().body(messageResponse);
    }
}

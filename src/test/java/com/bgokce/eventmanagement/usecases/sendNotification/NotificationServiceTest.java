package com.bgokce.eventmanagement.usecases.sendNotification;

import com.bgokce.eventmanagement.CommonFunctions;
import com.bgokce.eventmanagement.model.MessageResponse;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonRepository;
import com.bgokce.eventmanagement.usecases.manageevents.Event;
import com.bgokce.eventmanagement.usecases.manageevents.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    EventPersonRepository eventPersonRepository;
    @Mock
    EventRepository eventRepository;

    @InjectMocks
    NotificationService notificationService;

    Event event;

    @BeforeEach
    void setUp() {
        event = CommonFunctions.getValidEvent();
    }

    @Test
    void setNotification() {
        event.setStartDate(LocalDateTime.now().plusDays(2));

        boolean result = notificationService.setNotification(event.getStartDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),1L);

        assertTrue(result);
    }

    @Test
    void setNotificationAfterNotification() {
        boolean result = notificationService.setNotification(event.getStartDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),1L);

        assertFalse(result);
    }

    @Test
    void sendNotification() throws URISyntaxException {
        Set<String> tokens = Set.of("token");
        when(eventPersonRepository.getFirebaseTokensOfParticipants(anyLong())).thenReturn(tokens);
        when(eventRepository.findById(any())).thenReturn(Optional.of(event));

        MessageResponse response = notificationService.sendNotification(1L);

        assertEquals("success", response.getType());
    }

    @Test
    void sendNotificationEmptySet() throws URISyntaxException {
        Set<String> tokens = Set.of();
        when(eventPersonRepository.getFirebaseTokensOfParticipants(anyLong())).thenReturn(tokens);

        MessageResponse response = notificationService.sendNotification(1L);

        assertEquals("warn", response.getType());
    }
}
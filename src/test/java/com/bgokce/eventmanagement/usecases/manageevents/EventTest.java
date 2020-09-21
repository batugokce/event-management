package com.bgokce.eventmanagement.usecases.manageevents;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    Event event;

    @BeforeEach
    void setUp() {
        event = new Event();
        event.setEventName("Etkinlik ismi");
        event.setCity("Mersin");
        event.setCapacity(100);
    }

    @Test
    void testEntireEvent(){
        assertAll(
                () -> assertEquals("Etkinlik ismi",event.getEventName()),
                () -> assertEquals("Mersin",event.getCity())
        );
    }

    @Test
    void getEventName() {
        assertEquals("Etkinlik ismi", event.getEventName());
    }

    @Test
    void getCity() {
        assertEquals(event.getCity(),"Mersin");
    }

    @Test
    void getCapacity() {
        assertEquals(event.getCapacity(),100);
    }

    @Test
    void getPersonCount() {
        assertEquals(event.getPersonCount(),0);
    }

    @Test
    void getStartDate() {
        LocalDateTime date = LocalDateTime.now();
        //assertEquals(date,event.getStartDate());
    }
}
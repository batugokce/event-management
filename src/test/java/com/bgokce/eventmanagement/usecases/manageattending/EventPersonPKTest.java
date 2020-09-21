package com.bgokce.eventmanagement.usecases.manageattending;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EventPersonPKTest {

    @Test
    void testEquals() {
        EventPersonPK eventPersonPK = new EventPersonPK(1L,"11111111110");
        EventPersonPK otherEventPersonPK = new EventPersonPK(1L,"11111111110");

        boolean result =eventPersonPK.equals(otherEventPersonPK);

        assertTrue(result);
    }

    @Test
    void testEqualsNoArgs() {
        EventPersonPK eventPersonPK = new EventPersonPK(1L,"11111111110");
        EventPersonPK otherEventPersonPK = new EventPersonPK();
        otherEventPersonPK.setEventId(1L);
        otherEventPersonPK.setTcNo("11111111110");

        boolean result =eventPersonPK.equals(otherEventPersonPK);

        assertTrue(result);
    }

    @Test
    void testEqualsNotEqual() {
        EventPersonPK eventPersonPK = new EventPersonPK(1L,"11111111110");
        EventPersonPK otherEventPersonPK = new EventPersonPK(2L,"11111111110");

        boolean result = eventPersonPK.equals(otherEventPersonPK);

        assertFalse(result);
    }

    @Test
    void testHashCode() {
        EventPersonPK eventPersonPK = new EventPersonPK(1L,"11111111110");

        int hashCode = eventPersonPK.hashCode();

        assertEquals(eventPersonPK.getEventId()+1,hashCode);
    }
}
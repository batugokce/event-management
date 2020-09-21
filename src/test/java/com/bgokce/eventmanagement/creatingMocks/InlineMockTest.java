package com.bgokce.eventmanagement.creatingMocks;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class InlineMockTest {

    @Test
    void testInlineMock() {
        Map mockMap = mock(Map.class);

        assertEquals(mockMap.size(),0);
    }
}

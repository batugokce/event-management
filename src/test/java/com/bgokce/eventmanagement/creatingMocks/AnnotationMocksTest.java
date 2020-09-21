package com.bgokce.eventmanagement.creatingMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnnotationMocksTest {

    @Mock
    Map<String, Object> mockMap;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testMock(){
        mockMap.put("key","foo");

        assertEquals(0, mockMap.size());
    }
}

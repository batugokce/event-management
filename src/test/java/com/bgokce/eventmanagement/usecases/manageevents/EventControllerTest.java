package com.bgokce.eventmanagement.usecases.manageevents;

import com.bgokce.eventmanagement.CommonFunctions;
import com.bgokce.eventmanagement.DTO.EventDTO;
import com.bgokce.eventmanagement.model.MessageResponse;
import com.bgokce.eventmanagement.usecases.manageattending.EventPerson;
import com.bgokce.eventmanagement.usecases.manageattending.EventPersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.validation.BindingResult;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Mock
    EventService eventService;
    @Mock
    EventPersonService eventPersonService;

    @InjectMocks
    EventController eventController;

    ObjectWriter objectWriter;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        objectWriter = CommonFunctions.getObjectWriter();
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    void getEventByIdFoundTest() throws Exception {
        Event validEvent = CommonFunctions.getValidEvent();
        when(eventService.getEventById(anyLong())).thenReturn(validEvent);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/events/{id}",1L);

        MvcResult mvcResult = mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventName").value(validEvent.getEventName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity").value(validEvent.getCapacity()))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
    }

    @Test
    void getEventByIdNotFoundTest() throws Exception {
        when(eventService.getEventById(anyLong())).thenReturn(null);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/events/{id}",1L);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void createEventWithQuestionsValidTest() throws Exception {
        EventDTO eventDTO = CommonFunctions.getValidEventDTO();
        Event validEvent = CommonFunctions.getValidEvent();
        when(eventService.createEventWithQuestions(any(),any())).thenReturn(validEvent);

        String reqJson = objectWriter.writeValueAsString(eventDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/admin/events/create")
                .contentType(APPLICATION_JSON_UTF8)
                .content(reqJson);

        MvcResult mvcResult  = mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("success"))
                .andReturn();
    }

    @Test
    void createEventWithQuestionsInvalidTest() throws Exception {
        EventDTO eventDTO = CommonFunctions.getInvalidEventDTO();
        String reqJson = objectWriter.writeValueAsString(eventDTO);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/admin/events/create")
                .contentType(APPLICATION_JSON_UTF8)
                .content(reqJson);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("error"))
                .andReturn();
    }

    @Test
    void updateEventTest() throws Exception {
        Event event = CommonFunctions.getValidEvent();
        when(eventService.updateEvent(any(),anyLong())).thenReturn(new MessageResponse("success","message",null));
        when(eventPersonService.getAllEventsForAdmin()).thenReturn(new ArrayList<>());

        String reqJson = objectWriter.writeValueAsString(event);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api//admin/events/{id}",1L)
                .contentType(APPLICATION_JSON_UTF8)
                .content(reqJson);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").value(new ArrayList<>()));
    }

    @Test
    void deleteEventTest() throws Exception {
        when(eventService.deleteEvent(anyLong())).thenReturn(new MessageResponse("success","mesage","body"));
        when(eventPersonService.getAllEventsForAdmin()).thenReturn(new ArrayList<>());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/api//admin/events/{id}",1L);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").value(new ArrayList<>()))
                .andReturn();
    }

}
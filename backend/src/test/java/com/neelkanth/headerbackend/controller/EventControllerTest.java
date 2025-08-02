// File: backend/src/test/java/com/neelkanth/headerbackend/controller/EventControllerTest.java
package com.neelkanth.headerbackend.controller;

import com.neelkanth.headerbackend.entity.Event;
import com.neelkanth.headerbackend.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    void getUserEvents_returnsOk() throws Exception {
        when(eventService.getEventsByUserId(1L)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/event/1/fetchEvents"))
                .andExpect(status().isOk());
    }

    // Add tests for POST, PUT, DELETE endpoints
}
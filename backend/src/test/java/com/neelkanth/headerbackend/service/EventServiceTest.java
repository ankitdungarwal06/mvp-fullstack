// File: backend/src/test/java/com/neelkanth/headerbackend/service/EventServiceTest.java
package com.neelkanth.headerbackend.service;

import com.neelkanth.headerbackend.entity.Event;
import com.neelkanth.headerbackend.entity.User;
import com.neelkanth.headerbackend.repository.EventRepository;
import com.neelkanth.headerbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private EventService eventService;

    public EventServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEventsByUserId_returnsEvents() {
        when(eventRepository.findByUser_Id(1L)).thenReturn(Collections.emptyList());
        assertEquals(0, eventService.getEventsByUserId(1L).size());
    }

    @Test
    void createEvent_validUser_savesEvent() {
        User user = new User();
        user.setId(1L);
        Event event = new Event();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.save(event)).thenReturn(event);
        assertEquals(event, eventService.createEvent(event, 1L));
    }

    @Test
    void createEvent_invalidUser_throwsError() {
        Event event = new Event();
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(Error.class, () -> eventService.createEvent(event, 2L));
    }

    // Add similar tests for updateEvent and deleteEvent
}
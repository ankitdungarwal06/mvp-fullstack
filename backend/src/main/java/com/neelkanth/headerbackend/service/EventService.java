package com.neelkanth.headerbackend.service;

import com.neelkanth.headerbackend.entity.Event;
import com.neelkanth.headerbackend.entity.User;
import com.neelkanth.headerbackend.repository.EventRepository;
import com.neelkanth.headerbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;


    public List<Event> getEventsByUserId(Long userId) {
        List<Event> response = eventRepository.findByUser_Id(userId);
        return response;
    }

    public Event createEvent(Event event, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Error("User not found: " + userId));
        event.setUser(user);
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event event, Long userId) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new Error("Event not found: " + id));
        if (!existingEvent.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Event does not belong to user: " + userId);
        }
        existingEvent.setTitle(event.getTitle());
        existingEvent.setStart(event.getStart());
        existingEvent.setEnd(event.getEnd());
        existingEvent.setLocation(event.getLocation());
        existingEvent.setNotes(event.getNotes());
        existingEvent.setCategory(event.getCategory());
        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(Long id, Long userId) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new Error("Event not found: " + id));
        if (!event.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Event does not belong to user: " + userId);
        }
        eventRepository.deleteById(id);
    }
}

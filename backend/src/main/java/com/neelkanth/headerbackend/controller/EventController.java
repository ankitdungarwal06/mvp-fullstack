package com.neelkanth.headerbackend.controller;

import com.neelkanth.headerbackend.entity.Event;
import com.neelkanth.headerbackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/{userId}/fetchEvents")
    public ResponseEntity<List<Event>> getUserEvents(@PathVariable Long userId) {
        return ResponseEntity.ok(eventService.getEventsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event, @RequestParam Long userId) {
        return ResponseEntity.ok(eventService.createEvent(event, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event, @RequestParam Long userId) {
        return ResponseEntity.ok(eventService.updateEvent(id, event, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id, @RequestParam Long userId) {
        eventService.deleteEvent(id, userId);
        return ResponseEntity.ok().build();
    }
}

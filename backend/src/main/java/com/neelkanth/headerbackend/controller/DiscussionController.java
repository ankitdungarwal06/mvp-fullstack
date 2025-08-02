package com.neelkanth.headerbackend.controller;

import com.neelkanth.headerbackend.entity.Discussion;
import com.neelkanth.headerbackend.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discussion")
public class DiscussionController {
    @Autowired
    private DiscussionService discussionService;

    @GetMapping("/{userId}/fetchDiscussions")
    public ResponseEntity<List<Discussion>> getUserDiscussions(@PathVariable Long userId) {
        return ResponseEntity.ok(discussionService.getDiscussionsByUserId(userId));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Discussion>> getEventDiscussions(@PathVariable Long eventId) {
        return ResponseEntity.ok(discussionService.getDiscussionsByEventId(eventId));
    }

    @PostMapping
    public ResponseEntity<Discussion> createDiscussion(@RequestBody Discussion discussion, @RequestParam Long userId, @RequestParam(required = false) Long eventId) {
        return ResponseEntity.ok(discussionService.createDiscussion(discussion, userId, eventId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Discussion> updateDiscussion(@PathVariable Long id, @RequestBody Discussion discussion, @RequestParam Long userId) {
        return ResponseEntity.ok(discussionService.updateDiscussion(id, discussion, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscussion(@PathVariable Long id, @RequestParam Long userId) {
        discussionService.deleteDiscussion(id, userId);
        return ResponseEntity.ok().build();
    }
}

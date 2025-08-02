package com.neelkanth.headerbackend.controller;

import com.neelkanth.headerbackend.entity.DailyNote;
import com.neelkanth.headerbackend.service.DailyNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/daily-note")
public class DailyNoteController {
    @Autowired
    private DailyNoteService dailyNoteService;

    @GetMapping("/{userId}/fetchNotes")
    public ResponseEntity<List<DailyNote>> getUserDailyNotes(@PathVariable Long userId) {
        return ResponseEntity.ok(dailyNoteService.getDailyNotesByUserId(userId));
    }

    @GetMapping("/{userId}/search")
    public ResponseEntity<List<DailyNote>> searchDailyNotesByTag(@PathVariable Long userId, @RequestParam String tag) {
        return ResponseEntity.ok(dailyNoteService.getDailyNotesByTag(userId, tag));
    }

    @PostMapping
    public ResponseEntity<DailyNote> createDailyNote(@RequestBody DailyNote dailyNote, @RequestParam Long userId) {
        return ResponseEntity.ok(dailyNoteService.createDailyNote(dailyNote, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DailyNote> updateDailyNote(@PathVariable Long id, @RequestBody DailyNote dailyNote, @RequestParam Long userId) {
        return ResponseEntity.ok(dailyNoteService.updateDailyNote(id, dailyNote, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDailyNote(@PathVariable Long id, @RequestParam Long userId) {
        dailyNoteService.deleteDailyNote(id, userId);
        return ResponseEntity.ok().build();
    }
}

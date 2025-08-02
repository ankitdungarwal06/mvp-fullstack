package com.neelkanth.headerbackend.service;

import com.neelkanth.headerbackend.entity.DailyNote;
import com.neelkanth.headerbackend.entity.User;
import com.neelkanth.headerbackend.repository.DailyNoteRepository;
import com.neelkanth.headerbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyNoteService {
    @Autowired
    private DailyNoteRepository dailyNoteRepository;

    @Autowired
    private UserRepository userRepository;

    public List<DailyNote> getDailyNotesByUserId(Long userId) {
        return dailyNoteRepository.findByUser_Id(userId);
    }

    public List<DailyNote> getDailyNotesByTag(Long userId, String tag) {
        return dailyNoteRepository.findByUser_IdAndTagsContaining(userId, tag);
    }

    public DailyNote createDailyNote(DailyNote dailyNote, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Error("User not found: " + userId));
        dailyNote.setUser(user);
        return dailyNoteRepository.save(dailyNote);
    }

    public DailyNote updateDailyNote(Long id, DailyNote dailyNote, Long userId) {
        DailyNote existing = dailyNoteRepository.findById(id)
                .orElseThrow(() -> new Error("DailyNote not found: " + id));
        if (!existing.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("DailyNote does not belong to user: " + userId);
        }
        existing.setContent(dailyNote.getContent());
        existing.setDate(dailyNote.getDate());
        existing.setTags(dailyNote.getTags());
        return dailyNoteRepository.save(existing);
    }

    public void deleteDailyNote(Long id, Long userId) {
        DailyNote dailyNote = dailyNoteRepository.findById(id)
                .orElseThrow(() -> new Error("DailyNote not found: " + id));
        if (!dailyNote.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("DailyNote does not belong to user: " + userId);
        }
        dailyNoteRepository.deleteById(id);
    }
}

package com.neelkanth.headerbackend.service;

import com.neelkanth.headerbackend.entity.Discussion;
import com.neelkanth.headerbackend.entity.Event;
import com.neelkanth.headerbackend.entity.Task;
import com.neelkanth.headerbackend.entity.User;
import com.neelkanth.headerbackend.repository.DiscussionRepository;
import com.neelkanth.headerbackend.repository.EventRepository;
import com.neelkanth.headerbackend.repository.TaskRepository;
import com.neelkanth.headerbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussionService {
    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TaskRepository taskRepository;

    public List<Discussion> getDiscussionsByUserId(Long userId) {
        return discussionRepository.findByUser_Id(userId);
    }

    public List<Discussion> getDiscussionsByEventId(Long eventId) {
        return discussionRepository.findByEvent_Id(eventId);
    }

    public Discussion createDiscussion(Discussion discussion, Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Error("User not found: " + userId));
        discussion.setUser(user);
        if (eventId != null) {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new Error("Event not found: " + eventId));
            discussion.setEvent(event);
        }
        return discussionRepository.save(discussion);
    }

    public Discussion updateDiscussion(Long id, Discussion discussion, Long userId) {
        Discussion existing = discussionRepository.findById(id)
                .orElseThrow(() -> new Error("Discussion not found: " + id));
        if (!existing.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Discussion does not belong to user: " + userId);
        }
        existing.setQuestion(discussion.getQuestion());
        existing.setStatus(discussion.getStatus());
        existing.setSummary(discussion.getSummary());
        if (discussion.getEventId() != null) {
            Event event = eventRepository.findById(discussion.getEventId())
                    .orElseThrow(() -> new Error("Event not found: " + discussion.getEventId()));
            existing.setEvent(event);
        } else {
            existing.setEvent(null);
        }
        if (discussion.getTaskId() != null) {
            Task task = taskRepository.findById(discussion.getTaskId())
                    .orElseThrow(() -> new Error("Task not found: " + discussion.getTaskId()));
            existing.setTask(task);
        } else {
            existing.setTask(null);
        }
        return discussionRepository.save(existing);
    }

    public void deleteDiscussion(Long id, Long userId) {
        Discussion discussion = discussionRepository.findById(id)
                .orElseThrow(() -> new Error("Discussion not found: " + id));
        if (!discussion.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Discussion does not belong to user: " + userId);
        }
        discussionRepository.deleteById(id);
    }
}

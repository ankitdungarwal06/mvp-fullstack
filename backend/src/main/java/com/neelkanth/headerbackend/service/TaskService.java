package com.neelkanth.headerbackend.service;

import com.neelkanth.headerbackend.entity.Task;
import com.neelkanth.headerbackend.entity.User;
import com.neelkanth.headerbackend.repository.TaskRepository;
import com.neelkanth.headerbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUser_Id(userId);
    }

    public Task createTask(Task task, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Error("User not found: " + userId));
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task, Long userId) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new Error("Task not found: " + id));
        if (!existing.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Task does not belong to user: " + userId);
        }
        existing.setDescription(task.getDescription());
        existing.setDiscussionDate(task.getDiscussionDate());
        existing.setNotes(task.getNotes());
        existing.setPriority(task.getPriority());
        existing.setFollowUpDate(task.getFollowUpDate());
        existing.setStatus(task.getStatus());
        return taskRepository.save(existing);
    }

    public void deleteTask(Long id, Long userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new Error("Task not found: " + id));
        if (!task.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Task does not belong to user: " + userId);
        }
        taskRepository.deleteById(id);
    }
}

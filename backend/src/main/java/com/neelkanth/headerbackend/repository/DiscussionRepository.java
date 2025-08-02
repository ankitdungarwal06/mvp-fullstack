package com.neelkanth.headerbackend.repository;

import com.neelkanth.headerbackend.entity.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    List<Discussion> findByUser_Id(Long userId);
    List<Discussion> findByEvent_Id(Long eventId);
}

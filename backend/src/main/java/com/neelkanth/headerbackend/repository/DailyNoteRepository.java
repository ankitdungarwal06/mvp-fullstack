package com.neelkanth.headerbackend.repository;

import com.neelkanth.headerbackend.entity.DailyNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyNoteRepository extends JpaRepository<DailyNote, Long> {
    List<DailyNote> findByUser_Id(Long userId);
    List<DailyNote> findByUser_IdAndTagsContaining(Long userId, String tag);
}

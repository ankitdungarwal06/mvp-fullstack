package com.neelkanth.headerbackend.repository;

import com.neelkanth.headerbackend.entity.ImportantDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ImportantDateRepository extends JpaRepository<ImportantDate, Long> {
    @Query("SELECT d FROM ImportantDate d WHERE d.date BETWEEN :startDate AND :endDate AND d.user.id = :userId")
    List<ImportantDate> findByDateRangeAndUserId(Date startDate, Date endDate, Long userId);
}

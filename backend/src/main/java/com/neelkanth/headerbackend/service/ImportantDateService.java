package com.neelkanth.headerbackend.service;

import com.neelkanth.headerbackend.dto.ImportantDateDTO;
import com.neelkanth.headerbackend.entity.ImportantDate;
import com.neelkanth.headerbackend.repository.ImportantDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ImportantDateService {

    private static final String CACHE_KEY_PREFIX = "upcomingDates";
    private static final long CACHE_TTL_MINUTES = 60; // 1 hour TTL
    private final ImportantDateRepository dateRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public ImportantDateService(ImportantDateRepository dateRepository, RedisTemplate<String, Object> redisTemplate) {
        this.dateRepository = dateRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<ImportantDateDTO> getUpcomingDates(String userId) {
        Long idUser = Long.valueOf(userId);
        String cacheKey = CACHE_KEY_PREFIX + idUser;

        // Check cache first
        @SuppressWarnings("unchecked")
        List<ImportantDateDTO> cachedDates = (List<ImportantDateDTO>) redisTemplate.opsForValue().get(cacheKey);
        if (cachedDates != null && !cachedDates.isEmpty()) {
            return cachedDates;
        }

        // Fetch from database
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(2);
        List<ImportantDate> dates = dateRepository.findAll();
       // List<ImportantDate> dates = dateRepository.findByDateRangeAndUserId(Date.valueOf(startDate), Date.valueOf(endDate), idUser);
        dates.stream().forEach(System.out::print);

        // Convert to DTOs
        List<ImportantDateDTO> dateDTOs = dates.stream()
                .map(date -> {
                    ImportantDateDTO dto = new ImportantDateDTO();
                    dto.setId(date.getId());
                    dto.setOccasion(date.getOccasion());
                    dto.setDate(date.getDate());
                    dto.setRestricted(date.getRestricted());
                    return dto;
                })
                .collect(Collectors.toList());

        // Store in cache
        if (!dateDTOs.isEmpty()) {
            redisTemplate.opsForValue().set(cacheKey, dates, CACHE_TTL_MINUTES, TimeUnit.MINUTES);
        }
        return dateDTOs;
    }
}
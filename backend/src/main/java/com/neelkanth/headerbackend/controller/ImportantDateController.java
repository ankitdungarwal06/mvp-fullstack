package com.neelkanth.headerbackend.controller;

import com.neelkanth.headerbackend.dto.ImportantDateDTO;
import com.neelkanth.headerbackend.entity.ImportantDate;
import com.neelkanth.headerbackend.service.ImportantDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/upcoming-dates")
public class ImportantDateController {
    @Autowired
    private ImportantDateService dateService;

    @GetMapping
    public List<ImportantDateDTO> getUpcomingDates(@PathVariable String userId) {
        return dateService.getUpcomingDates(userId);
    }
}

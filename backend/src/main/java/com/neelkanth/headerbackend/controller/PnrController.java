package com.neelkanth.headerbackend.controller;

import com.neelkanth.headerbackend.entity.Pnr;
import com.neelkanth.headerbackend.repository.PnrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/pnr")
public class PnrController {

    @Autowired
    private PnrRepository pnrRepository;

    @PostMapping
    public Pnr addPnr(@RequestBody Pnr pnr) {
        return pnrRepository.save(pnr);
    }

    @GetMapping
    public List<Pnr> getAllPnrs() {
        return pnrRepository.findAll();
    }
}
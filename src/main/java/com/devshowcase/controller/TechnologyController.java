package com.devshowcase.controller;

import com.devshowcase.dto.TechnologyRequest;
import com.devshowcase.dto.TechnologyResponse;
import com.devshowcase.service.TechnologyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technologies")
@RequiredArgsConstructor
public class TechnologyController {
    private final TechnologyService technologyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TechnologyResponse create(@Valid @RequestBody TechnologyRequest request) {
        return technologyService.create(request);
    }

    @GetMapping
    public List<TechnologyResponse> findAll() {
        return technologyService.findAll();
    }
}

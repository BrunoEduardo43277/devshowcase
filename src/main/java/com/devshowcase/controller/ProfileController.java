package com.devshowcase.controller;

import com.devshowcase.dto.ProfileRequest;
import com.devshowcase.dto.ProfileResponse;
import com.devshowcase.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse create(@Valid @RequestBody ProfileRequest request) {
        return profileService.create(request);
    }

    @GetMapping("/{id}")
    public ProfileResponse findById(@PathVariable Long id) {
        return profileService.findById(id);
    }
}

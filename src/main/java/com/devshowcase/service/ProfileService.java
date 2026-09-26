package com.devshowcase.service;

import com.devshowcase.dto.*;
import com.devshowcase.exception.BusinessException;
import com.devshowcase.exception.ResourceNotFoundException;
import com.devshowcase.model.Profile;
import com.devshowcase.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    @Transactional
    public ProfileResponse create(ProfileRequest request) {
        String email = request.email().trim().toLowerCase();
        if (profileRepository.existsByEmailIgnoreCase(email)) {
            throw new BusinessException("Já existe um perfil com este e-mail");
        }
        Profile profile = Profile.builder()
                .name(request.name().trim())
                .email(email)
                .bio(normalize(request.bio()))
                .build();
        return toResponse(profileRepository.save(profile));
    }

    @Transactional(readOnly = true)
    public ProfileResponse findById(Long id) {
        return toResponse(profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado")));
    }

    private ProfileResponse toResponse(Profile profile) {
        return new ProfileResponse(profile.getId(), profile.getName(), profile.getEmail(), profile.getBio(),
                profile.getProjects().stream()
                        .map(project -> new ProjectSummaryResponse(project.getId(), project.getTitle(), project.getRepositoryUrl()))
                        .toList());
    }

    private String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}

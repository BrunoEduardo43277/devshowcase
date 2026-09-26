package com.devshowcase.service;

import com.devshowcase.dto.*;
import com.devshowcase.exception.ResourceNotFoundException;
import com.devshowcase.model.Profile;
import com.devshowcase.model.Project;
import com.devshowcase.model.Technology;
import com.devshowcase.repository.ProfileRepository;
import com.devshowcase.repository.ProjectRepository;
import com.devshowcase.repository.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProfileRepository profileRepository;
    private final TechnologyRepository technologyRepository;

    @Transactional
    public ProjectResponse create(ProjectRequest request) {
        Profile profile = profileRepository.findById(request.profileId())
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado"));

        Set<Long> requestedIds = request.technologyIds() == null ? Set.of() : request.technologyIds();
        Set<Technology> technologies = new HashSet<>(technologyRepository.findAllById(requestedIds));
        if (technologies.size() != requestedIds.size()) {
            throw new ResourceNotFoundException("Uma ou mais tecnologias não foram encontradas");
        }

        Project project = Project.builder()
                .title(request.title().trim())
                .description(request.description().trim())
                .repositoryUrl(request.repositoryUrl().trim())
                .profile(profile)
                .technologies(technologies)
                .build();
        return toResponse(projectRepository.save(project));
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> findAll() {
        return projectRepository.findAll().stream().map(this::toResponse).toList();
    }

    private ProjectResponse toResponse(Project project) {
        Set<TechnologyResponse> technologies = project.getTechnologies().stream()
                .map(item -> new TechnologyResponse(item.getId(), item.getName()))
                .collect(java.util.stream.Collectors.toCollection(java.util.LinkedHashSet::new));
        List<FeedbackResponse> feedbacks = project.getFeedbacks().stream()
                .map(item -> new FeedbackResponse(item.getId(), item.getAuthorName(), item.getComment(), item.getRating()))
                .toList();
        return new ProjectResponse(project.getId(), project.getTitle(), project.getDescription(),
                project.getRepositoryUrl(), project.getProfile().getId(), project.getProfile().getName(),
                technologies, feedbacks);
    }
}

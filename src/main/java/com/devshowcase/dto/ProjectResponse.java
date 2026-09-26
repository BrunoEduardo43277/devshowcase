package com.devshowcase.dto;

import java.util.List;
import java.util.Set;

public record ProjectResponse(
        Long id,
        String title,
        String description,
        String repositoryUrl,
        Long profileId,
        String profileName,
        Set<TechnologyResponse> technologies,
        List<FeedbackResponse> feedbacks) {
}

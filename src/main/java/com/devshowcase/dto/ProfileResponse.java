package com.devshowcase.dto;

import java.util.List;

public record ProfileResponse(Long id, String name, String email, String bio, List<ProjectSummaryResponse> projects) {
}

package com.devshowcase.dto;

public record FeedbackResponse(Long id, String authorName, String comment, Integer rating) {
}

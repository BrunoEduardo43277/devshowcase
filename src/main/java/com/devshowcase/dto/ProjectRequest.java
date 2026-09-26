package com.devshowcase.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

public record ProjectRequest(
        @NotBlank(message = "O título é obrigatório")
        @Size(max = 150, message = "O título deve ter no máximo 150 caracteres") String title,
        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 2000, message = "A descrição deve ter no máximo 2000 caracteres") String description,
        @NotBlank(message = "A URL do repositório é obrigatória")
        @URL(protocol = "https", message = "Informe uma URL HTTPS válida") String repositoryUrl,
        @NotNull(message = "O profileId é obrigatório") Long profileId,
        Set<Long> technologyIds) {
}

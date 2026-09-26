package com.devshowcase.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TechnologyRequest(
        @NotBlank(message = "O nome da tecnologia é obrigatório")
        @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres") String name) {
}

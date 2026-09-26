package com.devshowcase.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfileRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres") String name,
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Informe um e-mail válido") String email,
        @Size(max = 1000, message = "A bio deve ter no máximo 1000 caracteres") String bio) {
}

package com.escola.sistemaescolar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlunoRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotNull(message = "A idade é obrigatória")
        Integer idade,

        @NotBlank(message = "O telefone é obrigatório")
        String telefone,

        @NotBlank(message = "O e-mail do responsável é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        String emailResponsavel,

        @NotBlank(message = "A matrícula é obrigatória")
        String matricula,

        @NotNull(message = "O ID da turma é obrigatório")
        Long turmaId
) {
}

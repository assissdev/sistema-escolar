package com.escola.sistemaescolar.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NotaRequestDTO(
        @NotNull(message = "O valor da nota é obrigatório")
        @Min(value = 0, message = "A nota mínima é 0")
        @Max(value = 10, message = "A nota máxima é 10")
        Double valor,

        @NotNull(message = "O ID do aluno é obrigatório")
        @Positive(message = "O ID do aluno deve ser um número positivo")
        Long alunoId,

        @NotNull(message = "A disciplina é obrigatória")
        String disciplina
) {}
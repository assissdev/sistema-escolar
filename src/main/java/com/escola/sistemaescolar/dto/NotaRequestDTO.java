package com.escola.sistemaescolar.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotaRequestDTO(

        @NotNull(message = "O valor da nota é obrigatório")
        @Min(value = 0, message = "A nota não pode ser menor que zero")
        @Max(value = 10, message = "A nota não pode ser maior que 10")
        Double valor,

        @NotNull(message = "O ID do aluno é obrigatório")
        Long alunoId,

        @NotBlank(message = "O nome da disciplina é obrigatório")
        String disciplina
) {}
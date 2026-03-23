package com.escola.sistemaescolar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TurmaRequestDTO(
        @NotBlank(message = "O nome da turma é obrigatório e não pode ficar em branco.")
        String nome,

        @NotBlank(message = "A série é obrigatória.")
        @Size(min = 4, message = "A série deve ter no mínimo 4 letras. Ex: '1º Ano'.")
        String serie
){
}

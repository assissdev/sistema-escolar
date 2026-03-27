package com.escola.sistemaescolar.dto;

public record NotaRequestDTO(
        String disciplina,
        Double valor,
        Long alunoId
) {
}

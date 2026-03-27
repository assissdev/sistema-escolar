package com.escola.sistemaescolar.dto;

import com.escola.sistemaescolar.model.Nota;

public record NotaResponseDTO(Long id, String disciplina, Double valor, Long alunoId) {
    // Construtor para facilitar a conversão de Nota para DTO
    public NotaResponseDTO(Nota nota) {
        this(nota.getId(), nota.getDisciplina(), nota.getValor(), nota.getAluno().getId());
    }
}

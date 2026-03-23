package com.escola.sistemaescolar.dto;

import com.escola.sistemaescolar.model.Turma;

public record TurmaResponseDTO(Long id, String nome, String serie) {
    public TurmaResponseDTO(Turma turma) {
        this(turma.getId(), turma.getNome(), turma.getSerie());
    }
}
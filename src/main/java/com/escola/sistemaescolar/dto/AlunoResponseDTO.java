package com.escola.sistemaescolar.dto;

import com.escola.sistemaescolar.model.Aluno;

public record AlunoResponseDTO(
        Long id,
        String nome,
        Integer idade,
        String telefone,
        String emailResponsavel,
        String matricula,
        String nomeTurma
) {
    public AlunoResponseDTO(Aluno aluno) {
        this(
                aluno.getId(),
                aluno.getNome(),
                aluno.getIdade(),
                aluno.getTelefone(),
                aluno.getEmailResponsavel(),
                aluno.getMatricula(),
                aluno.getTurma().getNome()
        );
    }
}
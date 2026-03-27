package com.escola.sistemaescolar.service;

import com.escola.sistemaescolar.dto.NotaRequestDTO;
import com.escola.sistemaescolar.dto.NotaResponseDTO;
import com.escola.sistemaescolar.model.Nota;
import com.escola.sistemaescolar.repository.AlunoRepository;
import com.escola.sistemaescolar.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class NotaService {
    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Transactional
    public NotaResponseDTO lancarNota(NotaRequestDTO dados) {
        // 1. Busca o aluno pelo ID que veio no JSON
        var aluno = alunoRepository.findById(dados.alunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));

        // 2. Cria a entidade Nota e preenche os dados
        var nota = new Nota();
        nota.setDisciplina(dados.disciplina());
        nota.setValor(dados.valor());
        nota.setAluno(aluno);

        // 3. Salva no banco
        notaRepository.save(nota);

        // 4. Retorna o DTO de resposta
        return new NotaResponseDTO(nota);
    }
}

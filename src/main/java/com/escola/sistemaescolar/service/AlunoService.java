package com.escola.sistemaescolar.service;

import com.escola.sistemaescolar.dto.AlunoRequestDTO;
import com.escola.sistemaescolar.dto.AlunoResponseDTO;
import com.escola.sistemaescolar.model.Aluno;
import com.escola.sistemaescolar.model.Turma;
import com.escola.sistemaescolar.repository.AlunoRepository;
import com.escola.sistemaescolar.repository.TurmaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;

    public AlunoService(AlunoRepository alunoRepository, TurmaRepository turmaRepository) {
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
    }
    // --- NOVO MÉTODO BLINDADO COM DTO ---
    public AlunoResponseDTO matricular(AlunoRequestDTO dto) {

        // 1. Vai no banco e procura a turma pelo ID que o Front-end mandou
        Turma turma = turmaRepository.findById(dto.turmaId())
                .orElseThrow(() -> new RuntimeException("Turma não encontrada com o ID: " + dto.turmaId()));

        // 2. Transforma o DTO em uma Entidade de verdade (já injetando a Turma)
        Aluno aluno = new Aluno(
                dto.nome(),
                dto.idade(),
                dto.telefone(),
                dto.emailResponsavel(),
                dto.matricula(),
                turma
        );

        // 3. Salva no banco de dados
        alunoRepository.save(aluno);

        // 4. Converte de volta para DTO para devolver um recibo bonito
        return new AlunoResponseDTO(aluno);
    }
    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }

    public void deletarAluno(Long id) {
        alunoRepository.deleteById(id);
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id).orElse(null);
    }
}


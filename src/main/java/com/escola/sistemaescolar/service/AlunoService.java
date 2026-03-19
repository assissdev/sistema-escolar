package com.escola.sistemaescolar.service;

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
    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }
    public Aluno salvarAluno(Aluno aluno) {

        if (aluno.getTurma() != null && aluno.getTurma().getId() != null) {
            Turma turma = turmaRepository.findById(aluno.getTurma().getId())
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

            aluno.setTurma(turma);
        }
        return alunoRepository.save(aluno);
    }
    public void deletarAluno(Long id) {
        alunoRepository.deleteById(id);
    }
    public Aluno buscarPorId(Long id) {
        // É aqui dentro do Service que a lógica do Repository deve ficar!
        return alunoRepository.findById(id).orElse(null);
    }
}


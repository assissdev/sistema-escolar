package com.escola.sistemaescolar.service;

import com.escola.sistemaescolar.model.Aluno;
import com.escola.sistemaescolar.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public List<Aluno> listarAlunos() {
        return repository.findAll();
    }

    public Aluno salvarAluno(Aluno aluno) {
        return repository.save(aluno);
    }

    public void deletarAluno(Long id) {
        repository.deleteById(id);
    }
}


package com.escola.sistemaescolar.service;

import com.escola.sistemaescolar.model.Turma;
import com.escola.sistemaescolar.repository.TurmaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaService {
    private final TurmaRepository repository;

    public TurmaService(TurmaRepository repository) {
        this.repository = repository;
    }

    public List<Turma> listarTurmas() {
        return repository.findAll();
    }

    public Turma buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Turma salvarTurma(Turma turma) {
        return repository.save(turma);
    }

    public void deletarTurma(Long id) {
        repository.deleteById(id);
    }
}


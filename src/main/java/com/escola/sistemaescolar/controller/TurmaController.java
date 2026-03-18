package com.escola.sistemaescolar.controller;

import com.escola.sistemaescolar.model.Aluno;
import com.escola.sistemaescolar.model.Turma;
import com.escola.sistemaescolar.repository.TurmaRepository;
import com.escola.sistemaescolar.repository.AlunoRepository;
import com.escola.sistemaescolar.repository.TurmaRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/turmas")

public class TurmaController {
    private final TurmaRepository turmaRepository;
    private final AlunoRepository alunoRepository;

    public TurmaController(TurmaRepository turmaRepository, AlunoRepository alunoRepository) {
        this.turmaRepository = turmaRepository;
        this.alunoRepository = alunoRepository;
    }

    @GetMapping
    public List<Turma> listar() {
        return turmaRepository.findAll();
    }

    @PostMapping
    public Turma salvar(@RequestBody Turma turma) {
        return turmaRepository.save(turma);
    }

    @GetMapping("/teste/{id}")
    public List<Aluno> listarAlunosdaTurma(@PathVariable Long id){
        return alunoRepository.findByTurmaId(id);
    }
}

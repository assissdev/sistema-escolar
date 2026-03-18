package com.escola.sistemaescolar.controller;

import com.escola.sistemaescolar.repository.AlunoRepository;
import com.escola.sistemaescolar.model.Aluno;
import com.escola.sistemaescolar.repository.AlunoRepository;
import com.escola.sistemaescolar.service.AlunoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;
    private final AlunoRepository alunoRepository;

    public AlunoController(AlunoService service, AlunoRepository alunoRepository) {
        this.service = service;
        this.alunoRepository = alunoRepository;
    }

    @GetMapping
    public List<Aluno> listar() {
        return service.listarAlunos();
    }

    @PostMapping
    public Aluno salvar(@RequestBody Aluno aluno) {
        return service.salvarAluno(aluno);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletarAluno(id);
    }

    @GetMapping("/{id}")
    public Aluno buscarPorId(@PathVariable Long id){
        return alunoRepository.findById(id).orElse(null);
    }
}

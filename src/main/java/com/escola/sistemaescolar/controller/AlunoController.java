package com.escola.sistemaescolar.controller;

import com.escola.sistemaescolar.dto.AlunoRequestDTO;
import com.escola.sistemaescolar.dto.AlunoResponseDTO;
import com.escola.sistemaescolar.model.Aluno;
import com.escola.sistemaescolar.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/alunos")

    public AlunoController {
    // 1. Apenas o Service entra aqui! O Controller só dá ordens para ele.
    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
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

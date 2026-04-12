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
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AlunoResponseDTO> matricular(@RequestBody @Valid AlunoRequestDTO dto, UriComponentsBuilder uriBuilder) {
        var alunoSalvo = service.matricular(dto);

        var uri = uriBuilder.path("/alunos/{id}").buildAndExpand(alunoSalvo.id()).toUri();

        return ResponseEntity.created(uri).body(alunoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> listar() {
        return ResponseEntity.ok(service.listarAlunos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarAluno(id);
        return ResponseEntity.noContent().build();
    }
}

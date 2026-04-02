package com.escola.sistemaescolar.controller;

import com.escola.sistemaescolar.dto.NotaRequestDTO;
import com.escola.sistemaescolar.dto.NotaResponseDTO;
import com.escola.sistemaescolar.service.NotaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotaController {

    private final NotaService service;

    // Injeção de dependência via construtor
    public NotaController(NotaService service) {
        this.service = service;
    }

    // --- Validação adicionada (@Valid) ---
    @PostMapping
    public ResponseEntity<NotaResponseDTO> lancar(@RequestBody @Valid NotaRequestDTO dados, UriComponentsBuilder uriBuilder) {
        var nota = service.lancarNota(dados);
        var uri = uriBuilder.path("/notas/{id}").buildAndExpand(nota.id()).toUri();

        return ResponseEntity.created(uri).body(nota);
    }

    // --- Método atualizado para retornar Page e receber Pageable ---
    @GetMapping
    public ResponseEntity<Page<NotaResponseDTO>> listar(@PageableDefault(size = 10, sort = {"valor"}) Pageable paginacao) {
        var notas = service.listarTodas(paginacao);
        return ResponseEntity.ok(notas);
    }
    @GetMapping("/{id}")
    public ResponseEntity<NotaResponseDTO> detalhar(@PathVariable Long id) {
        var nota = service.buscarPorId(id);
        return ResponseEntity.ok(nota);
    }

    // --- Validação adicionada (@Valid) ---
    @PutMapping("/{id}")
    public ResponseEntity<NotaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid NotaRequestDTO dados) {
        var notaAtualizada = service.atualizarNota(id, dados);
        return ResponseEntity.ok(notaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarNota(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<NotaResponseDTO>> listarPorAluno(@PathVariable Long alunoId) {
        var boletim = service.listarPorAluno(alunoId);
        return ResponseEntity.ok(boletim);
    }
}
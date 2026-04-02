package com.escola.sistemaescolar.controller;

import com.escola.sistemaescolar.dto.NotaRequestDTO;
import com.escola.sistemaescolar.dto.NotaResponseDTO;
import com.escola.sistemaescolar.service.NotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotaController {

    private final NotaService service;

    // Injeção de dependência via construtor (Melhor prática!)
    public NotaController(NotaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<NotaResponseDTO> lancar(@RequestBody NotaRequestDTO dados, UriComponentsBuilder uriBuilder) {
        var nota = service.lancarNota(dados);
        var uri = uriBuilder.path("/notas/{id}").buildAndExpand(nota.id()).toUri();

        return ResponseEntity.created(uri).body(nota);
    }

    // --- NOVO MÉTODO PARA O GET ---
    @GetMapping
    public ResponseEntity<List<NotaResponseDTO>> listar() {
        var notas = service.listarTodas();
        return ResponseEntity.ok(notas);
    }
    // --- PASSO 1: GET por ID ---
    @GetMapping("/{id}")
    public ResponseEntity<NotaResponseDTO> detalhar(@PathVariable Long id) {
        var nota = service.buscarPorId(id);
        return ResponseEntity.ok(nota);
    }
    // --- PASSO 2: PUT para Atualizar ---
    @PutMapping("/{id}")
    public ResponseEntity<NotaResponseDTO> atualizar(@PathVariable Long id, @RequestBody NotaRequestDTO dados) {
        var notaAtualizada = service.atualizarNota(id, dados);
        return ResponseEntity.ok(notaAtualizada);
    }
}
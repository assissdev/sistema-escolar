package com.escola.sistemaescolar.controller;

import com.escola.sistemaescolar.dto.NotaRequestDTO;
import com.escola.sistemaescolar.dto.NotaResponseDTO;
import com.escola.sistemaescolar.service.NotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Notas", description = "Operações relacionadas ao lançamento e consulta de notas dos alunos")
public class NotaController {

    private final NotaService service;

    public NotaController(NotaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Lançar nova nota", description = "Cadastra uma nova nota para um aluno existente no sistema.")
    public ResponseEntity<NotaResponseDTO> lancar(@RequestBody @Valid NotaRequestDTO dados, UriComponentsBuilder uriBuilder) {
        var nota = service.lancarNota(dados);
        var uri = uriBuilder.path("/notas/{id}").buildAndExpand(nota.id()).toUri();

        return ResponseEntity.created(uri).body(nota);
    }

    @GetMapping
    @Operation(summary = "Listar todas as notas", description = "Retorna uma lista paginada com todas as notas cadastradas (Padrão: 10 itens por página).")
    public ResponseEntity<Page<NotaResponseDTO>> listar(@PageableDefault(size = 10, sort = {"valor"}) Pageable paginacao) {
        var notas = service.listarTodas(paginacao);
        return ResponseEntity.ok(notas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar nota por ID", description = "Busca os detalhes de uma nota específica informando o seu ID.")
    public ResponseEntity<NotaResponseDTO> detalhar(@PathVariable Long id) {
        var nota = service.buscarPorId(id);
        return ResponseEntity.ok(nota);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar nota", description = "Atualiza o valor ou a disciplina de uma nota já existente informando o ID.")
    public ResponseEntity<NotaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid NotaRequestDTO dados) {
        var notaAtualizada = service.atualizarNota(id, dados);
        return ResponseEntity.ok(notaAtualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir nota", description = "Remove permanentemente uma nota do sistema a partir do seu ID.")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarNota(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/aluno/{alunoId}")
    @Operation(summary = "Gerar boletim do aluno", description = "Retorna uma lista com todas as notas vinculadas a um aluno específico.")
    public ResponseEntity<List<NotaResponseDTO>> listarPorAluno(@PathVariable Long alunoId) {
        var boletim = service.listarPorAluno(alunoId);
        return ResponseEntity.ok(boletim);
    }
}
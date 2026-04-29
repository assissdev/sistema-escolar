package com.escola.sistemaescolar.controller;

import com.escola.sistemaescolar.dto.BoletimResponseDTO;
import com.escola.sistemaescolar.dto.NotaRequestDTO;
import com.escola.sistemaescolar.dto.NotaResponseDTO;
import com.escola.sistemaescolar.service.NotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/notas")
@Tag(name = "Notas", description = "Operações relacionadas ao lançamento e consulta de notas dos alunos")
@SecurityRequirement(name = "bearer-key") // Cadeado ativado e exigindo o token!
public class NotaController {

    private final NotaService service;

    public NotaController(NotaService service) {
        this.service = service;
    }

    // --- BLOQUEADO: Apenas Professor ou Diretor ---
    @PostMapping
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('DIRETOR')")
    @Operation(summary = "Lançar nova nota", description = "Cadastra uma nova nota para um aluno existente no sistema.")
    public ResponseEntity<NotaResponseDTO> lancar(@RequestBody @Valid NotaRequestDTO dados, UriComponentsBuilder uriBuilder) {
        var nota = service.lancarNota(dados);
        var uri = uriBuilder.path("/notas/{id}").buildAndExpand(nota.id()).toUri();

        return ResponseEntity.created(uri).body(nota);
    }

    // --- LIVRE: Qualquer usuário logado pode ver ---
    @GetMapping
    @Operation(summary = "Listar todas as notas", description = "Retorna uma lista paginada com todas as notas cadastradas (Padrão: 10 itens por página).")
    public ResponseEntity<Page<NotaResponseDTO>> listar(@PageableDefault(size = 10, sort = {"valor"}) Pageable paginacao) {
        var notas = service.listarTodas(paginacao);
        return ResponseEntity.ok(notas);
    }

    // --- LIVRE: Qualquer usuário logado pode ver ---
    @GetMapping("/{id}")
    @Operation(summary = "Buscar nota por ID", description = "Busca os detalhes de uma nota específica informando o seu ID.")
    public ResponseEntity<NotaResponseDTO> detalhar(@PathVariable Long id) {
        var nota = service.buscarPorId(id);
        return ResponseEntity.ok(nota);
    }

    // --- BLOQUEADO: Apenas Professor ou Diretor ---
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('DIRETOR')")
    @Operation(summary = "Atualizar nota", description = "Atualiza o valor ou a disciplina de uma nota já existente informando o ID.")
    public ResponseEntity<NotaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid NotaRequestDTO dados) {
        var notaAtualizada = service.atualizarNota(id, dados);
        return ResponseEntity.ok(notaAtualizada);
    }

    // --- BLOQUEADO: Apenas Professor ou Diretor ---
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('DIRETOR')")
    @Operation(summary = "Excluir nota", description = "Remove permanentemente uma nota do sistema a partir do seu ID.")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarNota(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/aluno/{alunoId}")
    @Operation(summary = "Gerar boletim do aluno", description = "Retorna as notas, a média final e o status (Aprovado/Reprovado) de um aluno.")
    public ResponseEntity<BoletimResponseDTO> gerarBoletim(@PathVariable Long alunoId) {
        var boletim = service.gerarBoletim(alunoId);
        return ResponseEntity.ok(boletim);
    }
}
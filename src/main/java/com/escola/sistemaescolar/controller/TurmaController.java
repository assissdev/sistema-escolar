package com.escola.sistemaescolar.controller;

import com.escola.sistemaescolar.dto.TurmaRequestDTO;
import com.escola.sistemaescolar.dto.TurmaResponseDTO;
import com.escola.sistemaescolar.model.Turma;
import com.escola.sistemaescolar.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    // Injetamos apenas o Service da Turma (nada de repositórios perdidos aqui!)
    private final TurmaService service;

    public TurmaController(TurmaService service) {
        this.service = service;
    }

    @GetMapping
    public Page<TurmaResponseDTO> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return service.listarTurmas(paginacao);
    }

    @PostMapping
    public TurmaResponseDTO salvar(@Valid @RequestBody TurmaRequestDTO dto) {
        return service.salvarTurma(dto);
    }

    @GetMapping("/{id}")
    public Turma buscarPorId(@PathVariable Long id) {
        // Agora retorna uma Turma corretamente!
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletarTurma(id);
    }
    @PutMapping("/{id}")
    public TurmaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody TurmaRequestDTO dto) {
        return service.atualizarTurma(id, dto);
    }
}

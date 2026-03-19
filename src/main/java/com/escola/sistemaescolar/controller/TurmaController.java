package com.escola.sistemaescolar.controller;

import com.escola.sistemaescolar.model.Turma;
import com.escola.sistemaescolar.service.TurmaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    // Injetamos apenas o Service da Turma (nada de repositórios perdidos aqui!)
    private final TurmaService service;

    public TurmaController(TurmaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Turma> listar() {
        return service.listarTurmas();
    }

    @PostMapping
    public Turma salvar(@RequestBody Turma turma) {
        return service.salvarTurma(turma);
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
}

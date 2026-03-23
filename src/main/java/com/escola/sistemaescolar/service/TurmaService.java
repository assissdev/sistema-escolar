package com.escola.sistemaescolar.service;

import com.escola.sistemaescolar.dto.TurmaRequestDTO;
import com.escola.sistemaescolar.dto.TurmaResponseDTO;
import com.escola.sistemaescolar.model.Turma;
import com.escola.sistemaescolar.repository.TurmaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaService {
    private final TurmaRepository repository;

    public TurmaService(TurmaRepository repository) {
        this.repository = repository;
    }

    public List<TurmaResponseDTO> listarTurmas() {
        // Pega todas as turmas do banco e transforma cada uma em um TurmaResponseDTO
        return repository.findAll().stream()
                .map(TurmaResponseDTO::new)
                .toList();
    }

    public Turma buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public TurmaResponseDTO salvarTurma(TurmaRequestDTO dto) {
        // 1. Pega os dados do DTO e cria uma Turma de verdade
        Turma novaTurma = new Turma(dto.nome(), dto.serie());

        // 2. Salva no banco de dados
        Turma turmaSalva = repository.save(novaTurma);

        // 3. Devolve como DTO
        return new TurmaResponseDTO(turmaSalva);
    }

    public void deletarTurma(Long id) {
        repository.deleteById(id);
    }
}


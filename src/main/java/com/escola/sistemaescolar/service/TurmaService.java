package com.escola.sistemaescolar.service;

import com.escola.sistemaescolar.dto.TurmaRequestDTO;
import com.escola.sistemaescolar.dto.TurmaResponseDTO;
import com.escola.sistemaescolar.model.Turma;
import com.escola.sistemaescolar.repository.TurmaRepository;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class TurmaService {
    private final TurmaRepository repository;

    public TurmaService(TurmaRepository repository) {
        this.repository = repository;
    }

    public Page<TurmaResponseDTO> listarTurmas(Pageable paginacao) {
        // Pega todas as turmas do banco e transforma cada uma em um TurmaResponseDTO
        return repository.findAll(paginacao).map(TurmaResponseDTO::new);
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

    public TurmaResponseDTO atualizarTurma(Long id, TurmaRequestDTO dto) {
        // 1. Busca a turma no banco. Se não achar, lança um erro específico (EntityNotFoundException)
        Turma turma = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        // 2. Atualiza os dados com o que veio do DTO
        turma.setNome(dto.nome());
        turma.setSerie(dto.serie());

        // 3. Salva no banco e devolve como ResponseDTO
        Turma turmaAtualizada = repository.save(turma);
        return new TurmaResponseDTO(turmaAtualizada);
    }
}

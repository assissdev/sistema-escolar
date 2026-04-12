package com.escola.sistemaescolar.service;

import com.escola.sistemaescolar.dto.NotaRequestDTO;
import com.escola.sistemaescolar.dto.NotaResponseDTO;
import com.escola.sistemaescolar.model.Nota;
import com.escola.sistemaescolar.repository.AlunoRepository;
import com.escola.sistemaescolar.repository.NotaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotaService {

    private final NotaRepository notaRepository;
    private final AlunoRepository alunoRepository;

    public NotaService(NotaRepository notaRepository, AlunoRepository alunoRepository) {
        this.notaRepository = notaRepository;
        this.alunoRepository = alunoRepository;
    }

    @Transactional
    public NotaResponseDTO lancarNota(NotaRequestDTO dados) {
        // Busca o aluno ou lança erro 404 caso não exista
        var aluno = alunoRepository.findById(dados.alunoId())
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado com o ID: " + dados.alunoId()));

        var nota = new Nota();
        nota.setDisciplina(dados.disciplina());
        nota.setValor(dados.valor());
        nota.setAluno(aluno);

        notaRepository.save(nota);

        return new NotaResponseDTO(nota);
    }

    public Page<NotaResponseDTO> listarTodas(Pageable paginacao) {
        return notaRepository.findAll(paginacao)
                .map(NotaResponseDTO::new);
    }

    public NotaResponseDTO buscarPorId(Long id) {
        var nota = notaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota não encontrada com o ID: " + id));

        return new NotaResponseDTO(nota);
    }

    @Transactional
    public NotaResponseDTO atualizarNota(Long id, NotaRequestDTO dados) {
        var nota = notaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota não encontrada para edição!"));

        nota.setValor(dados.valor());
        nota.setDisciplina(dados.disciplina());

        // No JPA, quando alteramos um objeto dentro de um @Transactional,
        // a atualização no banco ocorre automaticamente ao final do método.
        return new NotaResponseDTO(nota);
    }

    @Transactional
    public void deletarNota(Long id) {
        if (!notaRepository.existsById(id)) {
            throw new EntityNotFoundException("Nota não encontrada para exclusão!");
        }
        notaRepository.deleteById(id);
    }

    public List<NotaResponseDTO> listarPorAluno(Long alunoId) {
        if (!alunoRepository.existsById(alunoId)) {
            throw new EntityNotFoundException("Aluno não encontrado para gerar o boletim!");
        }

        return notaRepository.findByAlunoId(alunoId)
                .stream()
                .map(NotaResponseDTO::new)
                .toList();
    }
}
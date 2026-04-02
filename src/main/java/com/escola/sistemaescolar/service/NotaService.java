package com.escola.sistemaescolar.service;

import com.escola.sistemaescolar.dto.NotaRequestDTO;
import com.escola.sistemaescolar.dto.NotaResponseDTO;
import com.escola.sistemaescolar.model.Nota;
import com.escola.sistemaescolar.repository.AlunoRepository;
import com.escola.sistemaescolar.repository.NotaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotaService {

    private final NotaRepository notaRepository;
    private final AlunoRepository alunoRepository;

    // Injeção de dependência via construtor (Melhor prática!)
    public NotaService(NotaRepository notaRepository, AlunoRepository alunoRepository) {
        this.notaRepository = notaRepository;
        this.alunoRepository = alunoRepository;
    }

    @Transactional
    public NotaResponseDTO lancarNota(NotaRequestDTO dados) {
        // 1. Busca o aluno pelo ID que veio no JSON
        var aluno = alunoRepository.findById(dados.alunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));

        // 2. Cria a entidade Nota e preenche os dados
        var nota = new Nota();
        nota.setDisciplina(dados.disciplina());
        nota.setValor(dados.valor());
        nota.setAluno(aluno);

        // 3. Salva no banco
        notaRepository.save(nota);

        // 4. Retorna o DTO de resposta
        return new NotaResponseDTO(nota);
    }

    // --- MÉTODO REFATORADO PARA PAGINAÇÃO ---
    // Aceita Pageable e retorna Page (Resolve os Erros 1 e 2 do Controller)
    public Page<NotaResponseDTO> listarTodas(Pageable paginacao) {
        return notaRepository.findAll(paginacao)
                .map(NotaResponseDTO::new);
    }

    public NotaResponseDTO buscarPorId(Long id) {
        var nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada!"));

        return new NotaResponseDTO(nota);
    }

    @Transactional
    public NotaResponseDTO atualizarNota(Long id, NotaRequestDTO dados) {
        var nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada para edição!"));

        nota.setValor(dados.valor());
        nota.setDisciplina(dados.disciplina());

        return new NotaResponseDTO(nota);
    }

    @Transactional
    public void deletarNota(Long id) {
        if (!notaRepository.existsById(id)) {
            throw new RuntimeException("Nota não encontrada para exclusão!");
        }
        notaRepository.deleteById(id);
    }

    public List<NotaResponseDTO> listarPorAluno(Long alunoId) {
        if (!alunoRepository.existsById(alunoId)) {
            throw new RuntimeException("Aluno não encontrado para gerar o boletim!");
        }

        return notaRepository.findByAlunoId(alunoId)
                .stream()
                .map(NotaResponseDTO::new)
                .toList();
    }
}
package com.escola.sistemaescolar.service;

import com.escola.sistemaescolar.dto.BoletimResponseDTO;
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

    // --- NOSSO NOVO MÉTODO INTELIGENTE ---
    public BoletimResponseDTO gerarBoletim(Long alunoId) {
        // 1. Verifica se o aluno existe
        if (!alunoRepository.existsById(alunoId)) {
            throw new EntityNotFoundException("Aluno não encontrado para gerar o boletim!");
        }

        // 2. Busca as notas no banco de dados
        var notasDoAluno = notaRepository.findByAlunoId(alunoId);

        // 3. Converte as notas para DTO para mostrar na tela
        List<NotaResponseDTO> notasDTO = notasDoAluno.stream()
                .map(NotaResponseDTO::new)
                .toList();

        // 4. Calcula a média matemática
        double media = notasDoAluno.stream()
                .mapToDouble(Nota::getValor)
                .average()
                .orElse(0.0);

        // 5. Aplica a Regra de Negócio (Média >= 7)
        String status = media >= 7.0 ? "APROVADO" : "REPROVADO";

        // 6. Retorna o boletim completo
        return new BoletimResponseDTO(alunoId, notasDTO, media, status);
    }
}
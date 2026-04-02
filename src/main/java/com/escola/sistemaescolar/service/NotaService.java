package com.escola.sistemaescolar.service;

import com.escola.sistemaescolar.dto.NotaRequestDTO;
import com.escola.sistemaescolar.dto.NotaResponseDTO;
import com.escola.sistemaescolar.model.Nota;
import com.escola.sistemaescolar.repository.AlunoRepository;
import com.escola.sistemaescolar.repository.NotaRepository;
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

    // --- NOVO MÉTODO PARA O GET ---
    public List<NotaResponseDTO> listarTodas() {
        // Busca todas as notas, transforma em DTO e devolve como uma lista
        return notaRepository.findAll()
                .stream()
                .map(NotaResponseDTO::new)
                .toList();
    }

    // --- PASSO 1: Buscar por ID ---
    public NotaResponseDTO buscarPorId(Long id) { // Verifique se o seu ID é Long, se for UUID mude aqui!
        var nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada!"));

        return new NotaResponseDTO(nota);
    }
    // --- PASSO 2: Editar Nota ---
    @Transactional
    public NotaResponseDTO atualizarNota(Long id, NotaRequestDTO dados) {
        // Busca a nota existente
        var nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada para edição!"));

        // Atualiza os dados permitidos (geralmente só valor e disciplina)
        nota.setValor(dados.valor());
        nota.setDisciplina(dados.disciplina());

        // Retorna a nota atualizada
        return new NotaResponseDTO(nota);
    }
}
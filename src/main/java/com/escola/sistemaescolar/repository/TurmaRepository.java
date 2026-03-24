package com.escola.sistemaescolar.repository;

import com.escola.sistemaescolar.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    // A mágica acontece aqui!
    // "Containing" funciona como o LIKE do banco (busca pedaços da palavra)
    // "IgnoreCase" faz ignorar letras maiúsculas ou minúsculas.
    List<Turma> findByNomeContainingIgnoreCase(String nome);
}

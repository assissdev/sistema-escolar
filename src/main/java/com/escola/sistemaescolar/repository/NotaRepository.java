package com.escola.sistemaescolar.repository;

import com.escola.sistemaescolar.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    // O Spring cria o código SQL automaticamente só de ler o nome do método!
    List<Nota> findByAlunoId(Long alunoId);
}

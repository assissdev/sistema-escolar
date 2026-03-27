package com.escola.sistemaescolar.repository;

import com.escola.sistemaescolar.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Long> {
}

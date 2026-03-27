package com.escola.sistemaescolar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Nota")
@Table(name = "notas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String disciplina;
    private Double valor;

    // Relacionamento: Várias notas pertencem a Um aluno
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
}

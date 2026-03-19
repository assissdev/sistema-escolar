package com.escola.sistemaescolar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "alunos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer idade;
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;

    // Construtor vazio (obrigatório para o Spring/JPA)
    public Aluno() {}

    // Construtor preenchido (sem o ID, pois ele é gerado automaticamente pelo banco)
    public Aluno(String nome, Integer idade, String telefone, Turma turma) {
        this.nome = nome;
        this.idade = idade;
        this.telefone = telefone;
        this.turma = turma;
    }

    // --- Getters e Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
}

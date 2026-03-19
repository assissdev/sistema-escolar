package com.escola.sistemaescolar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String serie;

    @OneToMany(mappedBy = "turma")
    @JsonIgnore
    private List<Aluno> alunos;

    // Construtor vazio (obrigatório para o Spring/JPA)
    public Turma() {}

    // Construtor preenchido (sem o ID, pois ele é gerado automaticamente pelo banco)
    public Turma(String nome, String serie) {
        this.nome = nome;
        this.serie = serie;
    }

    // --- Getters e Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }

    // Faltava apenas os getters e setters da lista de alunos!
    public List<Aluno> getAlunos() { return alunos; }
    public void setAlunos(List<Aluno> alunos) { this.alunos = alunos; }
}
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

    public Turma() {
    }
    public Turma(Long id, String nome, String serie) {
       this.id = id;
       this.nome = nome;
       this.serie = serie;
    }
    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getSerie() {
        return serie;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setSerie(String serie) {
        this.serie = serie;
    }
}

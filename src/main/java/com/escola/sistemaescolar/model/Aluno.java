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

    public Aluno() {
    }

    public Aluno(Long id, String nome, Integer idade, String telefone){
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.telefone = telefone;
    }

    public Long getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}


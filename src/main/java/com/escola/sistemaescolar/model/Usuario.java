package com.escola.sistemaescolar.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String senha;

    // --- NOVO CAMPO DE PERFIL ---
    @Enumerated(EnumType.STRING) // Salva no banco como texto (ex: "ALUNO") em vez de número
    private TipoUsuario role;

    public Usuario() {}

    public Usuario(String login, String senha, TipoUsuario role) {
        this.login = login;
        this.senha = senha;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public TipoUsuario getRole() { return role; } // Novo getter

    // --- MÉTODOS OBRIGATÓRIOS DO SPRING SECURITY ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Agora é dinâmico! O Spring exige que a gente coloque "ROLE_" antes do nome.
        // Se a role for ALUNO, o Spring vai ler "ROLE_ALUNO".
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}


package com.escola.sistemaescolar.repository;

import com.escola.sistemaescolar.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Método importantíssimo para o Spring achar o usuário pelo login (email)
    UserDetails findByLogin(String login);
}

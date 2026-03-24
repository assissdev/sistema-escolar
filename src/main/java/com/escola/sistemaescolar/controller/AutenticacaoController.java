package com.escola.sistemaescolar.controller;

import com.escola.sistemaescolar.dto.AutenticacaoDTO;
import com.escola.sistemaescolar.dto.TokenJWTDTO;
import com.escola.sistemaescolar.infra.security.TokenService;
import com.escola.sistemaescolar.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    private final AuthenticationManager manager;
    private final TokenService tokenService;

    // Injetamos as ferramentas de segurança que o Spring gerencia para nós
    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid AutenticacaoDTO dto) {
        // 1. Cria um "ticket" com o login e a senha que o usuário digitou
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());

        // 2. O manager vai no banco, busca o usuário e confere se a senha bate (faz a mágica da criptografia)
        var authentication = manager.authenticate(authenticationToken);

        // 3. Se a senha bater, nós geramos o Token JWT
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        // 4. Devolvemos o Token formatado bonitinho no nosso DTO
        return ResponseEntity.ok(new TokenJWTDTO(tokenJWT));
    }
}

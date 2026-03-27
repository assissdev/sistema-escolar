package com.escola.sistemaescolar.infra.security;

import com.escola.sistemaescolar.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository repository;

    public SecurityFilter(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // --- NOVO ESPIÃO ---
        System.out.println("ESPIÃO - Alguém tentou acessar a URL: " + request.getRequestURI());
        // -------------------

        // 1. Pega o token do cabeçalho da requisição
        var tokenJWT = recuperarToken(request);

        // --- NOSSOS ESPIÕES ---
        System.out.println("ESPIÃO 1 - Passou pelo Filtro!");
        System.out.println("ESPIÃO 2 - Token lido: " + tokenJWT);
        // --

        if (tokenJWT != null) {
            // 2. Lê o token e descobre quem é o dono (o e-mail do diretor)
            var subject = tokenService.getSubject(tokenJWT);
            System.out.println("ESPIÃO 3 - Dono do Token: " + subject); // Mais um espião aqui!

            // 3. Busca o diretor no banco de dados
            var usuario = repository.findByLogin(subject);

            // 4. Cria o passe livre e avisa o Spring Security que o usuário está logado!
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 5. Continua o fluxo da requisição (se não tinha token, o Spring bloqueia logo depois daqui)
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}

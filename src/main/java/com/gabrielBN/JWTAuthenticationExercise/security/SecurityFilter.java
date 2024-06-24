package com.gabrielBN.JWTAuthenticationExercise.security;

import com.gabrielBN.JWTAuthenticationExercise.repository.UsuarioRepository;
import com.gabrielBN.JWTAuthenticationExercise.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//filtro personalizado que é chamado antes dos filtros do Spring Security para autenticar por token.
//Essa classe extende a classe OncePerRequestFilter para utilizar o método doFilterInternal
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService service;

    @Autowired
    private UsuarioRepository repository;

    //é necessário sobrescrever o método doFilterInternal com o nosso filtro personalizado baseado em tokens
    //JWT
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //guardando o token
        var token = recuperaToken(request);
        //verificando token
        if(token != null) {
            //guardando login do usuário
            var subject = service.getSubject(token);
            //localizando usuário pelo login no banco de dados
            var usuario = repository.findByLogin(subject);

            //criando objeto do tipo authentication com o usuário e o nível de autoridade para posteriormente
            //defini-lo como autenticado e liberar a requisição
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            //liberando o acesso através do método estático .setAuthentication()
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    //método utilizado para extrair o token do header authorization
    private String recuperaToken(HttpServletRequest request) {
        //guardando conteúdo do header authorization
        var authorizationHeader = request.getHeader("Authorization");
        //verificando o conteúdo
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        //caso vazio, retornamos null
        return null;
    }
}

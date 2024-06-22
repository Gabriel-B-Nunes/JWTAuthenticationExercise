package com.gabrielBN.JWTAuthenticationExercise.controller;

import com.gabrielBN.JWTAuthenticationExercise.dto.AutenticacaoDTO;
import com.gabrielBN.JWTAuthenticationExercise.dto.TokenDTO;
import com.gabrielBN.JWTAuthenticationExercise.models.Usuario;
import com.gabrielBN.JWTAuthenticationExercise.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService service;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid AutenticacaoDTO autenticacaoDTO) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(autenticacaoDTO.login(), autenticacaoDTO.senha());
        Authentication authentication = manager.authenticate(token);

        var tokenJWT = new TokenDTO(service.gerarToken((Usuario) authentication.getPrincipal()));

        return ResponseEntity.ok(tokenJWT);
    }
}

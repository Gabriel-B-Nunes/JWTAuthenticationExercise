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

    //método responsável por gerar o token que o usuário precisará informar nas demais requisições
    //essa requisição não necessita de token no header authorization, pois é aqui que o token será
    //criado
    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid AutenticacaoDTO autenticacaoDTO) {
        //criamos um objeto UsernamePasswordAuthenticationToken, que posteriormente é usado para validar
        //os dados de login e senha enviados pelo usuário
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(autenticacaoDTO.login(), autenticacaoDTO.senha());
        //os dados de login e senha são validados pelo AuthenticationManager e são guardados em um objeto
        //Authentication
        Authentication authentication = manager.authenticate(token);

        //após validação dos dados de login e senha é gerado um token JWT
        var tokenJWT = new TokenDTO(service.gerarToken((Usuario) authentication.getPrincipal()));

        //o token é retornado por um ResponseEntity
        return ResponseEntity.ok(tokenJWT);
    }
}

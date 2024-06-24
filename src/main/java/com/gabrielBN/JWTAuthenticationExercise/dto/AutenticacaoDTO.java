package com.gabrielBN.JWTAuthenticationExercise.dto;

//record utilizado para coletar os dados de login e senha enviados na requisição post
public record AutenticacaoDTO(String login, String senha) {
}

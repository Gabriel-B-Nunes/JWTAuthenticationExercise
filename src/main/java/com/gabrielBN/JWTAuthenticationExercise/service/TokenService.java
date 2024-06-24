package com.gabrielBN.JWTAuthenticationExercise.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gabrielBN.JWTAuthenticationExercise.models.Usuario;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

//classe para geração e validação de tokens JWT
@Service
public class TokenService {

    //definindo o segredo utilizado para geração dos tokens. O ideal é armazenar essa variável como váriavel
    //de ambiente no servidor da aplicação
    private final String secret = "${jwt.authentication.exercise.secret}";

    public String gerarToken(Usuario usuario) {
        try {
            //definindo o tipo do token para HMAC256
            Algorithm algorithm = Algorithm.HMAC256(secret);
            //definindo o proprietário do token (usuário), a data de expiração do token e retornando
            return JWT.create().withIssuer("JWTAuthenticationExercise").withSubject(usuario.getLogin()).withExpiresAt(DataExpiracao()).sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar o token JWT", e);
        }
    }

    //método que calcula a hora de expiração do token
    private Instant DataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    //método utilizado para verificar quem é o usuário que gerou o token, utilizado posteriormente para
    //autenticar e liberar a requisição
    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("JWTAuthenticationExercise")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Token JWT inválido ou expirado", e);
        }
    }
}

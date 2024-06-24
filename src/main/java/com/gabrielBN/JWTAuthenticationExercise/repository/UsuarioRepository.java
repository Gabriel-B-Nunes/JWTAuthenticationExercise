package com.gabrielBN.JWTAuthenticationExercise.repository;

import com.gabrielBN.JWTAuthenticationExercise.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

//repositório que será utilizado para consultar o login e senha do usuário no banco de dados
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String login);
}

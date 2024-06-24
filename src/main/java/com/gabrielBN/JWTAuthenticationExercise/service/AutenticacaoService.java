package com.gabrielBN.JWTAuthenticationExercise.service;

import com.gabrielBN.JWTAuthenticationExercise.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//essa classe é utilizada pelo AuthenticationManager para localizar o usuário no banco de dados
@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    //definindo como é feita a busca pelo usuário no bando de dados
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByLogin(login);
    }
}

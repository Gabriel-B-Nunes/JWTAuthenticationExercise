package com.gabrielBN.JWTAuthenticationExercise.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//classe de configuração do módulo Security do Spring
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter filter;

    //a principal classe de configuração do Spring Security retorna um SecurityFilterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //desabilitando segurança contra ataques CSRF pois a autenticação por token JWT é uma solução
        //contra esse tipo de ataque
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        //definindo o tipo de sessão para stateless, isso é, não usaremos um formulário de
                        //autenticação de login e senha em uma página web
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        //definindo que requisições feitas /login não devem ser autenticadas
                        .requestMatchers("/login").permitAll()
                        //requisições feitas para qualquer outro endpoint devem ser autenticadas
                        .anyRequest().authenticated())
                //para validar os tokens precisamos criar um filtro e ele precisa ser chamado antes dos
                //filtros do Spring Security. Caso não fizessemos isso não conseguiríamos validar os tokens
                //e autenticar o usuário
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                //devolvendo erro 401 unauthorized para tentativas de requisição sem token
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        return http.build();
    }

    //criando o AuthenticationManager que é usado na nossa classe AutenticacaoController
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //definindo o método de criptocrafia Bcrypt que será usado pelo AuthenticationManager para validar a
    //senha enviada na requisição post de autenticação
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

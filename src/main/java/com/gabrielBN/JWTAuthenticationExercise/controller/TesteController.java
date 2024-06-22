package com.gabrielBN.JWTAuthenticationExercise.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @PostMapping
    public void teste() {
        System.out.println("funcionou");
    }
}

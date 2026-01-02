package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.DadosConta;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Bem-vindo ao Sistema Bancário! Aplicação Spring Boot está funcionando.";
    }
    
    @GetMapping("/api/status")
    public String status() {
        return "Aplicação está online e funcionando corretamente!";
    }
    
    @PostMapping("/api/criar-conta")
    public String criarConta(@RequestBody DadosConta dados) {
        return String.format(
            "Conta criada com sucesso! Nome: %s, CPF: %s, Saldo Inicial: R$ %.2f",
            dados.getNome(),
            dados.getCpf(),
            dados.getSaldoInicial() != null ? dados.getSaldoInicial() : 0.0
        );
    }
}


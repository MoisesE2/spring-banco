package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.DadosConta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    @GetMapping("/api/endpoints")
    public ResponseEntity<Map<String, Object>> listarEndpoints() {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("baseUrl", "http://localhost:8081");
        
        List<Map<String, String>> endpoints = new ArrayList<>();
        
        // Endpoints gerais
        Map<String, String> endpoint1 = new HashMap<>();
        endpoint1.put("metodo", "GET");
        endpoint1.put("url", "/");
        endpoint1.put("descricao", "Página inicial");
        endpoints.add(endpoint1);
        
        Map<String, String> endpoint2 = new HashMap<>();
        endpoint2.put("metodo", "GET");
        endpoint2.put("url", "/api/status");
        endpoint2.put("descricao", "Status da aplicação");
        endpoints.add(endpoint2);
        
        Map<String, String> endpoint3 = new HashMap<>();
        endpoint3.put("metodo", "GET");
        endpoint3.put("url", "/api/endpoints");
        endpoint3.put("descricao", "Lista todos os endpoints disponíveis");
        endpoints.add(endpoint3);
        
        // Endpoints de contas
        Map<String, String> endpoint4 = new HashMap<>();
        endpoint4.put("metodo", "GET");
        endpoint4.put("url", "/api/contas");
        endpoint4.put("descricao", "Listar todas as contas");
        endpoints.add(endpoint4);
        
        Map<String, String> endpoint5 = new HashMap<>();
        endpoint5.put("metodo", "GET");
        endpoint5.put("url", "/api/contas/{id}");
        endpoint5.put("descricao", "Buscar conta por ID");
        endpoints.add(endpoint5);
        
        Map<String, String> endpoint6 = new HashMap<>();
        endpoint6.put("metodo", "POST");
        endpoint6.put("url", "/api/contas");
        endpoint6.put("descricao", "Criar nova conta");
        endpoint6.put("body", "{\"nome\": \"string\", \"cpf\": \"string\", \"saldo\": number}");
        endpoints.add(endpoint6);
        
        Map<String, String> endpoint7 = new HashMap<>();
        endpoint7.put("metodo", "PUT");
        endpoint7.put("url", "/api/contas/{id}");
        endpoint7.put("descricao", "Atualizar conta");
        endpoint7.put("body", "{\"nome\": \"string\", \"cpf\": \"string\"}");
        endpoints.add(endpoint7);
        
        Map<String, String> endpoint8 = new HashMap<>();
        endpoint8.put("metodo", "DELETE");
        endpoint8.put("url", "/api/contas/{id}");
        endpoint8.put("descricao", "Deletar conta por ID");
        endpoints.add(endpoint8);
        
        Map<String, String> endpoint8b = new HashMap<>();
        endpoint8b.put("metodo", "DELETE");
        endpoint8b.put("url", "/api/contas/cpf/{cpf}");
        endpoint8b.put("descricao", "Deletar conta por CPF");
        endpoints.add(endpoint8b);
        
        Map<String, String> endpoint8c = new HashMap<>();
        endpoint8c.put("metodo", "DELETE");
        endpoint8c.put("url", "/api/contas");
        endpoint8c.put("descricao", "Deletar todas as contas");
        endpoints.add(endpoint8c);
        
        // Endpoints de operações bancárias
        Map<String, String> endpoint9 = new HashMap<>();
        endpoint9.put("metodo", "POST");
        endpoint9.put("url", "/api/contas/{id}/deposito");
        endpoint9.put("descricao", "Fazer depósito");
        endpoint9.put("body", "{\"valor\": number}");
        endpoints.add(endpoint9);
        
        Map<String, String> endpoint10 = new HashMap<>();
        endpoint10.put("metodo", "POST");
        endpoint10.put("url", "/api/contas/{id}/saque");
        endpoint10.put("descricao", "Fazer saque");
        endpoint10.put("body", "{\"valor\": number}");
        endpoints.add(endpoint10);
        
        Map<String, String> endpoint11 = new HashMap<>();
        endpoint11.put("metodo", "POST");
        endpoint11.put("url", "/api/contas/transferencia");
        endpoint11.put("descricao", "Transferir entre contas");
        endpoint11.put("body", "{\"contaOrigemId\": number, \"contaDestinoId\": number, \"valor\": number}");
        endpoints.add(endpoint11);
        
        resposta.put("total", endpoints.size());
        resposta.put("endpoints", endpoints);
        
        return ResponseEntity.ok(resposta);
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


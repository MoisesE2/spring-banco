package com.example.model;

public class DadosConta {
    private String nome;
    private String cpf;
    private Double saldoInicial;
    
    // Construtor padrão (necessário para o Spring)
    public DadosConta() {
    }
    
    // Construtor com parâmetros
    public DadosConta(String nome, String cpf, Double saldoInicial) {
        this.nome = nome;
        this.cpf = cpf;
        this.saldoInicial = saldoInicial;
    }
    
    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public Double getSaldoInicial() {
        return saldoInicial;
    }
    
    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
}


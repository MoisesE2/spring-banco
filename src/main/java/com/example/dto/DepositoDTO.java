package com.example.dto;

public class DepositoDTO {
    private Double valor;
    
    public DepositoDTO() {
    }
    
    public DepositoDTO(Double valor) {
        this.valor = valor;
    }
    
    public Double getValor() {
        return valor;
    }
    
    public void setValor(Double valor) {
        this.valor = valor;
    }
}


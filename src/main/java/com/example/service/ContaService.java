package com.example.service;

import com.example.entity.Conta;
import com.example.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {
    
    @Autowired
    private ContaRepository contaRepository;
    
    public List<Conta> listarTodas() {
        return contaRepository.findAll();
    }
    
    public Optional<Conta> buscarPorId(Long id) {
        return contaRepository.findById(id);
    }
    
    public Optional<Conta> buscarPorCpf(String cpf) {
        return contaRepository.findByCpf(cpf);
    }
    
    @Transactional
    public Conta criar(Conta conta) {
        // Verifica se já existe conta com o mesmo CPF
        if (contaRepository.existsByCpf(conta.getCpf())) {
            throw new RuntimeException("Já existe uma conta com este CPF");
        }
        return contaRepository.save(conta);
    }
    
    @Transactional
    public Conta atualizar(Long id, Conta contaAtualizada) {
        Conta conta = contaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        
        // Verifica se o CPF está sendo alterado e se já existe
        if (!conta.getCpf().equals(contaAtualizada.getCpf()) && 
            contaRepository.existsByCpf(contaAtualizada.getCpf())) {
            throw new RuntimeException("Já existe uma conta com este CPF");
        }
        
        conta.setNome(contaAtualizada.getNome());
        conta.setCpf(contaAtualizada.getCpf());
        
        return contaRepository.save(conta);
    }
    
    @Transactional
    public void deletar(Long id) {
        if (!contaRepository.existsById(id)) {
            throw new RuntimeException("Conta não encontrada");
        }
        contaRepository.deleteById(id);
    }
    
    @Transactional
    public Conta depositar(Long id, Double valor) {
        if (valor <= 0) {
            throw new RuntimeException("Valor do depósito deve ser maior que zero");
        }
        
        Conta conta = contaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        
        conta.setSaldo(conta.getSaldo() + valor);
        return contaRepository.save(conta);
    }
    
    @Transactional
    public Conta sacar(Long id, Double valor) {
        if (valor <= 0) {
            throw new RuntimeException("Valor do saque deve ser maior que zero");
        }
        
        Conta conta = contaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        
        if (conta.getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente");
        }
        
        conta.setSaldo(conta.getSaldo() - valor);
        return contaRepository.save(conta);
    }
    
    @Transactional
    public void transferir(Long contaOrigemId, Long contaDestinoId, Double valor) {
        if (valor <= 0) {
            throw new RuntimeException("Valor da transferência deve ser maior que zero");
        }
        
        if (contaOrigemId.equals(contaDestinoId)) {
            throw new RuntimeException("Não é possível transferir para a mesma conta");
        }
        
        Conta contaOrigem = contaRepository.findById(contaOrigemId)
            .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada"));
        
        Conta contaDestino = contaRepository.findById(contaDestinoId)
            .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada"));
        
        if (contaOrigem.getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente para transferência");
        }
        
        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);
        
        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);
    }
}


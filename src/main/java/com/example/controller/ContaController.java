package com.example.controller;

import com.example.dto.DepositoDTO;
import com.example.dto.SaqueDTO;
import com.example.dto.TransferenciaDTO;
import com.example.entity.Conta;
import com.example.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contas")
@CrossOrigin(origins = "*") // Permite requisições do React
public class ContaController {
    
    @Autowired
    private ContaService contaService;
    
    // GET /api/contas - Listar todas as contas
    @GetMapping
    public ResponseEntity<List<Conta>> listarTodas() {
        List<Conta> contas = contaService.listarTodas();
        return ResponseEntity.ok(contas);
    }
    
    // GET /api/contas/{id} - Buscar conta por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Conta conta = contaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
            return ResponseEntity.ok(conta);
        } catch (RuntimeException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
        }
    }
    
    // POST /api/contas - Criar nova conta
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Conta conta) {
        try {
            Conta contaCriada = contaService.criar(conta);
            return ResponseEntity.status(HttpStatus.CREATED).body(contaCriada);
        } catch (RuntimeException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }
    }
    
    // PUT /api/contas/{id} - Atualizar conta
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Conta conta) {
        try {
            Conta contaAtualizada = contaService.atualizar(id, conta);
            return ResponseEntity.ok(contaAtualizada);
        } catch (RuntimeException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }
    }
    
    // DELETE /api/contas/{id} - Deletar conta por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            contaService.deletar(id);
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", "Conta deletada com sucesso");
            resposta.put("id", id.toString());
            return ResponseEntity.ok(resposta);
        } catch (RuntimeException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
        }
    }
    
    // DELETE /api/contas/cpf/{cpf} - Deletar conta por CPF
    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<?> deletarPorCpf(@PathVariable String cpf) {
        try {
            contaService.deletarPorCpf(cpf);
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", "Conta deletada com sucesso");
            resposta.put("cpf", cpf);
            return ResponseEntity.ok(resposta);
        } catch (RuntimeException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
        }
    }
    
    // DELETE /api/contas - Deletar todas as contas
    @DeleteMapping
    public ResponseEntity<?> deletarTodas() {
        try {
            contaService.deletarTodas();
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", "Todas as contas foram deletadas com sucesso");
            return ResponseEntity.ok(resposta);
        } catch (RuntimeException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }
    }
    
    // POST /api/contas/{id}/deposito - Fazer depósito
    @PostMapping("/{id}/deposito")
    public ResponseEntity<?> depositar(@PathVariable Long id, @RequestBody DepositoDTO depositoDTO) {
        try {
            Conta conta = contaService.depositar(id, depositoDTO.getValor());
            return ResponseEntity.ok(conta);
        } catch (RuntimeException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }
    }
    
    // POST /api/contas/{id}/saque - Fazer saque
    @PostMapping("/{id}/saque")
    public ResponseEntity<?> sacar(@PathVariable Long id, @RequestBody SaqueDTO saqueDTO) {
        try {
            Conta conta = contaService.sacar(id, saqueDTO.getValor());
            return ResponseEntity.ok(conta);
        } catch (RuntimeException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }
    }
    
    // POST /api/contas/transferencia - Fazer transferência
    @PostMapping("/transferencia")
    public ResponseEntity<?> transferir(@RequestBody TransferenciaDTO transferenciaDTO) {
        try {
            contaService.transferir(
                transferenciaDTO.getContaOrigemId(),
                transferenciaDTO.getContaDestinoId(),
                transferenciaDTO.getValor()
            );
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", "Transferência realizada com sucesso");
            return ResponseEntity.ok(resposta);
        } catch (RuntimeException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }
    }
}


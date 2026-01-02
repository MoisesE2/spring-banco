# Sistema Bancário - API Spring Boot

Projeto de API em Java para simulação de um sistema bancário, com armazenamento de usuários, transferência, saque e depósito.

## Como Iniciar o Projeto

### Pré-requisitos
- Java 17 ou superior instalado
- Maven instalado (ou use o `mvnw` que vem no projeto)

### Passo a Passo

1. **Abra o terminal na pasta do projeto**

2. **Execute o comando:**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   No Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

3. **Aguarde a mensagem:** `Started ProjetoBancoEmJavaApplication`

4. **Pronto!** A API está rodando em: `http://localhost:8081`

## Teste simples do funcionamento da api

### Opção 1: Navegador
Abra no navegador:
- `http://localhost:8081/` - Página inicial
- `http://localhost:8081/api/status` - Status da aplicação

### Opção 2: Postman ou Insomnia
Importe as requisições abaixo:

**Criar uma conta:**
```
POST http://localhost:8081/api/contas
Content-Type: application/json

{
  "nome": "João Silva",
  "cpf": "123.456.789-00",
  "saldo": 1000.00
}
```

**Listar todas as contas:**
```
GET http://localhost:8081/api/contas
```

**Fazer um depósito:**
```
POST http://localhost:8081/api/contas/1/deposito
Content-Type: application/json

{
  "valor": 500.00
}
```

##  Endpoints Disponíveis

GET http://localhost:8081/api/endpoints

## 💾 Banco de Dados

O projeto usa **H2 Database** (banco em memória).

Para acessar o console do banco:
1. Acesse: `http://localhost:8081/h2-console`
2. JDBC URL: `jdbc:h2:mem:testdb`
3. Usuário: `sa`
4. Senha: (deixe em branco)

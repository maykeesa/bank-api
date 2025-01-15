# MVP | Banco - 🏦
Este repositório contém um MVP de um sistema bancário simples, com funcionalidades básicas como cadastro de contas, operações de saldo e transações entre contas.


<br>

## 🪛 | Tecnologias usadas
- Java 17
- Spring Boot 3.4.1
- PostgreSQL 17
- Maven 3.9.9
- Docker 4.37.1

## 💻 | Funcionalidades
- Bank Accounts
  - Bank Account 
    - Buscar todos
    - Buscar por número da conta
    - Buscar por agência
    - Buscar por documento
    - Cadastrar conta bancária
    - Atualizar conta bancária
  - Balance
    - Atualizar saldo  
- Transactions
  - Transaction
    - Buscar todos
    - Buscar por Id
    - Buscar por código do banco e número da contraparte
    - Cadastrar transação


## 📜 Pré-requisitos
- Git
- Docker
- Postman ([Collections](https://drive.google.com/file/d/1FyaQEXZdAP0lKrfh3S7CVRYw2SndtN-a/view?usp=sharing))

#### Passos para Execução

1. Clone o repositório:
```
git clone https://github.com/maykeesa/bank-api.git
```

2. Rodar comando Docker na raiz do projeto:
```
 docker compose up -d
```

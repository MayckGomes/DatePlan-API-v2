# 📅 DatePlan API v2

API REST para planejamento e gerenciamento de encontros/eventos, construída com **Spring Boot 4** e **Java 21**.

---

## 🚀 Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.6 |
| PostgreSQL | Latest |
| Redis | Latest |
| Flyway | (migrations) |
| JWT (Auth0) | 4.5.1 |
| Lombok | Latest |
| H2 (testes) | Latest |
| BouncyCastle | 1.78.1 |
| Guava | 33.6.0-jre |

---

## 📋 Pré-requisitos

- **Java 21+**
- **Docker** (recomendado para subir PostgreSQL e Redis)
- **Gradle** (ou use o wrapper `./gradlew`)

---

## ⚙️ Configuração

1. Clone o repositório:
   ```bash
   git clone https://github.com/MayckGomes/dateplan-api-v2.git
   cd dateplan-api-v2
   ```

2. Crie o arquivo `.env` na raiz do projeto apartir do .envexemple

---

## ▶️ Executando

```bash
./gradlew bootRun
```

A API estará disponível em: `http://localhost:8080`

---


Os testes utilizam **H2** (banco em memória) e instâncias embutidas de Redis, sem necessidade de infraestrutura externa.

---

## 🏗️ Estrutura do Projeto

```
dateplan-api-v2/
├── src/
│   ├── main/
│   │   ├── java/com/mayckgomes/dateplan/
│   │   └── resources/
│   │       └── db/migration/        # Scripts Flyway
│   └── test/
├── docs/                            # Documentação adicional
├── build.gradle
└── .env                             # Variáveis de ambiente (não versionar)
```

---

## 📚 Documentação

- [Auth](https://github.com/MayckGomes/dateplan-api-v2/tree/master/docs/auth)
- [Users](https://github.com/MayckGomes/dateplan-api-v2/tree/master/docs/users)
- [Relationships](https://github.com/MayckGomes/dateplan-api-v2/tree/master/docs/relationships)
- [Invites](https://github.com/MayckGomes/dateplan-api-v2/tree/master/docs/invites)
- [Dates](https://github.com/MayckGomes/dateplan-api-v2/tree/master/docs/dates)

---

## 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)** para autenticação, com suporte a criptografia via **BouncyCastle**. Inclua o token no header das requisições:

```
Authorization: Bearer <seu_token>
```

---

## 🗄️ Banco de Dados

As migrações são gerenciadas pelo **Flyway** e executadas automaticamente na inicialização da aplicação. O banco de dados principal é **PostgreSQL**.

---



## 👤 Autor

**Mayck Gomes** — [@MayckGomes](https://github.com/MayckGomes)
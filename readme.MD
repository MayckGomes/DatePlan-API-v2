# 📅 DatePlan API v2

API REST para o **DatePlan**, um app de casais para planejar encontros, guardar memórias e organizar a rotina do relacionamento a dois. Construída com **Spring Boot 4** e **Java 21**, rodando em produção em uma instância ARM da Oracle Cloud (Always Free).

🔗 App na Play Store: [Dateplan](https://play.google.com/store/apps/details?id=mayckgomes.com.dateplan)

---

## 📑 Sumário

- [Sobre o projeto](#-sobre-o-projeto)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Documentação da API](#-documentação-da-api)
- [Pré-requisitos](#-pré-requisitos)
- [Configuração](#-configuração)
- [Executando](#-executando)
- [Testes](#-testes)
- [Estrutura do projeto](#-estrutura-do-projeto)
- [Autenticação](#-autenticação)
- [Banco de dados](#-banco-de-dados)
- [Autor](#-autor)

---

## 💡 Sobre o projeto

O DatePlan permite que casais:

- Conectem suas contas através de um sistema de convites;
- Criem e gerenciem um calendário compartilhado de encontros;
- Transformem encontros já realizados em memórias com fotos e anotações;
- Recebam notificações push (Firebase) sobre eventos e lembretes;
- Façam login com e-mail/senha ou Google Sign-In.

Este repositório contém o **backend** da aplicação. O cliente mobile (Android/KMP) consome esta API.

---

## 🚀 Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 4.0.6 | Framework da aplicação |
| Spring Security | — | Autenticação e autorização |
| Spring Data JPA | — | Persistência de dados |
| PostgreSQL | Latest | Banco de dados principal |
| Redis | Latest | Blacklist de tokens / cache |
| Flyway | — | Versionamento de schema (migrations) |
| Auth0 java-jwt | 4.5.1 | Geração e validação de JWT |
| BouncyCastle | 1.78.1 | Criptografia (hash de senha) |
| Firebase Admin SDK | 9.8.0 | Notificações push / login Google |
| Guava | 33.6.0-jre | Utilitários |
| H2 | Latest | Banco em memória para testes |
| Lombok | Latest | Redução de boilerplate |

---

## 🏗️ Arquitetura

A API segue uma arquitetura em camadas clássica do ecossistema Spring:

```
Controller → Service → Repository → Entity (JPA)
                 ↓
               DTOs (request/response)
```

Pontos de destaque:

- **JWT com dois tokens**: `accessToken` e `refreshToken`, cada um com segredo próprio e uma claim customizada `type`, validada na camada do verifier;
- **Logout com blacklist via Redis**: tokens invalidados no logout são armazenados no Redis até a expiração natural, impedindo reuso;
- **Login social**: integração com Firebase Admin SDK para validar o ID Token do Google recebido do app mobile;
- **Hash de senha**: implementação própria usando BouncyCastle, com parâmetros configuráveis (salt, memória, paralelismo, iterações — perfil Argon2-like);
- **Tratamento de exceções centralizado**: exceções de domínio customizadas (`exception/custom/*`) capturadas por um `@ControllerAdvice` em `exception/handler`;
- **Migrations versionadas com Flyway**: schema evolui através de scripts `V1` a `V5`, cobrindo usuários, relacionamentos, convites, encontros (dates) e memórias.

---

## 📚 Documentação da API

Documentação detalhada de request/response de cada módulo:

- [Auth](docs/auth/auth.MD)
- [Users](docs/users/users.MD)
- [Relationships](docs/relationship/relationship.MD)
- [Invites](docs/invite/invite.MD)
- [Dates](docs/date/date.MD)
- [Memories](docs/memories/memories.MD)
- [Server](docs/server/server.MD)

---

## 📋 Pré-requisitos

- **Java 21+**
- **Docker** (recomendado para subir PostgreSQL e Redis localmente)
- **Gradle** (ou use o wrapper `./gradlew`, incluso no projeto)

---

## ⚙️ Configuração

1. Clone o repositório:

   ```bash
   git clone https://github.com/MayckGomes/dateplan-api-v2.git
   cd dateplan-api-v2
   ```

2. Crie o arquivo `.env` na raiz do projeto a partir do `.envexemple`:

   ```bash
   cp .envexemple .env
   ```

3. Preencha as variáveis de ambiente:

   | Variável | Descrição |
      |---|---|
   | `SECRET` | Segredo usado para assinar os tokens JWT |
   | `ISSUER` | Emissor (`iss`) do token JWT |
   | `AUDIENCE` | Público-alvo (`aud`) do token JWT |
   | `SALT_LENGTH` | Tamanho do salt usado no hash de senha |
   | `HASH_LENGTH` | Tamanho do hash gerado |
   | `PARALLELISM` | Grau de paralelismo do algoritmo de hash |
   | `MEMORY` | Memória (KB) usada pelo algoritmo de hash |
   | `ITERATIONS` | Número de iterações do algoritmo de hash |
   | `REDIS_URL` | URL de conexão do Redis |
   | `FIREBASE_JSON_PATH` | Caminho para o JSON de credenciais do Firebase (`file:...`) |
   | `DB_URL` | URL JDBC do PostgreSQL |
   | `DB_USERNAME` | Usuário do banco |
   | `DB_PASSWORD` | Senha do banco |

4. (Opcional) Suba PostgreSQL e Redis via Docker, caso não tenha instâncias próprias.

---

## ▶️ Executando

```bash
./gradlew bootRun
```

A API estará disponível em: `http://localhost:8080`

### Rodando com Docker

O projeto já inclui um `Dockerfile` multi-stage (build com Gradle + runtime em `eclipse-temurin:21-jre`), otimizado para ambientes com poucos recursos (ex: instâncias ARM 1 vCPU / 1 GB RAM):

```bash
docker build -t dateplan-api .
docker run --env-file .env -p 8080:8080 dateplan-api
```

---

## 🧪 Testes

```bash
./gradlew test
```

Os testes utilizam **H2** (banco em memória) e instâncias embutidas de Redis, sem necessidade de infraestrutura externa.

---

## 🗂️ Estrutura do projeto

```
dateplan-api-v2/
├── src/
│   ├── main/
│   │   ├── java/com/mayckgomes/dateplan_api/
│   │   │   ├── configurations/    # Security, Firebase, filtros
│   │   │   ├── controllers/       # Endpoints REST
│   │   │   ├── services/          # Regras de negócio
│   │   │   ├── repositorys/       # Interfaces Spring Data JPA
│   │   │   ├── entitys/           # Entidades JPA
│   │   │   ├── dto/               # Objetos de request/response por módulo
│   │   │   ├── jwt/               # Geração e validação de tokens
│   │   │   ├── exception/         # Exceções customizadas + handler global
│   │   │   ├── domains/           # Objetos de domínio
│   │   │   └── utils/             # Utilitários gerais
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/      # Scripts Flyway (V1 a V5)
│   └── test/
├── docs/                          # Documentação detalhada por módulo
├── Dockerfile
├── build.gradle
└── .envexemple                    # Modelo de variáveis de ambiente
```

### Módulos principais

- **Auth** — registro, login, login com Google, refresh token, logout;
- **Users** — atualização de nome, senha, token de notificação, aceite de termos/política;
- **Relationship** — criação/consulta/remoção do vínculo entre o casal, definição da data de início;
- **Invite** — envio, aceite e recusa de convites para conectar contas;
- **Date** — criação, edição, listagem e remoção de encontros planejados;
- **Memory** — registro de memórias (fotos e notas) a partir de encontros realizados.

---

## 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)** com par de tokens (access/refresh), assinados com segredos distintos e validados por um filtro de segurança próprio (`SecurityFilter`). Inclua o token no header das requisições autenticadas:

```
Authorization: Bearer <seu_access_token>
```

No logout, o token é adicionado a uma blacklist no Redis, prevenindo seu reuso até a expiração natural.

---

## 🗄️ Banco de dados

As migrações são gerenciadas pelo **Flyway** e executadas automaticamente na inicialização da aplicação (`spring.jpa.hibernate.ddl-auto=validate`, garantindo que o schema em produção sempre reflita as migrations). O banco de dados principal é **PostgreSQL**, com pool de conexões (HikariCP) ajustado para ambientes com poucos recursos.

Tabelas principais: `users`, `relationships`, `invites`, `dates`, `memories`.

---


## 👤 Autor

**Mayck Gomes** — [@MayckGomes](https://github.com/MayckGomes)
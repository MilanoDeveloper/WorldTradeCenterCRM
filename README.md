# World Trade Center CRM

Este projeto é uma solução de CRM para a **WTC Association**, permitindo a interação direta entre Operadores e Clientes através de um chat em tempo real. O ecossistema é composto por um aplicativo Android (Kotlin/Compose) e um Backend (Spring Boot/MongoDB).

---

## 🚀 Instruções de Execução

### 1. Pré-requisitos
*   **Java 17** ou superior.
*   **MongoDB** rodando localmente (porta padrão `27017`).
*   **Android Studio** (versão Ladybug ou superior recomendada).
*   **Emulador Android** com API 27+.

### 2. Executando o Backend (Spring Boot)
1.  Navegue até a pasta: `C:\dev\repositorios\WTC\WTFBackEnd\work\work`.
2.  Abra um terminal e execute:
    ```bash
    ./mvnw spring-boot:run
    ```
3.  **Seed Automático**: Ao iniciar pela primeira vez, o backend criará automaticamente os usuários de teste (Operador WTC, João Silva e Maria Oliveira) se o banco estiver vazio.

### 3. Executando o Aplicativo (Android)
1.  Abra o projeto `WorldTradeCenterCRM` no Android Studio.
2.  Certifique-se de que o `ApiClient.kt` está apontando para `http://10.0.2.2:8080/api/` (IP padrão do emulador para o localhost).
3.  Clique em **Run** no Android Studio.

---

## 📑 Especificação da API

Base URL: `http://localhost:8080/api`

### Autenticação e Usuários

#### **Cadastrar Cliente**
*   **Rota**: `/auth/register`
*   **Método**: `POST`
*   **Payload (JSON)**:
    ```json
    {
      "name": "Nome do Usuário",
      "email": "usuario@exemplo.com",
      "password": "senha_segura"
    }
    ```
*   **Resposta (200 OK)**: Retorna o token JWT e os dados do usuário com `role: CLIENT`.

#### **Login**
*   **Rota**: `/auth/login`
*   **Método**: `POST`
*   **Payload (JSON)**:
    ```json
    {
      "email": "admin@wtc.com",
      "password": "123123"
    }
    ```
*   **Resposta (200 OK)**: Retorna o token JWT e o papel (`OPERATOR` ou `CLIENT`).

#### **Listar Usuários**
*   **Rota**: `/users`
*   **Método**: `GET`
*   **Resposta (200 OK)**: Lista de todos os usuários cadastrados.

---

### Chat e Mensagens

#### **Enviar Mensagem**
*   **Rota**: `/messages`
*   **Método**: `POST`
*   **Payload (JSON)**:
    ```json
    {
      "senderId": "ID_DO_REMETENTE",
      "receiverId": "ID_DO_DESTINATARIO",
      "content": "Olá, tudo bem?"
    }
    ```
*   **Resposta (200 OK)**: Objeto da mensagem salva com timestamp.

#### **Buscar Conversa (Histórico)**
*   **Rota**: `/messages`
*   **Método**: `GET`
*   **Parâmetros (Query)**: `user1` (ID), `user2` (ID).
*   **Exemplo**: `/messages?user1=ID_A&user2=ID_B`
*   **Resposta (200 OK)**: Lista ordenada cronologicamente de todas as mensagens trocadas entre os dois usuários (bidirecional).

---

## 🛠️ Tecnologias Utilizadas

### Mobile
*   **Jetpack Compose**: UI moderna e declarativa.
*   **Retrofit/OkHttp**: Comunicação com a API e interceptação de tokens.
*   **Kotlin Coroutines**: Gerenciamento de tarefas em segundo plano (Polling).
*   **Navigation Compose**: Fluxo entre telas (Login, Cadastro, Chat).

### Backend
*   **Spring Boot 3**: Framework robusto para microsserviços.
*   **Spring Data MongoDB**: Persistência de dados NoSQL.
*   **Spring Security + JWT**: Proteção de rotas e autenticação stateless.
*   **Lombok**: Redução de código boilerplate.
*   **SLF4J**: Logs detalhados do fluxo de mensagens.

---

## 👥 Credenciais de Teste (Seed)

| Papel | Email | Senha |
| :--- | :--- | :--- |
| **Operador** | `admin@wtc.com` | `123123` |
| **Cliente** | `joao@email.com` | `123123` |
| **Cliente** | `maria@consultoria.com` | `123123` |

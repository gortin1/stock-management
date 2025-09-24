# 📦 Stock Management API

Sistema de **Gestão de Estoque para Mini Mercados**, desenvolvido em **Java 17** com **Spring Boot 3.5.5**, utilizando **arquitetura hexagonal (ports & adapters)**.  
A API permite o gerenciamento de **sellers**, **produtos** e **vendas**, garantindo segurança via **JWT** e envio de mensagens via **Twilio**.

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.5**
    - Spring Web
    - Spring Data JPA
    - Spring Security
    - Spring Validation
- **PostgreSQL** (persistência de dados)
- **Lombok** (boilerplate reduction)
- **JWT (io.jsonwebtoken)** para autenticação
- **Twilio SDK** (envio de mensagens WhatsApp)
- **Maven** (gerenciamento de dependências)

---

## 🏗️ Arquitetura

A aplicação segue a **arquitetura hexagonal**, separando bem as responsabilidades:

```
src/main/java/com/api/stock_management
│
├── application
│   ├── controller       # Controllers REST
│   │   ├── AuthController
│   │   ├── ProductController
│   │   ├── SaleController
│   │   └── SellerController
│   │
│   ├── dto              # Objetos de transferência de dados (DTOs)
│   │   ├── auth (TokenResponseDTO)
│   │   ├── login (LoginRequestDTO)
│   │   ├── product (ProductRequestDTO, ProductResponseDTO)
│   │   ├── sale (SaleRequestDTO, SaleResponseDTO)
│   │   └── seller (SellerRequestDTO, SellerResponseDTO, SellerActivateRequestDTO)
│   │
│   └── service          # Regras de negócio e orquestração
│       ├── AuthenticationService
│       ├── ProductService
│       ├── SaleService
│       └── SellerService
│
├── domain
│   ├── model            # Entidades do domínio
│   │   ├── Product
│   │   ├── Sale
│   │   └── Seller
│   │
│   └── repository       # Interfaces de acesso ao banco
│       ├── ProductRepository
│       ├── SaleRepository
│       └── SellerRepository
│
└── infrastructure
    ├── config           # Configurações de segurança
    │   ├── SecretKeyGenerator
    │   └── SecurityConfig
    │
    ├── messaging        # Integrações externas
    │   └── TwilioService
    │
    └── security         # Autenticação e JWT
        ├── JwtAuthFilter
        └── TokenService
```

---

## 🔐 Segurança

- Autenticação baseada em **JWT**.
- Cada **seller** só pode visualizar e gerenciar **seus próprios produtos**.
- Filtros de segurança definidos em `JwtAuthFilter` + `SecurityConfig`.

---

## ⚙️ Funcionalidades

- **Seller**
    - Cadastro e ativação via código (Twilio WhatsApp).
    - Login e emissão de token JWT.
- **Produto**
    - CRUD de produtos.
    - Restrição: um seller só gerencia os próprios produtos.
- **Venda**
    - Registro de vendas atômicas com `@Transactional`.
    - Verificação de estoque e status do produto.

---

## 🛠️ Configuração do Ambiente

### 📑 application.properties
Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/stockdb
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret=chave_secreta_segura

# Twilio
twilio.accountSid=SEU_SID
twilio.authToken=SEU_TOKEN
twilio.phoneNumber=SEU_NUMERO
```

### ▶️ Rodando o projeto

```bash
# Build do projeto
mvn clean install

# Executar
mvn spring-boot:run
```

A API estará disponível em:  
👉 `http://localhost:8080`

---

## 📚 Endpoints Principais

### 🔑 Autenticação
- `POST /auth/login` → Login do seller
- `POST /auth/activate` → Ativação via código (WhatsApp)

### 👤 Sellers
- `POST /sellers` → Cadastro
- `GET /sellers/{id}` → Detalhes do seller

### 📦 Produtos
- `POST /products` → Criar produto
- `GET /products` → Listar produtos do seller logado
- `PUT /products/{id}` → Atualizar produto
- `DELETE /products/{id}` → Remover produto

### 💰 Vendas
- `POST /sales` → Registrar venda
- `GET /sales` → Listar vendas do seller logado

---

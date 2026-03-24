# 🛒 Nexus E-Commerce API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Swagger](https://img.shields.io/badge/-Swagger-%23C1E1C1?style=for-the-badge&logo=swagger&logoColor=black)

Uma API REST moderna e escalável para gerenciamento de e-commerce, construída com **Spring Boot 3**, focada em segurança, performance e uma experiência de desenvolvedor aprimorada através de documentação automatizada.

---

## 🚀 Demonstração Online

Acesse a interface visual da API rodando na minha VPS:

* **Página Inicial:** [https://ecommerce.lucasalmeidadev.com.br/](https://ecommerce.lucasalmeidadev.com.br/)
* **Documentação Swagger:** [Clique aqui para testar os endpoints](https://ecommerce.lucasalmeidadev.com.br/swagger-ui/index.html)

---

## 🛠️ Tecnologias e Recursos

### Core Backend
* **Java 17 & Spring Boot 3**: Base robusta para a aplicação.
* **Spring Data JPA**: Abstração de persistência e consultas ao banco.
* **PostgreSQL**: Banco de dados relacional de alta performance.
* **Spring Validation**: Regras de negócio validadas diretamente nos DTOs.

### Segurança e Arquitetura
* **Spring Security & JWT (JSON Web Token)**: Autenticação stateless com tokens de acesso.
* **Controle de Acesso (RBAC)**: Diferentes permissões para `ROLE_ADMIN` (gestão de produtos) e `ROLE_USER` (compras).
* **HATEOAS**: Implementação de links navegáveis nos recursos da API.

### Documentação
* **SpringDoc OpenAPI (Swagger)**: Documentação interativa para teste de endpoints em tempo real.

---

## 📋 Endpoints Principais

| Recurso | Endpoint | Acesso | Descrição |
| :--- | :--- | :--- | :--- |
| **Auth** | `POST /login` | Público | Autentica usuário e gera Token JWT |
| **Usuários** | `POST /usuarios` | Público | Cadastro de novos clientes |
| **Produtos** | `GET /produtos` | Público | Listagem e busca de itens |
| **Produtos** | `POST /produtos` | **Admin** | Cadastro de novos itens no estoque |
| **Pedidos** | `POST /pedidos` | **Autenticado** | Realização de checkout de compra |
| **Pedidos** | `GET /pedidos/meus` | **Autenticado** | Histórico de compras do usuário |

---

## 🏗️ Como rodar o projeto localmente

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/seu-usuario/API-E-commerce.git](https://github.com/seu-usuario/API-E-commerce.git)

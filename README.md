# Marketplace Backend API

[![CI](https://github.com/gisoogholizade-ux/marketplace-backend-api/actions/workflows/ci.yml/badge.svg)](https://github.com/gisoogholizade-ux/marketplace-backend-api/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F?logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-ready-2496ED?logo=docker&logoColor=white)

A production-style REST API for a multi-role marketplace, built with **Java 21, Spring Boot, Spring Security, JWT, MySQL, Flyway, Docker and GitHub Actions**.

> Portfolio project by **Gisoo Gholizade — Java Backend Developer**. This repository is an original implementation and contains no proprietary company source code.

## What this project demonstrates

This repository is designed to show more than CRUD. It focuses on engineering patterns used in real business systems: authentication, authorization, transactional workflows, validation, database migrations, API documentation, automated tests and CI.

### Implemented

- User registration with validation and BCrypt password hashing
- Login with JWT token generation
- Stateless Spring Security authentication
- Role model for CUSTOMER, SELLER and ADMIN
- Public paginated product API
- Order creation with customer validation
- Customer order retrieval
- Payment creation with order validation
- Duplicate transaction-reference protection
- Order payment history
- Database foreign-key constraints
- Global API error handling
- DTO validation
- MySQL persistence with Spring Data JPA
- Flyway database migrations
- Swagger / OpenAPI with Bearer JWT security scheme
- Unit tests with JUnit 5 and Mockito
- Integration tests with Spring Boot, MockMvc, H2 and Flyway
- Multi-stage Docker image
- Docker Compose development environment
- GitHub Actions CI on Java 21

### Future domain extensions

Category, cart, checkout line-items, commission, settlement and reporting are intentionally kept as future modules rather than being presented as completed functionality.

## Architecture

```text
Client / Swagger UI
        |
        v
Spring Security + JWT Filter
        |
        v
Controllers
        |
        v
Services / Business Rules
        |
        v
Spring Data JPA Repositories
        |
        v
MySQL
        |
      Flyway
```

```text
src/main/java/com/gisoo/marketplace
├── auth
├── common
│   ├── exception
│   └── response
├── config
├── order
├── payment
├── product
├── security
└── user
```

## Security model

| Route | Access |
|---|---|
| `/api/v1/auth/**` | Public |
| `/api/v1/products/**` | Public |
| `/swagger-ui/**` and `/v3/api-docs/**` | Public documentation |
| `/api/v1/orders/**` | Authenticated |
| `/api/v1/payments/**` | Authenticated |
| `/api/v1/seller/**` | SELLER or ADMIN |
| `/api/v1/admin/**` | ADMIN |

Passwords are stored using BCrypt. The API is stateless and expects JWT Bearer tokens for protected endpoints.

## API examples

### Register

```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "fullName": "Demo Customer",
  "email": "customer@example.com",
  "password": "StrongPass123"
}
```

### Login

```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "customer@example.com",
  "password": "StrongPass123"
}
```

The response contains a JWT token. Send it to protected endpoints as:

```http
Authorization: Bearer <token>
```

### Browse products

```http
GET /api/v1/products?page=0&size=20
```

### Create an order

```http
POST /api/v1/orders
Authorization: Bearer <token>
Content-Type: application/json

{
  "customerId": 1,
  "totalAmount": 149.90
}
```

### Create a payment record

```http
POST /api/v1/payments
Authorization: Bearer <token>
Content-Type: application/json

{
  "orderId": 1,
  "amount": 149.90,
  "transactionReference": "demo-tx-1001"
}
```

## Run locally with Docker

Requirements: Docker + Docker Compose.

```bash
git clone https://github.com/gisoogholizade-ux/marketplace-backend-api.git
cd marketplace-backend-api
docker compose up --build
```

API:

```text
http://localhost:8080
```

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

The Compose environment starts MySQL first, waits for it to become healthy, then starts the API. Flyway creates and upgrades the schema automatically.

## Run without Docker

Requirements: Java 21, Maven and MySQL.

Create a database named `marketplace`, then optionally set:

```bash
DB_URL=jdbc:mysql://localhost:3306/marketplace
DB_USERNAME=root
DB_PASSWORD=your_password
JWT_SECRET=your-long-development-secret
```

Run:

```bash
mvn spring-boot:run
```

## Tests

```bash
mvn clean verify
```

The test suite includes unit tests and application integration tests using an in-memory H2 database in MySQL compatibility mode. Flyway migrations are executed during integration testing so schema problems fail CI early.

## Database migrations

Production schema changes are versioned in:

```text
src/main/resources/db/migration
```

Hibernate uses `ddl-auto=validate`; schema creation is owned by Flyway rather than automatic Hibernate updates. Relational constraints enforce valid customer, seller, order and payment references at database level.

## CI

Every push and pull request to `main` runs:

```text
Java 21 setup -> Maven clean verify -> unit + integration tests
```

A green CI badge means the current main branch compiles and its automated test suite passes.

## Engineering decisions

- Controllers stay thin; business rules live in services.
- Persistence is isolated behind repositories.
- API input uses validated request DTOs.
- Authentication is stateless.
- Secrets and DB credentials are supplied through environment variables.
- Database changes are repeatable and reviewable through Flyway migrations.
- Payment transaction references are protected against duplicates.
- Service validation is backed by database-level relational constraints.
- CI verifies the repository from a clean environment instead of relying on a developer machine.

## Current version

**1.0.0** — first portfolio-ready release.

## About the developer

I'm **Gisoo Gholizade**, a Java developer focused on Spring Boot backend development and business applications. My experience includes authentication, role-based systems, database-driven workflows, marketplace logic, orders, payments, contracts, reporting and operational web applications.

**Available for freelance and remote Java / Spring Boot projects.**

- GitHub: [gisoogholizade-ux](https://github.com/gisoogholizade-ux)
- Email: [gholizadegisoo@gmail.com](mailto:gholizadegisoo@gmail.com)

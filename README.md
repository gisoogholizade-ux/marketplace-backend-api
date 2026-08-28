# Marketplace Backend API

[![CI](https://github.com/gisoogholizade-ux/marketplace-backend-api/actions/workflows/ci.yml/badge.svg)](https://github.com/gisoogholizade-ux/marketplace-backend-api/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F?logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-ready-2496ED?logo=docker&logoColor=white)

A production-style REST API for a multi-role marketplace built with **Java 21, Spring Boot, Spring Security, JWT, MySQL, Flyway, Docker and GitHub Actions**.

> Portfolio project by **Gisoo Gholizade — Java Backend Developer**. This repository is an original implementation and contains no proprietary company source code.

## Current version

**v1.1.0 — Marketplace Workflow Release**

## Implemented

- User registration with validation and BCrypt password hashing
- Login and stateless JWT authentication
- CUSTOMER, SELLER and ADMIN roles
- Public paginated product catalog
- Category management
- Customer shopping cart with stock validation
- Transactional checkout
- Order and order-item creation
- Inventory deduction during checkout
- Payment records with unique transaction references
- Payment capture workflow
- Order transition from `PENDING` to `PAID`
- Seller commission generation only after successful payment
- Idempotent commission generation
- Seller settlement creation from pending commissions
- Admin marketplace summary reporting
- Global API error handling and DTO validation
- Flyway database migrations and relational constraints
- Swagger / OpenAPI with Bearer JWT support
- Unit and integration tests
- Multi-stage Docker image and Docker Compose
- GitHub Actions CI on Java 21

## Business flow

```text
Register / Login
      ↓
JWT Authentication
      ↓
Browse Products & Categories
      ↓
Add Products to Cart
      ↓
Checkout
      ↓
Order + Order Items
      ↓
Inventory Deduction
      ↓
Create Payment
      ↓
Capture Payment
      ↓
Order = PAID
      ↓
Seller Commission
      ↓
Settlement
      ↓
Admin Reporting
```

## Architecture

```text
Client / Swagger UI
        ↓
Spring Security + JWT Filter
        ↓
Controllers
        ↓
Services / Business Rules
        ↓
Spring Data JPA Repositories
        ↓
MySQL
        ↓
Flyway Migrations
```

Main modules:

```text
src/main/java/com/gisoo/marketplace
├── auth
├── cart
├── category
├── checkout
├── commission
├── common
├── config
├── order
├── payment
├── product
├── report
├── security
├── settlement
└── user
```

## Security model

| Route | Access |
|---|---|
| `/api/v1/auth/**` | Public |
| `GET /api/v1/products/**` | Public |
| `GET /api/v1/categories/**` | Public |
| `POST /api/v1/categories/**` | ADMIN |
| `/api/v1/cart/**` | Authenticated |
| `/api/v1/checkout/**` | Authenticated |
| `/api/v1/orders/**` | Authenticated |
| `/api/v1/payments/**` | Authenticated |
| `/api/v1/seller/**` | SELLER or ADMIN |
| `/api/v1/admin/**` | ADMIN |

## Example API flow

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
```

Use the returned token on protected endpoints:

```http
Authorization: Bearer <token>
```

### Add to cart

```http
POST /api/v1/cart/items
Authorization: Bearer <token>
Content-Type: application/json

{
  "customerId": 1,
  "productId": 10,
  "quantity": 2
}
```

### Checkout

```http
POST /api/v1/checkout/1
Authorization: Bearer <token>
```

### Create payment

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

### Capture payment

```http
POST /api/v1/payments/1/capture
Authorization: Bearer <token>
```

Capturing the payment marks the order as paid and creates seller commissions from the order items.

## Run with Docker

```bash
git clone https://github.com/gisoogholizade-ux/marketplace-backend-api.git
cd marketplace-backend-api
docker compose up --build
```

API: `http://localhost:8080`  
Swagger UI: `http://localhost:8080/swagger-ui.html`

## Run tests

```bash
mvn clean verify
```

The test profile uses H2 in MySQL compatibility mode and executes all Flyway migrations, so schema problems fail CI early.

## Engineering decisions

- Controllers remain thin; business rules live in services.
- Checkout and settlement operations are transactional.
- Financial workflow state changes are explicit.
- Commissions are generated after successful payment rather than at checkout.
- Commission generation is idempotent per order.
- Database evolution is owned by Flyway; Hibernate validates the schema.
- Secrets and database credentials come from environment variables.
- CI verifies the project from a clean environment.

## About the developer

I'm **Gisoo Gholizade**, a Java developer focused on Spring Boot backend development and business applications, including authentication, role-based systems, marketplace logic, orders, payments, financial workflows and reporting.

**Available for freelance and remote Java / Spring Boot projects.**

- GitHub: [gisoogholizade-ux](https://github.com/gisoogholizade-ux)
- Email: [gholizadegisoo@gmail.com](mailto:gholizadegisoo@gmail.com)

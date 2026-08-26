# Marketplace Backend API

A production-style REST API for a multi-role marketplace, built with **Java 21** and **Spring Boot 3**.

This project demonstrates backend architecture and business workflows commonly required in real marketplace and e-commerce systems: secure authentication, role-based authorization, products, orders, payments, commissions, settlements and reporting.

> Portfolio project by **Gisoo Gholizade** — Java Backend Developer.

## Why this project?

This is not intended to be a basic CRUD demo. The goal is to model realistic business rules and show how a maintainable Spring Boot backend can be structured for a growing product.

The architecture and workflows are inspired by hands-on experience developing production business applications. All code in this repository is an original portfolio implementation and does not contain proprietary source code.

## Planned Features

- JWT authentication
- Role-based access control: CUSTOMER, SELLER, ADMIN
- User registration and login
- Product and category management
- Inventory management
- Shopping cart
- Checkout workflow
- Order lifecycle management
- Payment records
- Seller commissions
- Settlement tracking
- Validation and global exception handling
- Pagination and filtering
- OpenAPI / Swagger documentation
- Database migrations
- Unit and integration tests
- Docker-ready local environment

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- MySQL
- Maven
- Bean Validation
- OpenAPI / Swagger
- JUnit 5
- Mockito
- Docker

## Architecture

The application follows a layered, domain-oriented structure:

```text
src/main/java/com/gisoo/marketplace
├── config
├── security
├── auth
├── user
├── product
├── category
├── cart
├── order
├── payment
├── commission
├── settlement
├── report
└── common
    ├── exception
    ├── response
    └── validation
```

Typical request flow:

```text
Client
  ↓
Controller
  ↓
Service / Business Rules
  ↓
Repository
  ↓
MySQL
```

Cross-cutting concerns such as authentication, authorization, validation and exception handling are separated from domain logic.

## Core Business Flow

```text
Customer registers
       ↓
Authentication / JWT
       ↓
Browse products
       ↓
Add items to cart
       ↓
Checkout
       ↓
Order created
       ↓
Payment recorded
       ↓
Seller commission calculated
       ↓
Settlement tracked
       ↓
Admin reporting
```

## Security

The API is designed around Spring Security and JWT.

Example access model:

| Capability | Customer | Seller | Admin |
|---|:---:|:---:|:---:|
| Browse products | ✓ | ✓ | ✓ |
| Create order | ✓ |  | ✓ |
| Manage own products |  | ✓ | ✓ |
| View seller settlements |  | ✓ | ✓ |
| Manage users |  |  | ✓ |
| Financial reports |  |  | ✓ |

## API Design

Example endpoints planned for the project:

```http
POST   /api/v1/auth/register
POST   /api/v1/auth/login

GET    /api/v1/products
GET    /api/v1/products/{id}
POST   /api/v1/seller/products
PUT    /api/v1/seller/products/{id}

GET    /api/v1/cart
POST   /api/v1/cart/items

POST   /api/v1/orders
GET    /api/v1/orders/{id}
GET    /api/v1/me/orders

POST   /api/v1/payments
GET    /api/v1/seller/settlements

GET    /api/v1/admin/reports
```

## Engineering Goals

- Clear separation of responsibilities
- Business rules kept out of controllers
- DTO-based API boundaries
- Secure defaults
- Consistent API error responses
- Transaction-safe financial workflows
- Testable service layer
- Readable naming and maintainable package structure
- Documentation that another developer can use without reading the entire codebase

## Development Roadmap

**Phase 1 — Foundation**  
Project setup · database configuration · entities · common API response model

**Phase 2 — Security**  
Registration · login · JWT · Spring Security · roles

**Phase 3 — Marketplace**  
Products · categories · inventory · cart

**Phase 4 — Transactions**  
Checkout · orders · payments · commissions · settlements

**Phase 5 — Production Quality**  
Swagger · validation · exception handling · tests · Docker · documentation

## Status

🚧 **Active portfolio project — implementation in progress.**

The repository will be developed incrementally with meaningful commits so the evolution of the backend can be reviewed.

## About the Developer

I'm **Gisoo Gholizade**, a Java developer focused on Spring Boot backend development and business applications.

I work with Java, Spring Boot, Spring Security, SQL databases and full-stack Java web technologies, with hands-on experience implementing real-world marketplace and operational workflows.

**Available for freelance and remote Java / Spring Boot projects.**

- GitHub: [gisoogholizade-ux](https://github.com/gisoogholizade-ux)
- Email: [gholizadegisoo@gmail.com](mailto:gholizadegisoo@gmail.com)

---

If this project is useful, consider starring the repository.
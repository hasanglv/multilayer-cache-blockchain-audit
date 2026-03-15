# 🔐 Blockchain-Based Audit Logging for Multi-Layer Caching Systems

<div align="center">

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![Ethereum](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=ethereum&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

*A Master's Thesis Research Project — combining high-performance caching with tamper-resistant blockchain audit logging.*

</div>

---

## 📌 Table of Contents

- [Overview](#-overview)
- [Problem Statement](#-problem-statement)
- [System Architecture](#-system-architecture)
- [Key Features](#-key-features)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Caching Architecture](#-caching-architecture)
- [Blockchain Audit Logging](#-blockchain-audit-logging)
- [REST API Reference](#-rest-api-reference)
- [Performance Results](#-performance-results)
- [Getting Started](#-getting-started)
- [Running Performance Tests](#-running-performance-tests)
- [Future Roadmap](#-future-roadmap)
- [Author](#-author)

---

## 📖 Overview

This project implements a **secure, high-performance backend system** that integrates a **multi-layer caching architecture** with **blockchain-based immutable audit logging**.

Traditional caching systems dramatically improve application performance, but they lack any verifiable, tamper-proof mechanism for tracking data access and modifications. This project bridges that gap by recording all critical system operations on an **Ethereum smart contract**, providing a transparent and immutable audit trail — without sacrificing the performance benefits of caching.

> Built as part of a **Master's Thesis** to explore the practical integration of blockchain technology into modern backend systems.

---

## ❗ Problem Statement

Modern backend systems face a fundamental trade-off:

| Challenge | Description |
|---|---|
| **Performance** | Repeated database queries cause high latency at scale |
| **Auditability** | Traditional logs are mutable and can be tampered with |
| **Trust** | No verifiable proof of what data was accessed or modified, and when |
| **Scalability** | Centralized audit systems become bottlenecks |

This project solves all four by combining **multi-layer caching** for performance with **blockchain ledger logging** for immutable auditability.

---

## 🏗 System Architecture

The application follows a **layered architecture** where each layer has a distinct responsibility:

```
┌─────────────────────────────────────┐
│           Client (HTTP)             │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│       Spring Boot REST API          │  ← Handles incoming requests
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│          Service Layer              │  ← Business logic & cache coordination
└──────┬───────────────────┬──────────┘
       │                   │
       ▼                   ▼
┌─────────────┐    ┌───────────────┐
│  L1 Cache   │    │   L2 Cache    │  ← Cache-first retrieval strategy
│ (Local JVM) │    │    (Redis)    │
└──────┬──────┘    └───────┬───────┘
       │                   │
       └─────────┬─────────┘
                 │ (cache miss)
                 ▼
┌─────────────────────────────────────┐
│         PostgreSQL Database         │  ← Source of truth
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   Ethereum Blockchain (Audit Log)   │  ← Immutable event ledger
└─────────────────────────────────────┘
```

### Data Retrieval Flow

When a client requests data, the system follows this resolution order:

```
Request → L1 Cache hit? ──YES──→ Return instantly (~1ms)
               │
               NO
               ▼
          L2 Redis hit? ──YES──→ Populate L1 → Return (~3ms)
               │
               NO
               ▼
         PostgreSQL query → Populate L1 + L2 → Return (~5ms)
               │
               ▼
     Log event to Blockchain (async)
```

---

## ✨ Key Features

- ⚡ **Two-level caching** — L1 (in-memory JVM) and L2 (Redis distributed) for maximum throughput
- 🔗 **Blockchain audit trail** — all data access events are recorded immutably on Ethereum
- 🛡️ **Tamper-resistant logging** — audit events cannot be altered or deleted retroactively
- 🔄 **Cache fallback strategy** — graceful degradation from L1 → L2 → database
- 🏗️ **Containerized infrastructure** — Docker Compose for Redis and supporting services
- 🧪 **Load tested** — benchmarked with Apache JMeter across multiple configurations
- 📐 **Clean layered architecture** — separation of concerns across controller, service, repository layers

---

## 🛠 Technology Stack

| Technology | Version | Purpose |
|---|---|---|
| **Java** | 21 | Primary backend language |
| **Spring Boot** | 3.x | Application framework |
| **Spring Data JPA** | — | ORM & database abstraction |
| **PostgreSQL** | 15+ | Persistent relational database |
| **Redis** | 7.x | Distributed L2 cache |
| **Ethereum** | — | Blockchain network for audit logging |
| **Solidity** | 0.8.x | Smart contract language |
| **Web3j** | 4.x | Java Ethereum integration library |
| **Docker / Compose** | — | Service containerization |
| **Apache JMeter** | 5.x | Performance & load testing |
| **Postman** | — | API development and testing |
| **Gradle** | — | Build automation |

---

## 📁 Project Structure

```
src/
└── main/
    ├── java/
    │   └── com.hasan.thesisProject/
    │       ├── blockchain/          # Web3j integration & smart contract wrappers
    │       ├── config/              # Redis, security, and app configuration beans
    │       ├── controller/          # REST API endpoint definitions
    │       ├── dto/                 # Request and response data transfer objects
    │       ├── entity/              # JPA entity classes (mapped to DB tables)
    │       ├── repository/          # Spring Data JPA repository interfaces
    │       ├── security/            # Authentication and authorization logic
    │       ├── service/             # Core business logic and cache coordination
    │       └── ThesisProjectApplication.java   # Application entry point
    │
    └── resources/
        └── application.yml          # Application configuration
```

### Package Responsibilities

| Package | Responsibility |
|---|---|
| `blockchain` | Wraps the deployed Solidity smart contract; handles async event logging via Web3j |
| `config` | Defines Spring beans: `RedisTemplate`, security filters, serializers |
| `controller` | Exposes REST endpoints; delegates to the service layer |
| `dto` | Decouples API contract from internal entity model |
| `entity` | JPA-mapped database models |
| `repository` | Data access interfaces using Spring Data JPA |
| `security` | Handles request authentication and access control |
| `service` | Orchestrates L1 cache, Redis, database fallback, and blockchain logging |

---

## 🗄 Caching Architecture

### L1 Cache — Local In-Memory Cache

The first cache layer lives directly inside the JVM heap using `ConcurrentHashMap`. It provides **sub-millisecond** access with no network overhead.

```java
private final Map<Long, Product> localCache = new ConcurrentHashMap<>();

public Product getProduct(Long id) {
    // L1 hit — return immediately
    if (localCache.containsKey(id)) {
        return localCache.get(id);
    }
    // ... fall through to L2
}
```

**Characteristics:**
- Extremely fast (no serialization, no network)
- Scoped to a single application instance
- Evicted on application restart

---

### L2 Cache — Redis Distributed Cache

The second cache layer uses **Redis** as a shared distributed cache, allowing multiple application instances to share cached state.

```java
@Bean
public RedisTemplate<String, ProductResponseDTO> redisTemplate(
        RedisConnectionFactory connectionFactory) {

    RedisTemplate<String, ProductResponseDTO> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);

    Jackson2JsonRedisSerializer<ProductResponseDTO> serializer =
            new Jackson2JsonRedisSerializer<>(ProductResponseDTO.class);

    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(serializer);
    template.afterPropertiesSet();
    return template;
}
```

**Characteristics:**
- Shared across all application instances
- Survives individual instance restarts
- Supports TTL-based eviction policies
- Slightly higher latency than L1 due to network call

---

### Cache Write Strategy

When data is fetched from PostgreSQL (cache miss), it is written back into **both** cache layers so subsequent requests are served from cache:

```
DB result → write to Redis (L2) → write to local map (L1) → return to client
```

---

## ⛓ Blockchain Audit Logging

Every significant data operation is asynchronously recorded on the Ethereum blockchain via a **Solidity smart contract**.

### What Gets Logged

| Event | Data Recorded |
|---|---|
| Product accessed | Product ID, user identifier, timestamp |
| Product created | Product details, user, timestamp |
| Cache miss (DB fallback) | Query type, timestamp |

### Smart Contract Design

The contract is written in **Solidity** and stores audit events as on-chain events, making them permanently visible and verifiable:

```solidity
// Simplified example of the audit contract structure
pragma solidity ^0.8.0;

contract AuditLog {
    event AuditEvent(
        address indexed caller,
        string action,
        uint256 productId,
        uint256 timestamp
    );

    function logEvent(string memory action, uint256 productId) public {
        emit AuditEvent(msg.sender, action, productId, block.timestamp);
    }
}
```

### Why Blockchain for Audit Logs?

| Property | Traditional Log File | Blockchain Audit Log |
|---|---|---|
| Tamper-proof | ❌ Can be edited/deleted | ✅ Immutable once written |
| Verifiable | ❌ Requires trust in the host | ✅ Publicly verifiable |
| Transparent | ❌ Opaque | ✅ Open ledger |
| Decentralized | ❌ Single point of failure | ✅ Distributed |

---

## 📡 REST API Reference

### Create a Product

```http
POST /products
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Mazda RX-7",
  "category": "CAR",
  "price": 44500.00,
  "stock": 321
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "name": "Mazda RX-7",
  "category": "CAR",
  "price": 44500.00,
  "stock": 321
}
```

---

### Get a Product by ID

```http
GET /products/{id}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "Mazda RX-7",
  "category": "CAR",
  "price": 44500.00,
  "stock": 321
}
```

> The system resolves this request through L1 → L2 → DB in order. The audit event is logged to the blockchain upon every access.

---

## 📊 Performance Results

System performance was benchmarked using **Apache JMeter** under equivalent load conditions across three configurations:

| Configuration | Description | Avg. Response Time |
|---|---|---|
| 🔴 Database Only | No caching; every request hits PostgreSQL | ~5 ms |
| 🟡 Redis Cache (L2) | Requests served from distributed Redis cache | ~3 ms |
| 🟢 Local Cache (L1) | Requests served from in-memory JVM cache | ~1 ms |

### Key Takeaways

- **L1 cache delivers a 5× improvement** in average response time compared to direct database access
- **Redis reduces latency by ~40%** over database-only access while supporting multi-instance deployments
- **Blockchain logging is performed asynchronously**, ensuring it introduces negligible latency to the request path

---

## 🚀 Getting Started

### Prerequisites

Ensure you have the following installed:

- Java 21+
- Docker & Docker Compose
- PostgreSQL 15+
- Gradle

---

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/thesisProject.git
cd thesisProject
```

---

### 2. Start Redis with Docker

```bash
docker-compose up -d
```

This starts a Redis instance on the default port `6379`.

---

### 3. Configure PostgreSQL

Create the database:

```sql
CREATE DATABASE thesis_db;
```

Update `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/thesis_db
    username: postgres
    password: your_password

  redis:
    host: localhost
    port: 6379

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

---

### 4. Run the Application

```bash
./gradlew bootRun
```

Or run `ThesisProjectApplication.java` directly from IntelliJ IDEA.

The application starts on: `http://localhost:8081`

---

### 5. Test the API

Use **Postman** or `curl`:

```bash
# Create a product
curl -X POST http://localhost:8081/products \
  -H "Content-Type: application/json" \
  -d '{"name": "Mazda RX-7", "category": "CAR", "price": 44500, "stock": 321}'

# Retrieve a product
curl http://localhost:8081/products/1
```

---

## 🧪 Running Performance Tests

1. Install and open **Apache JMeter**
2. Create a new **Test Plan** with the following elements:
   - `Thread Group` — set number of threads (e.g., 100 users, 10 loops)
   - `HTTP Request Sampler` — target `GET http://localhost:8081/products/{id}`
   - `Summary Report` listener
   - `View Results in Table` listener
3. Run the test and compare results across configurations (no cache / Redis / local cache)

---

## 🔭 Future Roadmap

- [ ] **Hyperledger Fabric integration** — enterprise-grade permissioned blockchain for private deployments
- [ ] **AI-powered anomaly detection** — ML model trained on audit logs to flag suspicious access patterns
- [ ] **Microservices migration** — decompose into independently deployable services
- [ ] **Off-chain storage optimization** — store large payloads off-chain (IPFS) with on-chain hash verification
- [ ] **Real-time monitoring dashboard** — live visualization of cache hit rates, DB fallback frequency, and blockchain event stream
- [ ] **Cache invalidation strategy** — event-driven invalidation using message queues (Kafka / RabbitMQ)

---

## 👨‍💻 Author

**Hasan Guliyev**
Master's Thesis — *Blockchain-Based Audit Logging for Multi-Layer Caching Systems*

---

## 📄 License

This project is released for **academic and educational purposes**.
Feel free to reference, fork, or build upon it with appropriate attribution.

---

<div align="center">
  <sub>Built with ☕ Java, ⛓ Ethereum, and a lot of cache invalidation headaches.</sub>
</div>

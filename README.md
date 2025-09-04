# Hospitality Event‑Driven Architecture (EDA) Demo — **Oktank Hotels**

A hands‑on demo for **Oktank Hotels** that simulates a **hotel reservation workflow** using an **event‑driven architecture**. It shows how a guest booking triggers downstream services (loyalty, housekeeping, notifications) via **Kafka**. Designed to run locally with **Docker Compose** and **Java 17 + Spring Boot**.

---

## Context
This repo was built as part of my **mandatory “Awesome Builder” program** during onboarding as a **Solutions Architect at AWS**. The objective was to demonstrate hands‑on ability to design and implement **event‑driven, cloud‑ready architectures** mapped to a hospitality scenario (**Oktank Hotels**) and to create reusable reference patterns (APIs, schemas, ADRs, IaC) applicable to enterprise customers.

---

## Scenario
1. **Reservation Service** exposes a REST endpoint (`POST /api/reservations`).
2. On create, it **publishes** `ReservationCreated` to Kafka topic `reservations.created`.
3. **Loyalty Service** **consumes** the event, computes points, and **emits** `LoyaltyUpdated` to `loyalty.updated`.
4. **Housekeeping Service** **consumes** the event and schedules a cleaning task (emits `housekeeping.tasks`).
5. **Notification Service** **consumes** both reservation and loyalty events and logs “email/SMS” messages (simulated).

This demo highlights **async/decoupled** integration, **schema discipline**, and **operational visibility**.

---

```
oktank-hospitality-eda-demo/
├─ README.md
├─ docker-compose.yml
├─ assets/
│  └─ oktank-logo.svg
├─ infra/
│  ├─ kafka-init/            # topic creation scripts
│  └─ terraform-aws/         # (optional) IaC for AWS MSK/EventBridge
├─ services/
│  ├─ reservation-service/   # :8181
│  ├─ loyalty-service/       # :8182
│  ├─ housekeeping-service/  # :8183
│  └─ notification-service/  # :8184
├─ schemas/
│  ├─ reservation-created.json
│  ├─ loyalty-updated.json
│  └─ housekeeping-task.json
├─ adr/
│  ├─ 0001-use-kafka-for-async-integration.md
│  ├─ 0002-event-schema-json.md
│  └─ 0003-outbox-vs-tx-producer.md
└─ .github/workflows/
   └─ ci.yml
```

---

## Quick Start

### Prerequisites
- **Docker** & **Docker Compose**
- **JDK 17** (e.g., Temurin/Corretto/Zulu)
- **Maven 3.9+**

### 1) Start Kafka stack
```bash
docker compose up -d
# Services:
# - Zookeeper :2181
# - Kafka     :9092 (PLAINTEXT)
# - Kafka UI  :8080 (open http://localhost:8080)
```

### 2) Build & run each service (new terminal per service)
From the repo root:
```bash
# Reservation Service (port 8181)
cd services/reservation-service
mvn spring-boot:run

# Loyalty Service (port 8182)
cd ../loyalty-service
mvn spring-boot:run

# Housekeeping Service (port 8183)
cd ../housekeeping-service
mvn spring-boot:run

# Notification Service (port 8184)
cd ../notification-service
mvn spring-boot:run
```


### 3) Create a reservation
Use the endpoint your service exposes (`/api/reservations`). Example payload includes **all fields** used by downstream services so nothing is `null`:

```bash
curl -i -X POST "http://localhost:8181/api/reservations"   -H "Content-Type: application/json"   -d '{
        "reservationId": "r-3001",
        "guestId": "g-77",
        "hotelId": "h-001",
        "roomType": "DELUXE",
        "checkIn": "2021-05-10",
        "checkOut": "2021-05-14",
        "amount": 420.00,
        "currency": "USD",
        "createdAt": "2020-05-10T23:05:00Z"
      }'
```

**Expected**
- `reservation-service` logs **produced** `ReservationCreated` (keyed by `reservationId`).
- `loyalty-service`, `housekeeping-service`, `notification-service` **consume** and log actions.
- Inspect topics via **Kafka UI** at <http://localhost:8080> (e.g., `reservations.created`, `loyalty.updated`).

---

##  Service Ports & Endpoints
- **Reservation**: `http://localhost:8181` → `POST /reservations` (or `/api/reservations`)
- **Loyalty**: `http://localhost:8182` (no REST; Kafka consumer/producer)
- **Housekeeping**: `http://localhost:8183` (no REST; Kafka consumer)
- **Notification**: `http://localhost:8184` (no REST; Kafka consumer)




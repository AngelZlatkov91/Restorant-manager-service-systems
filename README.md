# 🍽️ Restaurant Service Manager System

A microservices-based system for managing restaurant operations including order processing, kitchen display, menu management, and inventory control. Built with Java (Spring Boot), Node.js, Apache Kafka, MySQL, and integrated with a kitchen printer (Epson TM-T88V).

## 🧱 Architecture Overview

This system is built using microservices communicating asynchronously through Kafka. Each service is independently deployable, scalable, and manages its own domain logic and persistence.

### Key Components

| Service                 | Technology           | Description |
|------------------------|----------------------|-------------|
| **Order Service**      | Java, Spring Boot    | Handles order creation, update, status tracking, and sends kitchen items via Kafka. |
| **Menu Service**       | Java, Spring Boot    | Manages restaurant menu (CRUD for dishes, categories, etc). |
| **Kitchen Display**    | Node.js, Kafka       | Receives orders from Kafka, displays them on screen and optionally prints them via a connected Epson TM-T88V printer. |
| **Inventory Service**  | Java, Spring Boot    | Listens to order events and manages ingredient stock levels accordingly. *(In progress)* |
| **Kafka Broker**       | Apache Kafka (Docker)| Central event hub for asynchronous communication between services. |

---

## 🔄 Communication Flow

```plaintext
Client (Tablet / POS)
    ↓ REST
[Order Service]
    ↓ Kafka (kitchen-orders topic)
[Kitchen Display Service]
    → Display on screen / Print via Epson TM-T88V

    ↓ Kafka (inventory-events topic)
[Inventory Service]

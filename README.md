# 🍽️ Restaurant Service Manager System

A **microservices-based system** for managing restaurant operations — including order processing, kitchen display, menu management, and inventory control.  
Built with **Java (Spring Boot)**, **Node.js**, **Apache Kafka**, **MySQL**, and an **Electron-based React UI client** for order management.  
The system is designed for scalability, modularity, and real-time communication between services.

---

## 🧱 Architecture Overview

This system follows a **microservice architecture**, where each service is independently deployable and communicates asynchronously through **Apache Kafka**.  
Each microservice manages its own database and domain logic.

### 🧩 Key Components

| Service / Module                         | Technology Stack                   | Description |
|------------------------------------------|------------------------------------|-------------|
| **Order Service**                        | Java, Spring Boot, Kafka           | Handles order creation, updates, and status tracking. Publishes events to Kafka for kitchen and inventory updates. |
| **Menu Service**                         | Java, Spring Boot, MySQL           | Manages restaurant menu — CRUD for categories, dishes, and prices. |
| **Kitchen Display Service**              | Node.js, Kafka, Epson SDK          | Listens to order events via Kafka, displays active kitchen orders, and prints them to an Epson TM-T88V POS printer. |
| **Inventory Service**                    | Java, Spring Boot, Kafka, MySQL    | Listens to order events and updates ingredient quantities accordingly. *(In progress)* |
| **React + Electron UI Client (Order UI)**| React.js, Electron.js, JavaScript  | A modern desktop client interface used by restaurant staff to manage orders, select menu items, process payments, and communicate with backend microservices securely. |
| **Kafka Broker**                         | Apache Kafka (Dockerized)          | Event streaming platform used for asynchronous communication between all services. |

---

## 🔄 Communication Flow

```plaintext
[Electron React Client]
    ↓ REST API (JWT-secured)
[Order Service]
    ↓ Kafka (topic: kitchen-orders)
[Kitchen Display Service]
    → Displays order / Prints receipt

    ↓ Kafka (topic: inventory-events)
[Inventory Service]
    → Updates ingredient stock levels

# BlinkFlow

BlinkFlow is an automation platform that enables users to create and connect webhooks, define triggers, and automate tasks — all without writing a single line of code.

Live Demo: [https://blinkflow.akashindulkar.in](https://blinkflow.akashindulkar.in)

---

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Microservices](#microservices)
- [Setup](#setup)
- [API Endpoints](#api-endpoints)
  - [Primary Backend](#primary-backend)
  - [Flowrun Listener](#flowrun-listener)
- [Author](#author)

---

## Overview

BlinkFlow provides a low-code way for users to automate workflows between applications.  
It allows users to:
- Create and manage webhook-based flows.
- Define triggers and actions.
- Automate data exchange and operations between services.

The platform leverages a distributed microservices architecture for reliability, scalability, and fault tolerance.

---

## Architecture

The project follows an event-driven microservice architecture built around Kafka for inter-service communication.

- **Primary Backend** — Manages authentication, user accounts, and flow creation/updates.  
- **Flowrun Listener** — Receives external webhook triggers and writes them into the outbox table.  
- **Flowrun Producer** — Reads flowrun events from the outbox and publishes them to Kafka.  
- **Flowrun Executor** — Consumes Kafka events and executes the corresponding actions.  

Each service runs independently in a Docker container, communicating through Kafka, Redis, and MySQL.

![Architecture Diagram](https://res.cloudinary.com/dadualj4l/image/upload/v1757300694/BlinkFlow-architecture_bsq1at.png)

---

## Tech Stack

**Frontend**
- React.js + TypeScript
- React Flow

**Backend**
- Spring Boot (Java)
- Kafka
- Redis
- MySQL

**Infrastructure**
- Docker & Docker Compose
- AWS EC2
- Nginx

**AI/LLM Integration**
- GroqAPI  
- OpenAI gpt-oss-120b

---

## Microservices

1. **Primary Backend**  
   Handles authentication, user management, and flow CRUD operations.

2. **Flowrun Listener**  
   Receives external webhook triggers, formats metadata using OpenAI gpt-oss-120b and logs them to the `flowrun_outbox` table.

3. **Flowrun Producer**  
   Reads from the `flowrun_outbox` table and publishes events to Kafka.

4. **Flowrun Executor**  
   Consumes Kafka messages and executes defined actions.

This design follows the **Transactional Outbox Pattern**, ensuring event reliability and consistency between database writes and Kafka messages.

---

## Setup

You can run BlinkFlow using Docker or manually.

### 1. Clone the repository
```bash
git clone https://github.com/akash-indulkar/BlinkFlow.git
cd BlinkFlow
```

### 2. Configure environment variables
Copy `.env.example` and update the required values:

```bash
cp .env.example .env
```

### 3. Run using Docker Compose
```bash
docker-compose up --build
```

This will start:
- Frontend (React)
- Primary Backend
- Flowrun Listener, Producer, and Executor
- Kafka
- Redis

### 4. Access the application
Once running:
- **Frontend**: http://localhost:5173
- **Backend**: http://localhost:8080
---

## API Endpoints

### Base URLs

- **Primary Backend:** `http://localhost:8080`
- **Flowrun Listener:** `http://localhost:8082`

### Primary Backend

#### User Routes

- `POST /user/signup` → Register a new user and send an OTP to the provided email.
- `POST /user/signup/verify` → Verify OTP and create a new user account. Returns a JWT token.
- `POST /user/login` → Authenticate user credentials. Returns a JWT token.
- `POST /user/login/forget` → Request an OTP for password reset.
- `POST /user/login/reset` → Verify OTP and reset the user password.
- `POST /user/me` → Get the authenticated user’s profile. Requires Authorization header.

#### Available Actions Routes

- `GET /actions/availableactions` → Retrieve a list of all available actions.

#### Available Triggers Routes

- `GET /triggers/availabletriggers` → Retrieve a list of all available triggers.

#### Flow Routes

- `POST /flow/create` → Create a new automation flow.
- `DELETE /flow/:flowID` → Delete a specific flow by its ID.
- `PUT /flow/:flowID` → Update an existing flow by its ID.
- `GET /flow/` → Retrieve all flows created by the authenticated user.
- `GET /flow/:flowID` → Retrieve a specific flow by its ID.

#### Flowrun Routes

- `GET /flowrun/logs/:flowID` → Retrieve execution logs for a specific flow.

### Flowrun Listener

- `POST /flowrun/initiate/:userID/:flowID` → Trigger execution of a flow via webhook event.

## Author

**Akash Indulkar**

- GitHub: [@akash-indulkar](https://github.com/akashindulkar)
- LinkedIn: [@akash-indulkar](https://linkedin.com/in/akash-indulkar)
- Portfolio: [https://akashindulkar.in](https://akashindulkar.in)
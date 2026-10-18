# Hospital Operational Status Monitor

Monitoring system for the critical platforms of **E.S.E. Hospital Universitario San Rafael**. Final project for the *Service-Oriented Development* course.

It centralizes the availability of key services (**Admissions, Laboratory and Medical Records**) and calculates the hospital's overall operational severity level in real time.

<!-- Add a screenshot here: ![Dashboard](docs/dashboard.png) -->

## Tech Stack

| Area | Technology |
|---|---|
| Language | Java 21 (Eclipse Temurin) |
| Framework | Spring Boot, Spring MVC, Thymeleaf |
| Database | PostgreSQL 16 |
| Infrastructure | Docker, Docker Compose |
| Build | Gradle |
| Code quality | SonarQube |

## Architecture

Built with **Hexagonal Architecture (Ports & Adapters)** to keep business logic independent from frameworks and infrastructure.

```
src/main/java
├── core/      # Entities, ports (interfaces), services and DTOs. No external dependencies.
├── adapter/   # Persistence (JPA repositories) and REST adapters.
└── ui/        # Spring MVC controllers and Thymeleaf views.
```

**Why this design:**
- **Decoupled core:** business rules know nothing about the database or the UI.
- **Replaceable infrastructure:** the persistence layer can be swapped (e.g. for NoSQL or in-memory) without changing the core.
- **Testable:** services can be unit tested with their ports mocked.

## Getting Started

### Prerequisites
- Java JDK 21
- Docker and Docker Compose

### 1. Clone the repository
```bash
git clone https://github.com/Sebastian320CA/YOUR_REPO.git
cd YOUR_REPO
```

### 2. Start the database
```bash
docker compose up -d
```
PostgreSQL runs on port `5432` with the database name `bd_san_rafael`.

### 3. Start the simulated health APIs
Container provided by the course to simulate latency, errors and async responses from the hospital network:
```bash
docker run --name servicios_health_containers \
  -p 8081:8081 -p 8082:8082 -p 8083:8083 -p 8084:8084 -p 8085:8085 \
  harrysoler/rnghealthapis
```

### 4. Run the application
```bash
./gradlew bootRun
```
Then open `http://localhost:8080/`.

> **Tip (IntelliJ IDEA):** in `Settings > Build Tools > Gradle`, set *Build and run using* and *Run tests using* to **IntelliJ IDEA** for faster builds.

## Code Quality

Static analysis with SonarQube (running at `localhost:9000`):
```bash
./gradlew sonar \
  -Dsonar.projectKey=SanRafael \
  -Dsonar.projectName='SanRafael' \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=$SONAR_TOKEN
```

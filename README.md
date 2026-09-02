# Clerk

**Do not deploy this anywhere public.**

A deliberately vulnerable software license management API, built as a training target for OWASP API Top 10 (2023).

Application is built using Spring Boot 4 and Java 25.

## Running the application

```bash
mvn spring-boot:run
```

Or with Docker:

```bash
make        # mvn package + docker compose build
make run    # docker compose up
```

The API listens on `http://localhost:19090`. The H2 console is at `/h2-console` and actuator endpoints are fully exposed at `/actuator`.

## User accounts

| User | Password | Role |
|------|----------|------|
| alice | password1 | USER |
| bob | hunter2 | USER |
| admin | admin123 | ADMIN |

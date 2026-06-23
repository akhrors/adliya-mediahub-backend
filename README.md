# Adliya MediaHub — Backend

Spring Boot 3.2 + PostgreSQL + Liquibase + JWT

## Stack
- Java 17
- Spring Boot 3.2.5
- Spring Security + JWT (jjwt 0.11.5)
- Spring Data JPA + PostgreSQL
- Liquibase (db migrations)
- MapStruct + Lombok
- Apache POI (Word/Excel reports)
- iText7 (PDF reports)
- Telegram Bot API
- Springdoc OpenAPI (Swagger UI)

## Setup

1. Create PostgreSQL database:
```sql
CREATE DATABASE adliya_mediahub;
```

2. Set env vars (or edit application-dev.yml):
```
DB_URL=jdbc:postgresql://localhost:5432/adliya_mediahub
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=your-secret-key-min-32-chars
TELEGRAM_BOT_TOKEN=your-telegram-bot-token
```

3. Run:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

4. API Docs: http://localhost:8080/swagger-ui.html

## Package Structure
```
uz.adevs.mediahub
├── config/          # SecurityConfig, JwtAuthFilter
├── constants/       # Enums: RoleName, MediaRequestStatus, EventStatus, ...
├── controller/      # REST controllers (v1)
├── dto/             # request/ and response/ DTOs
├── exception/       # ResourceNotFoundException, GlobalExceptionHandler
├── model/           # JPA entities
├── repository/      # Spring Data JPA repositories
├── service/         # Business logic
└── utils/           # JwtUtils
```

## Modules
| Module | Controller |
|--------|-----------|
| Auth (login/refresh) | `/api/v1/auth` |
| Users | `/api/v1/users` |
| Organizations | `/api/v1/organizations` |
| OAV So'rovlari | `/api/v1/media-requests` |
| Tadbirlar | `/api/v1/events` |
| Faoliyatni yoritish | `/api/v1/coverage-materials` |
| Monitoring | `/api/v1/monitoring` |
| Bildirishnomalar | `/api/v1/notifications` |
| Reyting qoidalari | `/api/v1/ratings` |
| Kontent kalendar | `/api/v1/content-calendar` |
| Dashboard | `/api/v1/dashboard` |

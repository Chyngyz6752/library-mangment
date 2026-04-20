# Library Management System

Spring Boot 3.3 API для управления библиотечным фондом: книги, экземпляры, авторы, категории, читатели, выдачи/возвраты, штрафы за просрочку.

## Возможности

- REST API для Book, BookCopy, Author, Category, Member, Loan
- Пагинация, сортировка, поиск по названию
- Валидация DTO (ISBN, email, телефон, границы полей)
- Единый `ErrorResponseDto` + обработка validation/integrity/optimistic-lock
- Оптимистические блокировки (`@Version`) + пессимистический lock на `BookCopy` при выдаче
- Авторасчёт штрафа за просрочку, scheduled-job перевода в `OVERDUE` (ежедневно 03:00)
- Spring Security (HTTP Basic): чтение открыто, модификации — только ADMIN
- Swagger UI + Spring Boot Actuator (`/actuator/health`)
- Dashboard со статистикой (`/dashboard`)
- Docker Compose для локального запуска + GitHub Actions CI
- Тесты сервисного слоя (JUnit 5 + Mockito) и smoke-тест контекста на H2

## Технологии

Java 17 · Spring Boot 3.3 · Spring Data JPA · Spring Security · PostgreSQL · Lombok · Springdoc OpenAPI · H2 (тесты)

## Быстрый старт через Docker Compose

```bash
docker compose up --build
```

Приложение будет на `http://localhost:8080`. По умолчанию: логин/пароль `admin/admin` (изменить через `ADMIN_USERNAME`, `ADMIN_PASSWORD`).

## Локальный запуск

### Требования
- JDK 17+
- Maven 3.9+
- PostgreSQL 14+

### Запуск

```bash
# Переменные окружения (пример)
export DB_URL=jdbc:postgresql://localhost:5432/library_db
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
export ADMIN_PASSWORD=change-me

mvn spring-boot:run
```

## UI-страницы

| URL             | Назначение                      |
|-----------------|---------------------------------|
| `/`             | Главная                         |
| `/books`        | Каталог книг с поиском          |
| `/register`     | Регистрация читателя            |
| `/dashboard`    | Сводная статистика              |
| `/swagger-ui.html` | OpenAPI UI                   |

## Основные REST-эндпоинты

```
GET    /api/books?query=&page=&size=          — каталог с пагинацией
POST   /api/books                              — создать (ADMIN)
PUT    /api/books/{id}                         — обновить (ADMIN)
DELETE /api/books/{id}                         — удалить (ADMIN)

GET    /api/book-copies
POST   /api/book-copies                        — (ADMIN)
DELETE /api/book-copies/{id}                   — (ADMIN)

GET    /api/authors  POST/PUT/DELETE           — (ADMIN для модификаций)
GET    /api/categories  POST/PUT/DELETE        — (ADMIN для модификаций)

POST   /api/members                            — публичная регистрация
PATCH  /api/members/{id}                       — (ADMIN)
DELETE /api/members/{id}                       — деактивация (ADMIN)

GET    /api/loans?status=ACTIVE|OVERDUE|...
POST   /api/loans/borrow                       — требует аутентификации
POST   /api/loans/return?loanId=              — требует аутентификации

GET    /api/stats                              — публичные счётчики
```

## Примеры

Взять книгу:
```bash
curl -u admin:admin -X POST http://localhost:8080/api/loans/borrow \
  -H "Content-Type: application/json" \
  -d '{"memberId":1,"copyId":1}'
```

Вернуть:
```bash
curl -u admin:admin -X POST "http://localhost:8080/api/loans/return?loanId=1"
```

## Бизнес-правила

- Длительность выдачи: `library.loan.duration-days` (по умолчанию 14)
- Штраф за день просрочки: `library.loan.fine-per-day` (по умолчанию 0.50)
- Максимум займов на читателя — поле `Member.maxAllowedLoans`
- При попытке выдать недоступный экземпляр — `409 Conflict`
- При просроченном займе возврат допускается и начисляет штраф

## Тесты

```bash
mvn test
```

## Переменные окружения

| Имя              | По умолчанию                                   |
|------------------|------------------------------------------------|
| `SERVER_PORT`    | `8080`                                         |
| `DB_URL`         | `jdbc:postgresql://localhost:5432/library_db`  |
| `DB_USERNAME`    | `postgres`                                     |
| `DB_PASSWORD`    | `postgres`                                     |
| `DDL_AUTO`       | `update`                                       |
| `ADMIN_USERNAME` | `admin`                                        |
| `ADMIN_PASSWORD` | `admin`                                        |
| `LOAN_DURATION_DAYS` | `14`                                       |
| `LOAN_FINE_PER_DAY`  | `0.50`                                     |

# Library Management System API

This is a Spring Boot application for a Library Management System, providing a RESTful API to manage books, members, and loans.

## Features
- **Clean Architecture**: Controller → Service → Repository
- **REST API**: Endpoints for managing books, members, and loans.
- **Database**: PostgreSQL
- **Validation**: Input DTOs are validated.
- **Error Handling**: Global exception handler for consistent error responses.
- **Documentation**: OpenAPI (Swagger UI) enabled.

## Technologies Used
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Maven
- PostgreSQL
- Lombok
- Springdoc OpenAPI (for Swagger UI)

## Getting Started

### Prerequisites
- JDK 17 or later
- Maven 3.6.3 or later
- PostgreSQL database running

### Database Setup
1.  Create a PostgreSQL database.
2.  Update the `src/main/resources/application.properties` file with your database credentials:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```

### Running the Application
1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd library-management
    ```

2.  **Build the project:**
    ```bash
    mvn clean package
    ```

3.  **Run the application:**
    ```bash
    java -jar target/library-0.0.1-SNAPSHOT.jar
    ```

The application will start on `http://localhost:8080`.

## API Documentation
Once the application is running, you can access the Swagger UI for API documentation and testing at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Example API Usage (cURL)

### Borrow a Book
This request borrows a book copy (`copyId=1`) for a member (`memberId=1`).

```bash
curl -X POST "http://localhost:8080/api/loans/borrow" \
     -H "Content-Type: application/json" \
     -d '{"memberId": 1, "copyId": 1}'
```

### Return a Book
This request returns a book based on the `loanId`. Assuming the borrow request above created a loan with `loanId=1`.

```bash
curl -X POST "http://localhost:8080/api/loans/return?loanId=1"
```

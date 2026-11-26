# 📚 Spring Boot REST API — Book Management

A production-ready REST API built with Spring Boot 3, JPA, and H2 (in-memory database).

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+

### Run the app
```bash
mvn spring-boot:run
```

Server starts at: `http://localhost:8080`

H2 Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:bookdb`
- Username: `sa` | Password: *(empty)*

---

## 📡 API Endpoints

### Base URL: `/api/v1/books`

| Method | Endpoint              | Description              |
|--------|-----------------------|--------------------------|
| GET    | `/`                   | Get all books            |
| GET    | `/{id}`               | Get book by ID           |
| GET    | `/isbn/{isbn}`        | Get book by ISBN         |
| GET    | `/search?keyword=...` | Search by title/author   |
| GET    | `/author?name=...`    | Filter by author         |
| GET    | `/year/{year}`        | Filter by published year |
| POST   | `/`                   | Create a new book        |
| PUT    | `/{id}`               | Update a book            |
| DELETE | `/{id}`               | Delete a book            |

---

## 📋 Sample Requests

### Create a Book
```http
POST /api/v1/books
Content-Type: application/json

{
  "title": "Effective Java",
  "author": "Joshua Bloch",
  "isbn": "978-0134685991",
  "price": 45.99,
  "publishedYear": 2018,
  "description": "Best practices for Java programming"
}
```

### Update a Book
```http
PUT /api/v1/books/1
Content-Type: application/json

{
  "title": "Effective Java (3rd Edition)",
  "author": "Joshua Bloch",
  "isbn": "978-0134685991",
  "price": 49.99,
  "publishedYear": 2018
}
```

### Search
```http
GET /api/v1/books/search?keyword=java
GET /api/v1/books/author?name=Bloch
GET /api/v1/books/year/2018
```

---

## 📦 Response Format

All responses are wrapped in a standard envelope:
```json
{
  "success": true,
  "message": "Book retrieved successfully",
  "data": { ... },
  "timestamp": "2024-01-01T12:00:00"
}
```

---

## 🏗️ Project Structure

```
src/main/java/com/example/bookapi/
├── BookApiApplication.java       # Entry point
├── controller/
│   └── BookController.java       # REST endpoints
├── service/
│   ├── BookService.java          # Interface
│   └── BookServiceImpl.java      # Business logic
├── repository/
│   └── BookRepository.java       # JPA repository
├── model/
│   └── Book.java                 # JPA entity
├── dto/
│   ├── BookDTO.java              # Data transfer object
│   └── ApiResponse.java          # Response wrapper
└── exception/
    ├── BookNotFoundException.java
    ├── DuplicateIsbnException.java
    └── GlobalExceptionHandler.java
```

---

## 🗄️ Switch to MySQL/PostgreSQL

Replace H2 in `pom.xml`:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

---

## 🧪 Run Tests
```bash
mvn test
```

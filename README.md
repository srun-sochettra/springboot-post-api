# SpringHomework002

A simple Spring Boot REST API for managing blog posts with in-memory storage.

## Tech Stack

- Java 21
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Validation (Jakarta Bean Validation)
- springdoc OpenAPI + Swagger UI
- Lombok
- Maven

## Project Features

- Create a post
- Get all posts (with optional pagination)
- Get a post by ID
- Search a post by title
- Search posts by author
- Search posts by one or multiple tags
- Update a post by ID
- Delete a post by ID
- Standardized API response envelope (`ApiResponse<T>`)

## API Base URL

When running locally:

- `http://localhost:9090`
- Base endpoint: `http://localhost:9090/api/v1/posts`

## API Documentation

- Swagger UI: `http://localhost:9090/swagger-ui.html`

## Request Validation

`PostRequest` requires:

- `title`: not blank
- `content`: not blank
- `author`: not blank
- `tags`: not empty

## Response Format

Most endpoints return this response shape:

```json
{
  "message": "string",
  "payload": {},
  "status": "OK",
  "time": "2026-05-01T20:30:00"
}
```

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/posts` | Create a new post |
| `GET` | `/api/v1/posts` | Get all posts |
| `GET` | `/api/v1/posts?page=1&size=5` | Get posts with pagination (1-based page) |
| `GET` | `/api/v1/posts/{id}` | Get a post by ID |
| `GET` | `/api/v1/posts/search/title?title=...` | Search a post by title |
| `GET` | `/api/v1/posts/search/author?author=...` | Search posts by author |
| `GET` | `/api/v1/posts/search/tags?tags=java&tags=spring` | Search posts by multiple tags |
| `PUT` | `/api/v1/posts/{id}` | Update a post by ID |
| `DELETE` | `/api/v1/posts/{id}` | Delete a post by ID |

## Example Request Body

Use this JSON for `POST /api/v1/posts` and `PUT /api/v1/posts/{id}`:

```json
{
  "title": "Spring Boot Basics",
  "content": "This post explains the fundamentals of Spring Boot.",
  "author": "Alice",
  "tags": ["spring", "java", "backend"]
}
```

## Getting Started

### Prerequisites

- Java 21
- Maven 3.9+

### Run Locally

1. Clone the repository
2. Navigate to the project directory
3. Run the application:

```bash
mvn spring-boot:run
```

The app starts on port `9090`.

### Build

```bash
mvn clean package
```

### Run Tests

```bash
mvn test
```

## Notes

- Data is stored in memory (`InMemoryPostRepository`), so it resets when the application restarts.
- Pagination in `GET /api/v1/posts` is applied only when both `page` and `size` are provided.


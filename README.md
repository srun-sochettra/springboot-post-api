# Spring Boot Post API

**A layered REST coursework project for blog-post CRUD, search and optional pagination.**

Built with Java 21 and Spring Boot 4.0.6, this application separates HTTP handling, service logic and in-memory data access. Request DTOs apply Jakarta validation, while controller responses use a generic envelope. A Postman collection is included for manual exploration.

This is a learning API, not a complete publishing platform. Records and ID allocation reset when the process restarts. No database, login, author ownership or publication workflow is implemented.

## Features

- Create, retrieve, replace and delete posts.
- Find the first case-insensitive full-title match.
- Find all case-insensitive full-author matches.
- Search by one or more tags using **any-match**, not all-match, semantics.
- Optionally paginate the collection using one-based page numbers.
- Validate create/update requests and expose Swagger UI configuration.

## Local development

The POM targets Java 21 and declares Spring Boot 4.0.6, Spring MVC, validation, Lombok and Springdoc 3.0.2. The supplied Maven wrapper selects Maven 3.9.14. These are source declarations, not a freshly tested dependency set.

Suggested commands from the original repository root:

### Windows PowerShell

```powershell
.\mvnw.cmd spring-boot:run
```

### POSIX shell

```sh
sh mvnw spring-boot:run
```

Initial use may download Maven and dependencies. No database or application environment variables are required by the supplied configuration. The server is configured for port 9090, with [local Swagger UI](http://localhost:9090/swagger-ui.html). These commands and the local endpoint were not exercised during this refresh.

## Endpoints

| Method | Path | Behaviour |
| --- | --- | --- |
| POST | `/api/v1/posts` | Create; HTTP 201 |
| GET | `/api/v1/posts` | List all, or paginate when both page and size are supplied; HTTP 200 |
| GET | `/api/v1/posts/{id}` | Find by ID; HTTP 200 or 404 |
| GET | `/api/v1/posts/search/title?title=Example` | First full-title match; HTTP 200 or 404 |
| GET | `/api/v1/posts/search/author?author=Alex` | Full-author matches; HTTP 200 or 404 |
| GET | `/api/v1/posts/search/tags?tags=java&tags=spring` | Posts matching either requested tag; HTTP 200 or 404 |
| PUT | `/api/v1/posts/{id}` | Replace editable fields; HTTP 200 or 404 |
| DELETE | `/api/v1/posts/{id}` | Remove; HTTP 200 or 404, with null payload |

Title/author matching ignores case but does not trim or perform substring matching. Duplicate titles are permitted. Search endpoints return 404 with a null payload when nothing matches, unlike an empty collection response.

### Pagination

Use `page=1&size=5` for the first five records in insertion order. Pagination applies only when **both** parameters are present; providing just one returns the full list. The controller clamps values below 1 to 1. A page beyond the end returns an empty list. Responses contain no total-count or page metadata.

There is no maximum size or overflow protection around integer index arithmetic. Extreme values require a separate correctness fix and regression check.

### Request body

Illustrative synthetic input for POST and PUT, not a captured execution:

```json
{
  "title": "Learning Spring",
  "content": "A sample post for local API practice.",
  "author": "Alex",
  "tags": ["java", "spring"]
}
```

Title, content and author must be non-blank. Tags must be a non-empty list, but individual tag elements have no non-blank/null constraint. No maximum text/list lengths are supplied. PUT requires the complete validated request, preserves the existing ID and creationDate, and replaces title/content/author/tags. It is not a partial update.

## Architecture

```text
PostController -> PostService -> PostServiceImpl -> PostRepository
                                                   |
                                          InMemoryPostRepository
```

The in-memory implementation is a package-private class inside [PostRepository.java](src/main/java/com/example/springhomework002/repository/PostRepository.java), not a separate file or JPA repository. It uses an ArrayList and an ordinary incrementing Integer counter without synchronisation. Copies of lists do not make the mutable post objects thread-safe.

[PostRequest.java](src/main/java/com/example/springhomework002/model/request/PostRequest.java) separates input from the stored model. The service assigns creationDate on insertion. Controller envelopes contain message, payload, HttpStatus status and a LocalDateTime response timestamp. These times have no explicit timezone and are not execution durations.

No global exception handler is supplied. Framework-level validation, parsing and parameter-binding errors are not explicitly converted into this envelope.

## Manual requests and checks

Import [the existing Postman collection](postman/SpringHomework002.postman_collection.json) and review its baseUrl variable, configured for the local port-9090 server. It contains example requests, not automated assertions or verified results. Update/delete examples assume ID 1 exists.

Suggested Windows checks:

```powershell
.\mvnw.cmd test
.\mvnw.cmd package
```

POSIX equivalents use `sh mvnw test` and `sh mvnw package`. The supplied Java test is a contextLoads smoke test without endpoint assertions. No Maven download, compilation, test, server startup or HTTP request was performed during this documentation refresh.

See [CONTRIBUTING.md](CONTRIBUTING.md), [SECURITY.md](SECURITY.md) and [refresh notes](docs/repository-refresh.md). This overlay changes documentation only.

## Licence

The existing [MIT License](LICENSE) is retained unchanged. Its copyright line states 2026 without naming a holder; this refresh does not fill in legal attribution.

# GitHub Repositories API

![Java 17](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot 3.5](https://img.shields.io/badge/Spring%20Boot-3.5.x-brightgreen.svg)
![Build](https://img.shields.io/badge/build-Maven-blueviolet)

> **GitHub Repositories API** is a Spring Boot 3.5 microservice that exposes endpoints to fetch public repositories of GitHub users, stores them in a **PostgreSQL** database, and allows CRUD operations on saved results. JSON is the default response format.

---

## 🚀 Features

- `GET /repositories/{username}` – fetches GitHub repos & branches, saves to DB
- `GET /repositories/database` – retrieves all saved entries from PostgreSQL
- `POST /repositories/database` – manually create a DB entry
- `PUT /repositories/database/{id}` – update a saved DB entry by ID
- `DELETE /repositories/database/{id}` – delete entry by ID
- **Rate-limit aware** – handles GitHub 403/429 properly
- **OpenAPI 3.1** – Swagger UI at `/swagger-ui/index.html`
- **Docker-ready**

---

## ⚙️ Tech Stack

- Java 17
- Spring Boot 3.5
- PostgreSQL (via Spring Data JPA)
- Maven
- Lombok, Jakarta Validation
- Swagger/OpenAPI 3.1

---

## 🔧 Configuration

| Property                         | Example                     | Description                    |
|----------------------------------|-----------------------------|--------------------------------|
| `server.port`                    | `8081`                      | Port where API is served       |
| `github-api.url`                | `https://api.github.com`    | GitHub REST API base URL       |
| `spring.datasource.url`         | `jdbc:postgresql://...`     | PostgreSQL connection string   |
| `spring.datasource.username`    | `your_db_user`              | DB username                    |
| `spring.datasource.password`    | `your_db_pass`              | DB password                    |
| `spring.jpa.hibernate.ddl-auto` | `update`                    | Schema update strategy         |

---

## 📬 API Reference

### `GET /repositories/{username}`

Fetches all public repositories and branches of a GitHub user, maps them to domain objects (`GitHubResult`, `Owner`, etc.), saves to database, and returns the result.

#### Example:
```bash
curl -X GET http://localhost:8081/repositories/vvhoffmann
```

#### Example Response `200 OK` (JSON)
```json
[
  {
    "name": "awesome-project",
    "owner": "vvhoffmann",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "a1b2c3d4e5..."
      }
    ]
  }
]
```
---

### `GET /repositories/database`

Retrieves all `DatabaseResult` entries from the PostgreSQL database (paged).

#### Example:
```bash
curl http://localhost:8081/repositories/database
```

---

### `POST /repositories/database`

Creates a new database entry manually via request DTO.

#### Example Request Payload:
```json
{
  "owner": "custom-user",
  "name": "custom-name"
}
```

---

### `PUT /repositories/database/{id}`

Updates a specific `DatabaseResult` entry by ID.

#### Example Request Payload:
```json
{
  "owner": "custom-user",
  "name": "custom-name"
}
```

---

### `DELETE /repositories/database/{id}`

Deletes a saved repository entry by its database ID.

```bash
curl -X DELETE http://localhost:8081/repositories/database/1
```

---

## 🔐 Rate Limits (GitHub)

| Authentication      | Requests/hour |
|---------------------|---------------|
| None (unauthenticated) | 60          |
| With PAT (token)    | 5,000         |

> Use a personal token to avoid hitting rate limits.

```bash
curl -H "Authorization: token YOUR_TOKEN" https://api.github.com/rate_limit
```

---

## 🐳 Docker

Build and run with:

```bash
docker build -t github-repositories-api .
docker run -p 8081:8081 --env-file .env github-repositories-api
```

> Ensure PostgreSQL DB is accessible and configured correctly via `application.properties` or environment variables.

---


#### Error Codes
| Code | Meaning |
|------|---------|
| `404` | User not found or no public repositories |
| `403` | GitHub rate limit exceeded |

---

## GitHub Rate Limits

| Authentication | Limit (per hour) |
|-----------------|------------------|
| None | **60** |
| PAT / OAuth | **5,000** |

Check your current quota:
```bash
curl https://api.github.com/rate_limit
```


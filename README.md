# Role Service

A Spring Boot microservice responsible for managing roles in the Employee Access Management System.
Exposes both REST and SOAP APIs for role operations.

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Web Services (SOAP)
- MySQL
- Lombok
- JUnit 5 + Mockito

## Architecture

This service is part of a two-microservice system:
```
user-service (port 8080)  →  role-service (port 8081)
        REST (HTTP)
```

user-service calls role-service to validate roles before creating or updating an employee.

## Prerequisites

- Java 17+
- Maven
- MySQL running on port 3306

## Setup

1. Create the database:
```sql
CREATE DATABASE role_db;
```

2. Set the environment variable for DB password:
```bash
# Windows
set DB_PASSWORD=your_password

# Mac/Linux
export DB_PASSWORD=your_password
```

3. Run the service:
```bash
./mvnw spring-boot:run
```

The service starts on **http://localhost:8081**

## REST API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/roles` | Create a new role |
| GET | `/api/roles` | Get all roles |
| GET | `/api/roles/{id}` | Get role by ID |
| GET | `/api/roles/name/{name}` | Get role by name |
| PUT | `/api/roles/{id}` | Update a role |
| DELETE | `/api/roles/{id}` | Delete a role |

### Sample Request — Create Role
```json
POST /api/roles
{
  "name": "ADMIN_ROLE",
  "description": "Administrator Role"
}
```

### Sample Response
```json
{
  "id": 1,
  "name": "ADMIN_ROLE",
  "description": "Administrator Role"
}
```

## SOAP API

WSDL available at: **http://localhost:8081/ws/roles.wsdl**

### Supported Operation

**GetRoleByName** — fetch a role by its name
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:ws="http://sahil.com/role-service/ws">
   <soapenv:Body>
      <ws:GetRoleByNameRequest>
         <ws:name>ADMIN_ROLE</ws:name>
      </ws:GetRoleByNameRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

## Error Handling

| Scenario | HTTP Status |
|----------|-------------|
| Role not found | 404 Not Found |
| Duplicate role name | 409 Conflict |
| Validation failure | 400 Bad Request |

## Testing
```bash
./mvnw test
```

10 unit tests covering all service layer operations using JUnit 5 and Mockito.
No database required to run tests.
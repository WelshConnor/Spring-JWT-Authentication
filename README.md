# Auth Portal

A Spring Boot REST API providing JWT-based authentication. Handles user registration and login, returning signed tokens for stateless session management.

## Endpoints

| Method | Endpoint | Auth Required | Description |
|--------|----------|---------------|-------------|
| POST | `/auth/register` | No | Register a new user |
| POST | `/auth/login` | No | Login and receive a JWT |
| * | `/api/**` | Yes | All other endpoints |

## Request Examples

**Register**
```json
{
  "email": "user@example.com",
  "password": "Test@1234",
  "confirmPassword": "Test@1234"
}
Login


{
  "email": "user@example.com",
  "password": "Test@1234"
}
Response (both)


{
  "token": "eyJhbGci...",
  "email": "user@example.com",
  "role": "USER"
}
Using the Token
Include the token in the Authorization header on subsequent requests:


Authorization: Bearer eyJhbGci...
Password Requirements
Passwords must be 8–100 characters and contain at least one uppercase letter, lowercase letter, digit, and special character.

Setup
Prerequisites: Java 25, Maven

1. Set environment variables


[System.Environment]::SetEnvironmentVariable("JWT_SECRET", "your_secret_here", "User")
2. Run


.\mvnw.cmd clean spring-boot:run
The app starts on http://localhost:8080. An H2 in-memory database is used by default — data is cleared on restart.

Tech Stack
Spring Boot 4.0.3
Spring Security
JJWT 0.12.6
Spring Data JPA
H2 (development)
Lombok


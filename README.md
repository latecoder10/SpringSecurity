# Spring JWT Authentication with Refresh Token

## Project Overview

This project implements a SpringSecurity then **JWT (JSON Web Token)** based authentication system in a Spring Boot application with the support for **refresh tokens**. It covers the typical use case of user login, registration, token generation, and refresh token management, ensuring that your application has a robust and secure authentication mechanism.

## Features

- **JWT Authentication**: Generate JWT tokens for authenticated users.
- **Refresh Token Support**: Provides the ability to refresh JWT tokens to extend sessions without needing to log in again.
- **Role-based Access**: Users can be assigned roles (e.g., `USER`, `ADMIN`) and access is restricted based on these roles.
- **Spring Security**: Utilizes Spring Security for securing endpoints and handling authentication.

## Technologies Used

- **Spring Boot**: For building the backend application.
- **Spring Security**: For handling authentication and authorization.
- **JWT**: For secure token-based authentication.
- **Lombok**: For reducing boilerplate code like getters, setters, constructors, etc.
- **H2 Database**: For simple in-memory storage during development.
- **Maven**: For dependency management and build automation.

## Prerequisites

Before running the project, ensure you have the following installed:

- **Java 17** or later.
- **Maven**: For dependency management and build tasks.
- **IDE**: Any Java IDE (Eclipse, IntelliJ IDEA, etc.).
- **Postman** (Optional): For testing the API endpoints.

## Installation

### Clone the repository

```bash
git clone https://github.com/yourusername/spring-jwt-authentication.git
cd spring-jwt-authentication
```
### Build the project
```bash
mvn clean install
```

### Run the project
```bash
mvn spring-boot:run
```
### Project Structure
```CSS
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com
│   │   │   │   ├── springJWT
│   │   │   │   │   ├── authentication
│   │   │   │   │   │   ├── config
│   │   │   │   │   │   ├── entities
│   │   │   │   │   │   ├── repositories
│   │   │   │   │   │   ├── services
│   │   │   │   │   │   ├── utils
│   │   │   │   │   ├── controller
│   │   │   │   │   ├── dto
│   │   │   │   │   ├── exception
│   │   │   │   │   ├── mapper
│   │   │   │   │   ├── repository
│   │   │   │   │   ├── service
│   │   │   │   │   ├── model
│   │   │   │   │   │   └── enums
│   ├── resources
│   │   ├── application.properties
└── pom.xml

```

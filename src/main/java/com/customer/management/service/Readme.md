# Customer Management Service

A Spring Boot-based RESTful service for managing customer data. 
Supports creating, updating, retrieving, deleting (soft delete), and activating customers using OTP verification.

---

## Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Setup & Installation](#setup--installation)
- [Database Configuration](#database-configuration)
- [API Endpoints](#api-endpoints)
- [Request & Response Examples](#request--response-examples)
- [Utilities](#utilities)
- [Error Handling](#error-handling)

---

## Features

- Create new customers with OTP activation
- Fetch all customers with pagination & sorting
- Get customer by ID
- Update customer details (full update, mobile, email, password)
- Forgot password functionality
- Softly delete customers (status set as INACTIVE)
- Activate customers using OTP
- Password encryption using BCrypt
- Validation with `javax.validation` annotations
- Global exception handling

---

## Technology Stack

- Java 17+
- Spring Boot 3.x
- Spring Data JPA (Hibernate)
- MySQL
- Lombok
- Spring Validation
- Spring Security (for password encoding)
- Maven

---

## Setup & Installation

1. **Clone the repository**

```bash
git clone <repository_url>
cd customer-management-service
spring.datasource.url=jdbc:mysql://localhost:3306/customer_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

Build and run

mvn clean install
mvn spring-boot:run
Service runs at: http://localhost:8080/customer-management-service/api/v1
```


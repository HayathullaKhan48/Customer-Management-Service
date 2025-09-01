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

## API Endpoints
* **POST**: 'http://localhost:8080/customer-management-service/api/v1/create'
* **GET**: 'http://localhost:8080/customer-management-service/api/v1/customers'
* **PATCH**: 'http://localhost:8080/customer-management-service/api/v1/updateMobile/{mobileNumber}'
* **GET**: 'http://localhost:8080/customer-management-service/api/v1/getById/{customerId}'
* **PATCH**: 'http://localhost:8080/customer-management-service/api/v1/updateEmail/{mobileNumber}'
* **PUT**: 'http://localhost:8080/customer-management-service/api/v1/customerUpdate'
* **PATCH**: 'http://localhost:8080/customer-management-service/api/v1/customer/changePassword/{mobileNumber}/{newPassword}'
* **PATCH**: 'http://localhost:8080/customer-management-service/api/v1/forgotPassword/{mobileNumber}/{newPassword}/{conformPassword}'
* **DELETE**: 'http://localhost:8080/customer-management-service/api/v1/deleteCustomer/{customerId}'
* **PATCH**: 'http://localhost:8080/customer-management-service/api/v1/activateByOtp/{mobileNumber}/{otpNo}'

___

### 1. **Create Customer**

* **Method**: 'POST'
* **URL**: 'http://localhost:8080/customer-management-service/api/v1/create'
* **Request Body**:
```json
{
  "firstName": "Aakif",
  "lastName": "khan",
  "fullName": "navab Aakif Ali khan",
  "age": 16,
  "mobileNumber": "9988776655",
  "emailAddress": "AakifAilKhan@gmail.com",
  "password": "ABC@123123",
  "addresses": [
    {
      "street": "Main Road",
      "city": "Chinnamandem",
      "state": "Andhra pradesh",
      "country": "India"
    }
  ]
}

```

*  **Response Body**:
```json
{
    "customerId": 4,
    "firstName": "Aakif",
    "lastName": "khan",
    "fullName": "Navab Aakif Ali khan",
    "age": 16,
    "mobileNumber": "9988776655",
    "emailAddress": "AakifAilKhan@gmail.com",
    "status": "INACTIVE",
    "createdDate": "2025-09-01T13:14:53.393074",
    "updatedDate": "2025-09-01T13:14:53.393074",
    "addresses": [
        {
            "addressId": 5,
            "street": "Main Road",
            "city": "Chinnamandem",
            "state": "Andhra pradesh",
            "country": "India"
        }
    ]
}
```

### 2. **All Customers**

* **Method**: 'GET'
* **URL**: 'http://localhost:8080/customer-management-service/api/v1/customers'
* **Request Body**: NULL

*  **Response Body**:
```json
{
    "content": [
        {
            "customerId": 4,
            "firstName": "Aakif",
            "lastName": "khan",
            "fullName": "navab Aakif Ali khan",
            "age": 16,
            "mobileNumber": "9988776655",
            "emailAddress": "AakifAilKhan@gmail.com",
            "status": "INACTIVE",
            "createdDate": "2025-09-01T13:14:53.393074",
            "updatedDate": "2025-09-01T13:14:53.393074",
            "addresses": [
                {
                    "addressId": 5,
                    "street": "Main Road",
                    "city": "Chinnamandem",
                    "state": "Andhra pradesh",
                    "country": "India"
                }
            ]
        },
        {
            "customerId": 3,
            "firstName": "Sufiyan",
            "lastName": "khan",
            "fullName": "Nayazi Sufiyan khan",
            "age": 16,
            "mobileNumber": "9515421910",
            "emailAddress": "Sufiyan@Gmail.com",
            "status": "INACTIVE",
            "createdDate": "2025-08-30T19:07:34.204728",
            "updatedDate": "2025-08-30T19:30:11.362162",
            "addresses": [
                {
                    "addressId": 4,
                    "street": "MRC Road",
                    "city": "rayachoty",
                    "state": "Andhra pradesh",
                    "country": "India"
                }
            ]
        },
        {
            "customerId": 2,
            "firstName": "Arzoo",
            "lastName": "khanam",
            "fullName": "Nayazi Arzoo khanam",
            "age": 18,
            "mobileNumber": "9182299568",
            "emailAddress": "Arzoo@Gmail.com",
            "status": "INACTIVE",
            "createdDate": "2025-08-30T19:06:47.504741",
            "updatedDate": "2025-08-30T19:06:47.504741",
            "addresses": [
                {
                    "addressId": 2,
                    "street": "Main Road",
                    "city": "Chinnamandem",
                    "state": "Andhra pradesh",
                    "country": "India"
                }
            ]
        },
        {
            "customerId": 1,
            "firstName": "Hayathulla",
            "lastName": "khan",
            "fullName": "Nayazi Hayathulla khan",
            "age": 24,
            "mobileNumber": "8985415771",
            "emailAddress": "nayazihayathullaUpdated@gmail.com",
            "status": "ACTIVE",
            "createdDate": "2025-08-30T18:42:58.650863",
            "updatedDate": "2025-08-30T19:32:52.273101",
            "addresses": [
                {
                    "addressId": 1,
                    "street": "Main Road",
                    "city": "Chinnamandem",
                    "state": "Andhra pradesh",
                    "country": "India"
                }
            ]
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 20,
        "sort": {
            "empty": false,
            "unsorted": false,
            "sorted": true
        },
        "offset": 0,
        "unpaged": false,
        "paged": true
    },
    "last": true,
    "totalElements": 4,
    "totalPages": 1,
    "size": 20,
    "number": 0,
    "sort": {
        "empty": false,
        "unsorted": false,
        "sorted": true
    },
    "numberOfElements": 4,
    "first": true,
    "empty": false
}
```

### 3. **Update Mobile**

* **Method**: 'PATCH'
* **URL**: 'http://localhost:8080/customer-management-service/api/v1/updateMobile/{mobileNumber}'
* **Request Body**:
  * **Query Params**: 
    * Key: newMobileNumber
    * Value: 6304474604
    
*  **Response Body**:
```json
{
    "customerId": 1,
    "firstName": "Hayathulla",
    "lastName": "khan",
    "fullName": "Nayazi Hayathulla khan",
    "age": 24,
    "mobileNumber": "6304474604",
    "emailAddress": "nayazihayathullaUpdated@gmail.com",
    "status": "ACTIVE",
    "createdDate": "2025-08-30T18:42:58.650863",
    "updatedDate": "2025-09-01T14:35:41.7795145",
    "addresses": [
        {
            "addressId": 1,
            "street": "Main Road",
            "city": "Chinnamandem",
            "state": "Andhra pradesh",
            "country": "India"
        }
    ]
}
```

### 4. **Get By ID**

* **Method**: 'GET'
* **URL**: 'http://localhost:8080/customer-management-service/api/v1/getById/2'
* **Request Body**: NULL

*  **Response Body**:
```json
{
    "customerId": 2,
    "firstName": "Arzoo",
    "lastName": "khanam",
    "fullName": "Nayazi Arzoo khanam",
    "age": 18,
    "mobileNumber": "9182299568",
    "emailAddress": "Arzoo@Gmail.com",
    "status": "INACTIVE",
    "createdDate": "2025-08-30T19:06:47.504741",
    "updatedDate": "2025-08-30T19:06:47.504741",
    "addresses": [
        {
            "addressId": 2,
            "street": "Main Road",
            "city": "Chinnamandem",
            "state": "Andhra pradesh",
            "country": "India"
        }
    ]
}
```

### 5. **Update Customer Email**

* **Method**: 'PATCH'
* **URL**: 'http://localhost:8080/customer-management-service/api/v1/updateEmail/6304474604?newEmail=nayazihayathullaUpdated2@gmail.com'
* **Request Body**:
    * **Query Params**:
        * Key: newEmail
        * Value: nayazihayathullaUpdated2@gmail.com

*  **Response Body**:
```json
{
    "customerId": 1,
    "firstName": "Hayathulla",
    "lastName": "khan",
    "fullName": "Nayazi Hayathulla khan",
    "age": 24,
    "mobileNumber": "6304474604",
    "emailAddress": "nayazihayathullaUpdated2@gmail.com",
    "status": "ACTIVE",
    "createdDate": "2025-08-30T18:42:58.650863",
    "updatedDate": "2025-09-01T14:38:16.5610772",
    "addresses": [
        {
            "addressId": 1,
            "street": "Main Road",
            "city": "Chinnamandem",
            "state": "Andhra pradesh",
            "country": "India"
        }
    ]
}
```

### 6. **Update Full Customer Details**

* **Method**: 'PUT'
* **URL**: 'http://localhost:8080/customer-management-service/api/v1/customersUpdate'
* **Request Body**:
```json
{
  "firstName": "Sufiyan",
  "lastName": "khan",
  "fullName": "Nayazi Sufiyan khan",
  "age": 16,
  "mobileNumber": "9515421910",
  "emailAddress": "Sufiyan@Gmail.com",
  "status": "ACTIVE",
  "password": "00@sugiyan!123",
  "addresses": [
    {
      "street": "MRC Road",
      "city": "rayachoty",
      "state": "Andhra pradesh",
      "country": "India"
    }
  ]
}

```

*  **Response Body**:
```json
{
    "customerId": 3,
    "firstName": "Sufiyan",
    "lastName": "khan",
    "fullName": "Nayazi Sufiyan khan",
    "age": 16,
    "mobileNumber": "9515421910",
    "emailAddress": "Sufiyan@Gmail.com",
    "status": "INACTIVE",
    "createdDate": "2025-08-30T19:07:34.204728",
    "updatedDate": "2025-09-01T14:40:18.9293287",
    "addresses": [
        {
            "addressId": 6,
            "street": "MRC Road",
            "city": "rayachoty",
            "state": "Andhra pradesh",
            "country": "India"
        }
    ]
}
```

### 7. **Change Customer Password**

* **Method**: 'PATCH'
* **URL**: 'http://localhost:8080/customer-management-service/api/v1/customer/changePassword/9515421910/10M21A@535'
* **Request Body**: NULL

*  **Response Body**:
```json
{
    "customerId": 3,
    "firstName": "Sufiyan",
    "lastName": "khan",
    "fullName": "Nayazi Sufiyan khan",
    "age": 16,
    "mobileNumber": "9515421910",
    "emailAddress": "Sufiyan@Gmail.com",
    "status": "INACTIVE",
    "createdDate": "2025-08-30T19:07:34.204728",
    "updatedDate": "2025-09-01T14:41:28.9491586",
    "addresses": [
        {
            "addressId": 6,
            "street": "MRC Road",
            "city": "rayachoty",
            "state": "Andhra pradesh",
            "country": "India"
        }
    ]
}
```

### 8. **Forgot Password**

* **Method**: 'PATCH'
* **URL**: 'http://localhost:8080/customer-management-service/api/v1/forgotPassword/9515421910/sufiyan@1234/sufiyan@1234'
* **Request Body**: NULL

*  **Response Body**:
```json
{
    "customerId": 3,
    "firstName": "Sufiyan",
    "lastName": "khan",
    "fullName": "Nayazi Sufiyan khan",
    "age": 16,
    "mobileNumber": "9515421910",
    "emailAddress": "Sufiyan@Gmail.com",
    "status": "INACTIVE",
    "createdDate": "2025-08-30T19:07:34.204728",
    "updatedDate": "2025-09-01T14:42:08.6510779",
    "addresses": [
        {
            "addressId": 6,
            "street": "MRC Road",
            "city": "rayachoty",
            "state": "Andhra pradesh",
            "country": "India"
        }
    ]
}
```

### 9. **Delete Customer**

* **Method**: 'DELETE'
* **URL**: 'http://localhost:8080/customer-management-service/api/v1/deleteCustomer/1'
* **Request Body**: NULL

*  **Response Body**:
```json
{
    "customerId": 1,
    "firstName": "Hayathulla",
    "lastName": "khan",
    "fullName": "Nayazi Hayathulla khan",
    "age": 24,
    "mobileNumber": "6304474604",
    "emailAddress": "nayazihayathullaUpdated2@gmail.com",
    "status": "INACTIVE",
    "createdDate": "2025-08-30T18:42:58.650863",
    "updatedDate": "2025-09-01T14:43:46.6931167",
    "addresses": [
        {
            "addressId": 1,
            "street": "Main Road",
            "city": "Chinnamandem",
            "state": "Andhra pradesh",
            "country": "India"
        }
    ]
}
```

### 10. **Activate By Otp**

* **Method**: 'PATCH'
* **URL**: 'http://localhost:8080/customer-management-service/api/v1/activateByOtp/9182299568/811740'
* **Request Body**: NULL

*  **Response Body**:
```json
{
    "customerId": 2,
    "firstName": "Arzoo",
    "lastName": "khanam",
    "fullName": "Nayazi Arzoo khanam",
    "age": 18,
    "mobileNumber": "9182299568",
    "emailAddress": "Arzoo@Gmail.com",
    "status": "ACTIVE",
    "createdDate": "2025-08-30T19:06:47.504741",
    "updatedDate": "2025-09-01T14:44:47.7778118",
    "addresses": [
        {
            "addressId": 2,
            "street": "Main Road",
            "city": "Chinnamandem",
            "state": "Andhra pradesh",
            "country": "India"
        }
    ]
}
```

##  Database Schema

**Table**: `select * from customers;`

| Field Name    | Type      | Description                  |
|---------------|-----------|------------------------------|
| customer_id   | bigint    | Primary Key (Auto-generated) |
| first_name    | varchar   | Customer first name          |
| last_name     | varchar   | Customer last name           |
| full_name     | varchar   | Full name                    |
| mobile_number | varchar   | Customer mobile number       |
| email_address | varchar   | Customer email               |
| age           | int       | Customer age                 |
| password      | varchar   | Encrypted password (BCrypt)  |
| status        | varchar   | Enum: `ACTIVE`, `INACTIVE`   |
| created_date  | timestamp | Record creation timestamp    |
| updated_date  | timestamp | Last update timestamp        |

## Password Hashing
* Passwords are auto-generated and securely hashed using BCrypt. 
* Format: 12 characters containing uppercase, lowercase, digits, and special characters.

---

**Table**: `customer_address`

| Field Name  | Type    | Description                                |
|-------------|---------|--------------------------------------------|
| address_id  | bigint  | Primary Key (Auto-generated)               |
| customer_id | bigint  | Foreign Key (customer_details.customer_id) |
| street      | varchar | Street name                                |
| city        | varchar | City name                                  |
| state       | varchar | State name                                 |
| country     | varchar | Country name                               |

---
## Password Hashing

- Passwords are **hashed** using **BCryptPasswordEncoder** before saving.
- No plaintext password is stored in the database.
- During login or password change, raw password is matched against hashed password using BCrypt’s `matches()`.

---

## Project Structure
```json
customer-management-service
|── constant          # Constants like password characters, length
|── controller        # REST controllers for endpoints
|── enums             # Enum for CustomerStatus
|── entity/model      # JPA Entity - CustomerModel & CustomerAddress
|── exceptions        # Custom exceptions & global exception handler
|── mapper            # Mapper classes for converting between Entity and DTOs
|── repository        # Spring Data JPA repository
|── request           # Request DTOs
|── response          # Response DTOs
|── service           # Interfaces for business logic
|── service/impl      # Service implementation classes
|── util              # Utility classes (PasswordUtil, OtpGenerator)
── CustomerManagementServiceApplication.java
```

---

## Enum — CustomerStatus

```java
enum
    ACTIVE,
    INACTIVE
```
---

## Important Classes & Purpose

```json
Class	                              Purpose

CustomerController	               Handles all REST API requests
CustomerService	                       Service interface for business logic
CustomerServiceImpl	               Implementation of business logic
CustomerModel & CustomerAddress	       Entity classes for JPA table mapping
CustomerRequest	                       DTO for input requests
CustomerResponse	               DTO for output responses
PasswordUtil	                       Utility for password encryption & validation
OtpGenerator	                       Generates OTP for customer activation
CustomerConstant	               Constant values used across application
CustomerRepository	               Extends JpaRepository for DB operations
CustomerAlreadyExistsException	       Custom exception thrown when duplicate customer is found
CustomerNotFoundException	       Custom exception thrown when requested customer is not found
GlobalExceptionHandler	               Handles exceptions globally and returns standardized error responses

```
---


## Setup & Installation
### Clone the Repository
1. **Clone the repository**

```bash

git clone <https://github.com/HayathullaKhan48/Customer-Management-Service>
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
---

## Request & Response Examples
Detailed request/response samples for Create, Get All, Update, Delete, OTP Activation are provided above.

---

## Error Handling
* Centralized Global Exception Handler 
* Returns meaningful HTTP status codes:
  * 404 – Customer Not Found 
  * 409 – Customer Already Exists 
  * 400 – Invalid Request (Validation Failed)
  * 500 – Internal Server Error 
* All errors are returned with a standard JSON response structure





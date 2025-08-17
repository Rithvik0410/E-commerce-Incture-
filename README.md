# E-Commerce Platform

E-Commerce Platform is a Spring Boot application designed to provide a comprehensive online shopping experience. It offers REST APIs for product browsing, shopping cart management, order processing, payment handling, and product reviews. The system features a complete purchase flow from product discovery to checkout and payment processing, with detailed order tracking and user management.

The application is built on Java 21, Spring Boot 3, Spring Data JPA with MySQL for persistence, and includes comprehensive logging for monitoring user transactions and admin actions. The platform implements proper error handling and data validation, ensuring robust and secure operations.

## Features

- **Product Management**: Create, read, update, and delete products
- **User Management**: User registration, authentication, and profile management
- **Shopping Cart**: Add/remove products, update quantities, view cart contents
- **Order Processing**: Create orders from cart, track order status
- **Payment System**: Process payments with different methods (credit card, PayPal, etc.)
- **Review System**: Add product reviews with ratings
- **Comprehensive Logging**: Track user transactions and admin actions
- **Exception Handling**: Consistent error responses in JSON format
- **Unit and Integration Testing**: Ensuring reliability and correctness

## Technologies Used

- Java 21
- Spring Boot 3.5.4
- Spring Data JPA with MySQL
- Hibernate
- JUnit 5 and Mockito
- Maven
- Logback for logging
- Jackson for JSON processing

## Setup and Run Instructions

### Prerequisites

- Java 21 or higher
- Maven
- MySQL server with a database named e_commerce

### Database Setup

Create the database in MySQL:

```sql
CREATE DATABASE e_commerce;

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
```


# E-Commerce Application

This is a Spring Boot-based e-commerce application that provides a robust platform for managing products, carts, orders, payments, reviews, and admin logging. The application uses MySQL as the database and includes comprehensive logging, testing, and a well-defined API for various functionalities.

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Setup Instructions](#setup-instructions)
- [Configuration](#configuration)
- [API Endpoints](#api-endpoints)
- [Payment Methods](#payment-methods)
- [Logging](#logging)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Entity Relationships](#entity-relationships)
- [Contributing](#contributing)
- [License](#license)

## Features
- Product management (CRUD operations)
- Shopping cart functionality
- Order creation and management
- Payment processing with multiple payment methods
- Product review system
- Comprehensive admin and user activity logging
- Unit and integration testing

## Technologies
- **Backend**: Spring Boot, Spring Data JPA, Spring Security
- **Database**: MySQL (H2 in-memory database for testing)
- **Build Tool**: Maven
- **Logging**: SLF4J with daily rotating log files
- **Testing**: JUnit, Mockito, Spring Boot Test

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven
- MySQL server
- Git

### Steps
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd e-commerce
   ```

2. **Configure Database**:
   Update the `application.properties` file in `src/main/resources` with your MySQL credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/e_commerce
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. **Build the Project**:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**:
   The application will be available at `http://localhost:8080`.

## Configuration
The application is configured via the `application.properties` file. Key settings include:
- **Database**: MySQL connection details
- **JPA/Hibernate**: Automatic schema updates (`ddl-auto=update`) and SQL logging (`show-sql=true`)
- **Logging**: Configured to save logs in daily rotating files

## API Endpoints
Below is a tabulated list of the available API endpoints:

| **Method** | **Endpoint**                              | **Description**                          |
|------------|-------------------------------------------|------------------------------------------|
| **Product Management** | | |
| GET        | `/products`                              | List all products                       |
| GET        | `/products/{id}`                         | Get product by ID                       |
| POST       | `/products`                              | Create new product                      |
| PUT        | `/products/{id}`                         | Update a product                        |
| DELETE     | `/products/{id}`                         | Delete product                          |
| **Cart Management** | | |
| POST       | `/cart/create/{userId}`                  | Create cart for user                    |
| POST       | `/cart/{cartId}/add/{productId}`         | Add product to cart                     |
| GET        | `/cart/{cartId}/items`                   | View cart items                         |
| DELETE     | `/cart/item/{itemId}`                    | Remove specific item                    |
| DELETE     | `/cart/{cartId}/clear`                   | Clear cart                              |
| PUT        | `/cart/{cartId}/update/{itemId}`         | Update cart item quantity               |
| **Order Management** | | |
| POST       | `/orders/create-from-cart/{cartId}`      | Create order from cart                  |
| GET        | `/orders/user/{userId}`                  | Get all orders for a user               |
| GET        | `/orders/{orderId}`                      | Get specific order                      |
| PUT        | `/orders/{orderId}/status`               | Update order status                     |
| **Payment Processing** | | |
| POST       | `/payments/order/{orderId}`              | Create payment for order                |
| POST       | `/payments/{paymentId}/process`          | Process payment                         |
| GET        | `/payments/{paymentId}`                  | Get payment details                     |
| GET        | `/payments/order/{orderId}`              | Get payment for order                   |
| **Review System** | | |
| POST       | `/reviews/product/{productId}`           | Add review to product                   |
| GET        | `/reviews/product/{productId}`           | Get all reviews for product             |
| GET        | `/reviews/{reviewId}`                    | Get specific review                     |
| PUT        | `/reviews/{reviewId}`                    | Update review                           |
| DELETE     | `/reviews/{reviewId}`                    | Delete review                           |
| **Admin Logging** | | |
| GET        | `/admin/logs/user/{userId}`              | Get user activity logs                  |
| GET        | `/admin/logs/admin`                      | Get admin activity logs                 |
| GET        | `/admin/logs/date-range`                 | Get logs by date range                  |

## Payment Methods
The application supports the following payment methods:
- Credit Card
- Debit Card
- PayPal
- UPI

## Logging
The application implements comprehensive logging for:
- **User Transactions**: Purchases, cart operations (`user-transactions.log`)
- **Admin Actions**: Product management (`admin-actions.log`)
- **Security Events**: Login attempts, authentication (`security.log`)
- **System Events**: General application logs (`E-Commerce.log`)

Logs are stored in the `logs` folder with daily rotation.

## Testing
The project includes both unit and integration tests:
- **Unit Tests**: Test individual components with mocked dependencies
- **Integration Tests**: Test component interactions using an H2 in-memory database

Run all tests:
```bash
mvn test
```

Run specific tests:
```bash
mvn test -Dtest=TestClassName
```

## Project Structure
- **Entities**: User, Product, Cart, CartItem, Order, OrderItem, Payment, Review, AuditLog
- **Controllers**: Handle API requests for each module
- **Services**: Business logic for product, cart, order, payment, and review management
- **Repositories**: JPA repositories for database operations
- **Configuration**: Logging, security, and database configurations
- **Tests**: Unit and integration tests

## Entity Relationships
- **User**: Has many Orders and one Cart
- **Product**: Has many Reviews and can be in many CartItems and OrderItems
- **Cart**: Belongs to one User and has many CartItems
- **CartItem**: Belongs to one Cart and references one Product
- **Order**: Belongs to one User, has many OrderItems, and one Payment
- **OrderItem**: Belongs to one Order and references one Product
- **Payment**: Belongs to one Order
- **Review**: Belongs to one Product
- **AuditLog**: Tracks user and admin actions

## Contributing
Contributions are welcome! Please follow these steps:
1. Fork the repository
2. Create a new branch (`git checkout -b feature-branch`)
3. Commit your changes (`git commit -m 'Add feature'`)
4. Push to the branch (`git push origin feature-branch`)
5. Create a pull request

## License
This project is licensed under the MIT License.




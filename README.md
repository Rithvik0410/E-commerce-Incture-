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
##Configuration
Update database credentials in application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/e_commerce
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


Running the Application
Clone the repository
Build the project: mvn clean install
Run the application: mvn spring-boot:run
Access the API at http://localhost:8080
API Endpoints
Method	Endpoint	Description
Product Management		
GET	/products	List all products
GET	/products/{id}	Get product by ID
POST	/products	Create new product
PUT	/products/{id}	Update a product
DELETE	/products/{id}	Delete product
Cart Management		
POST	/cart/create/{userId}	Create cart for user
POST	/cart/{cartId}/add/{productId}	Add product to cart
GET	/cart/{cartId}/items	View cart items
DELETE	/cart/item/{itemId}	Remove specific item
DELETE	/cart/{cartId}/clear	Clear cart
PUT	/cart/{cartId}/update/{itemId}	Update cart item quantity
Order Management		
POST	/orders/create-from-cart/{cartId}	Create order from cart
GET	/orders/user/{userId}	Get all orders for a user
GET	/orders/{orderId}	Get specific order
PUT	/orders/{orderId}/status	Update order status
Payment Processing		
POST	/payments/order/{orderId}	Create payment for order
POST	/payments/{paymentId}/process	Process payment
GET	/payments/{paymentId}	Get payment details
GET	/payments/order/{orderId}	Get payment for order
Review System		
POST	/reviews/product/{productId}	Add review to product
GET	/reviews/product/{productId}	Get all reviews for product
GET	/reviews/{reviewId}	Get specific review
PUT	/reviews/{reviewId}	Update review
DELETE	/reviews/{reviewId}	Delete review
Admin Logging		
GET	/admin/logs/user/{userId}	Get user activity logs
GET	/admin/logs/admin	Get admin activity logs
GET	/admin/logs/date-range	Get logs by date range
Payment Methods
The system supports the following payment methods:

CREDIT_CARD
DEBIT_CARD
PAYPAL
UPI
Logging
The application implements comprehensive logging for:

User transactions (purchases, cart operations)
Admin actions (product management)
Security events (login attempts, authentication)
System events
Logs are saved to daily rotating files in the logs folder with different log files for different types of events:

E-Commerce.log - Main application log
user-transactions.log - User transaction logs
admin-actions.log - Admin action logs
security.log - Security-related logs
Testing
The project includes both unit and integration tests:

Unit Tests: Testing individual components with mocked dependencies
Integration Tests: Testing component interactions with H2 in-memory database
Run tests using Maven:

Or run specific tests:

Project Structure
Entity Relationships
User: Has many Orders and one Cart
Product: Has many Reviews and can be in many CartItems and OrderItems
Cart: Belongs to one User and has many CartItems
CartItem: Belongs to one Cart and references one Product
Order: Belongs to one User, has many OrderItems, and one Payment
OrderItem: Belongs to one Order and references one Product
Payment: Belongs to one Order
Review: Belongs to one Product
AuditLog: Tracks user and admin actions


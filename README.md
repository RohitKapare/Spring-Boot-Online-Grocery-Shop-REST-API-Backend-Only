# Spring Boot Grocery API 

A comprehensive REST API for managing an online grocery store, built with Spring Boot 3, JPA, and MySQL. This backend application provides complete CRUD operations for customers, grocery items, and orders with automatic inventory management.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Gradle](https://img.shields.io/badge/Gradle-8.14.3-lightgrey)

## Features

### Core Functionality
- **Customer Management**: Register, update, and manage customer accounts with email validation
- **Inventory Management**: Add, update, and track grocery items with stock quantities
- **Order Processing**: Create orders with automatic stock validation and deduction
- **Stock Management**: Automatic inventory updates and stock restoration on order cancellation

### Technical Features
- **RESTful API Design**: Clean, intuitive endpoints following REST conventions
- **Data Validation**: Input validation and error handling
- **Transaction Management**: Atomic operations for order processing
- **Relationship Management**: Proper JPA relationships with circular reference handling
- **Stock Control**: Real-time inventory tracking and insufficient stock prevention


## API Endpoints

### Customers
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/customers` | Get all customers |
| GET | `/api/customers/{id}` | Get customer by ID |
| POST | `/api/customers` | Create new customer |
| PUT | `/api/customers/{id}` | Update customer |
| DELETE | `/api/customers/{id}` | Delete customer |

### Grocery Items
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/items` | Get all items |
| GET | `/api/items/{id}` | Get item by ID |
| POST | `/api/items` | Create new item |
| PUT | `/api/items/{id}` | Update item |
| PUT | `/api/items/{id}/increase-stock?quantity=N` | Increase item stock |
| DELETE | `/api/items/{id}` | Delete item |

### Orders
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/orders` | Get all orders |
| GET | `/api/orders/{id}` | Get order by ID |
| GET | `/api/orders/customer/{id}` | Get orders by customer |
| POST | `/api/orders` | Create new order |
| DELETE | `/api/orders/{id}` | Delete order (restores stock) |

## 🛠️ Quick Start

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/RohitKapare/Spring-Boot-Online-Grocery-Shop-REST-API-Backend-Only.git
   cd Spring-Boot-Online-Grocery-Shop-REST-API-Backend-Only
   ```

2. **Set up MySQL database**
   ```sql
   CREATE DATABASE grocery_db;
   ```

3. **Configure database connection**
   
   Create or update `src/main/resources/application.properties`:
   ```properties
   spring.application.name=groceryapibackend
   
   # MySQL Database Configuration
   spring.datasource.url=jdbc:mysql://localhost:3306/grocery_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   
   # JPA/Hibernate Configuration
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
   spring.jpa.properties.hibernate.format_sql=true
   ```

4. **Run the application**
   ```bash
   ./gradlew bootRun
   ```
   Or on Windows:
   ```bash
   .\gradlew.bat bootRun
   ```

The API will be available at `http://localhost:8080`

## Usage Examples

### Create a Customer
```json
POST /api/customers
{
  "name": "John Doe",
  "email": "john@example.com",
  "phoneNumber": "123-456-7890",
  "address": "123 Main Street"
}
```

### Add a Grocery Item
```json
POST /api/items
{
  "name": "Organic Apples",
  "category": "Fruits",
  "price": 4.99,
  "stockQuantity": 100
}
```

### Create an Order
```json
POST /api/orders
{
  "customerId": 1,
  "orderItems": [
    {
      "itemId": 1,
      "quantity": 3
    },
    {
      "itemId": 2,
      "quantity": 2
    }
  ]
}
```


## Database Schema

The application uses the following main tables:
- `customer` - Customer information
- `grocery_item` - Product catalog
- `orders` - Order headers
- `order_item` - Order line items

The schema is automatically created/updated using Hibernate DDL auto-generation.

## ER-Diagram

Use below erDiagram in [mermaid.live](https://mermaid.live/edit) website to see erDiagram.
```
erDiagram
    CUSTOMER {
        int id PK
        string name
        string email
        string phone
        string address
    }

    ORDER {
        int id PK
        date order_date
        int customer_id FK
    }

    GROCERY_ITEM {
        int id PK
        string name
        string category
        float price
        int stock_quantity
    }

    ORDER_ITEM {
        int id PK
        int order_id FK
        int grocery_item_id FK
        int quantity
        float subtotal
    }

    CUSTOMER ||--o{ ORDER : places
    ORDER ||--o{ ORDER_ITEM : contains
    GROCERY_ITEM ||--o{ ORDER_ITEM : included_in
```


## Author

**Rohit Kapare**
- GitHub: [@RohitKapare](https://github.com/RohitKapare)

---

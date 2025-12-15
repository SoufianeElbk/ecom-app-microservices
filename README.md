# E-Commerce Microservices Application

A distributed e-commerce application built with Spring Boot and Spring Cloud microservices architecture, demonstrating service discovery, API gateway, inter-service communication, and distributed configuration management.

## 🏗️ Architecture Overview

This project implements a microservices-based e-commerce system with the following services:

```
┌─────────────────┐
│   API Gateway   │ :8888
│  (Spring Cloud  │
│    Gateway)     │
└────────┬────────┘
         │
    ┌────┴─────────────────────┐
    │                          │
┌───▼──────────┐      ┌────────▼──────┐
│  Discovery   │◄─────┤   Services    │
│   Service    │      │   Register    │
│  (Eureka)    │      │   & Discover  │
│   :8761      │      └───────────────┘
└──────────────┘
         ▲
         │
    ┌────┴────────────┬──────────────┬────────────────┐
    │                 │              │                │
┌───┴──────────┐ ┌────┴────────┐ ┌──┴──────────┐ ┌──┴──────────┐
│  Customer    │ │  Inventory  │ │   Billing   │ │   Config    │
│   Service    │ │   Service   │ │   Service   │ │   Service   │
│   :8081      │ │   :8082     │ │   :8083     │ │  (Optional) │
└──────────────┘ └─────────────┘ └─────────────┘ └─────────────┘
```

### Services Description

- **Discovery Service (Eureka)**: Service registry that enables service discovery for all microservices
- **Gateway Service**: API Gateway for routing, load balancing, and centralized entry point
- **Customer Service**: Manages customer data and operations
- **Inventory Service**: Handles product inventory management
- **Billing Service**: Processes billing, orders, and aggregates data from Customer and Inventory services using Feign
- **Config Service**: (Optional) Centralized configuration management using Spring Cloud Config

## 🛠️ Technologies Used

### Core Framework
- **Java 17**: Programming language
- **Spring Boot 3.5.6**: Application framework
- **Maven**: Build and dependency management

### Spring Cloud Stack
- **Spring Cloud 2025.0.0**: Cloud-native toolkit
- **Spring Cloud Netflix Eureka**: Service discovery and registration
- **Spring Cloud Gateway**: API Gateway with WebFlux
- **Spring Cloud OpenFeign**: Declarative REST client for inter-service communication
- **Spring Cloud Config**: (Optional) Distributed configuration

### Data & Persistence
- **Spring Data JPA**: Data persistence layer
- **Spring Data REST**: RESTful API exposure with HATEOAS support
- **MySQL**: Primary database
- **Hibernate**: ORM framework

### Other Libraries
- **Lombok**: Reduce boilerplate code
- **Spring Boot Actuator**: Production-ready monitoring and management
- **Spring Boot DevTools**: Development-time features

## 📁 Project Structure

```
ecom-app/
├── discovery-service/          # Eureka service registry
│   ├── src/
│   └── pom.xml
├── gatewey-service/           # API Gateway
│   ├── src/
│   └── pom.xml
├── customer-service/          # Customer management microservice
│   ├── src/
│   │   └── main/
│   │       └── java/ma/soufiane/customerservice/
│   │           ├── entity/
│   │           ├── repository/
│   │           └── config/
│   └── pom.xml
├── inventory-service/         # Product inventory microservice
│   ├── src/
│   │   └── main/
│   │       └── java/ma/soufiane/inventoryservice/
│   │           ├── entity/
│   │           ├── repository/
│   │           └── config/
│   └── pom.xml
├── billing-service/           # Billing and orders microservice
│   ├── src/
│   │   └── main/
│   │       └── java/ma/soufiane/billingservice/
│   │           ├── entity/
│   │           ├── repository/
│   │           ├── model/
│   │           ├── feign/
│   │           └── controller/
│   └── pom.xml
├── config-repo/              # Configuration files repository
│   ├── application.properties
│   ├── customer-service.properties
│   ├── inventory-service.properties
│   ├── billing-service.properties
│   └── *-dev.properties / *-prod.properties
├── pom.xml                   # Parent POM
└── README.md
```

## 🚀 Getting Started

### Prerequisites

- **JDK 17 or higher** installed
- **Maven 3.6+** installed
- **MySQL Server** running on localhost:3306
- **Git** for version control

### Database Setup

1. Start MySQL server
2. Create a MySQL user (or use root):
   ```sql
   CREATE USER 'root'@'localhost' IDENTIFIED BY 'Glsid@2025';
   GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. The databases will be created automatically when services start:
   - `customers_db` for Customer Service
   - `inventory_db` for Inventory Service
   - `billing_db` for Billing Service

### Installation & Running

**Important**: Services must be started in the following order to ensure proper dependency resolution:

#### 1. Clone the Repository
```bash
git clone https://github.com/SoufianeElbk/ecom-app.git
cd ecom-app
```

#### 2. Start Discovery Service (First)
```bash
cd discovery-service
./mvnw spring-boot:run
```
Wait until you see: `Eureka Server started` (usually takes 30-60 seconds)
Access Eureka Dashboard at: http://localhost:8761

#### 3. Start Gateway Service (Second)
```bash
cd ../gatewey-service
./mvnw spring-boot:run
```
Gateway will be available at: http://localhost:8888

#### 4. Start Business Services (Any Order)

**Customer Service:**
```bash
cd ../customer-service
./mvnw spring-boot:run
```

**Inventory Service:**
```bash
cd ../inventory-service
./mvnw spring-boot:run
```

**Billing Service:**
```bash
cd ../billing-service
./mvnw spring-boot:run
```

### Verify Services are Running

1. Open Eureka Dashboard: http://localhost:8761
2. You should see all services registered:
   - CUSTOMER-SERVICE
   - INVENTORY-SERVICE
   - BILLING-SERVICE
   - GATEWAY-SERVICE

## 📡 API Endpoints

### Through API Gateway (Recommended)
```
http://localhost:8888/api/...
```

### Direct Service Access

#### Customer Service (Port: 8081)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/customers` | List all customers (paginated) |
| GET | `/api/customers/{id}` | Get customer by ID |
| POST | `/api/customers` | Create new customer |
| PUT | `/api/customers/{id}` | Update customer |
| DELETE | `/api/customers/{id}` | Delete customer |

**Example Request:**
```bash
curl http://localhost:8081/api/customers
```

#### Inventory Service (Port: 8082)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | List all products (paginated) |
| GET | `/api/products/{id}` | Get product by ID |
| POST | `/api/products` | Create new product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |

**Example Request:**
```bash
curl http://localhost:8082/api/products
```

#### Billing Service (Port: 8083)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/bills` | List all bills |
| GET | `/api/bills/{id}` | Get bill by ID |
| GET | `/api/bills/{id}/productItems` | Get items for a specific bill |

**Example Request:**
```bash
curl http://localhost:8083/api/bills/1
```

**Sample Response:**
```json
{
  "billingDate": "2025-11-16T16:31:40.096+00:00",
  "customerId": 1,
  "customer": null,
  "_links": {
    "self": {
      "href": "http://localhost:8083/api/bills/1"
    },
    "bill": {
      "href": "http://localhost:8083/api/bills/1"
    },
    "productItems": {
      "href": "http://localhost:8083/api/bills/1/productItems"
    }
  }
}
```

### Through Gateway (Port: 8888)
```bash
# Customers through gateway
curl http://localhost:8888/api/customers

# Products through gateway
curl http://localhost:8888/api/products
```

## 🔧 Configuration

### Environment Profiles

Each service supports multiple environments with externalized configuration:

- **Development**: Default profile
- **Production**: Use `-prod.properties` files

### Configuration Files Location

Configuration files are stored in the `config-repo/` directory:
- `application.properties` - Global configuration
- `{service-name}.properties` - Service-specific configuration
- `{service-name}-{profile}.properties` - Profile-specific configuration

### Key Configuration Properties

**Global (application.properties):**
```properties
spring.cloud.discovery.enabled=true
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
management.endpoints.web.exposure.include=*
```

**Service Ports:**
- Discovery Service: 8761
- Customer Service: 8081
- Inventory Service: 8082
- Billing Service: 8083
- Gateway Service: 8888

## 🏛️ Data Model

### Customer Entity
```java
@Entity
public class Customer {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String email;
}
```

### Product Entity
```java
@Entity
public class Product {
    @Id
    private String id;
    private String name;
    private double price;
    private int quantity;
}
```

### Bill Entity
```java
@Entity
public class Bill {
    @Id @GeneratedValue
    private Long id;
    private Date billingDate;
    private long customerId;
    @OneToMany(mappedBy = "bill")
    private List<ProductItem> productItems;
    @Transient 
    private Customer customer;
}
```

### ProductItem Entity
```java
@Entity
public class ProductItem {
    @Id @GeneratedValue
    private Long id;
    private String productId;
    private double unitPrice;
    private int quantity;
    @ManyToOne
    private Bill bill;
}
```

## 🔌 Inter-Service Communication

The Billing Service uses **OpenFeign** to communicate with other services:

```java
@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerRestClient {
    @GetMapping("/api/customers")
    PagedModel<Customer> getAllCustomers();
}

@FeignClient(name = "INVENTORY-SERVICE")
public interface ProductRestClient {
    @GetMapping("/api/products")
    PagedModel<Product> getAllProducts();
}
```

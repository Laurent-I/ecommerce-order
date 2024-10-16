# E-commerce Order Processing System

A Spring Boot application that demonstrates object-oriented design principles in handling different types of order items (physical products, digital products, and gift cards) for an e-commerce platform.

## Table of Contents
1. [Architecture & Design Decisions](#architecture--design-decisions)
2. [API Documentation](#api-documentation)
3. [Setup Instructions](#setup-instructions)
4. [Testing Instructions](#testing-instructions)
5. [Future Improvements](#future-improvements)
6. [Contributing](#contributing)

## Architecture & Design Decisions

### Object-Oriented Design Principles

#### 1. Abstraction
- `OrderItem` abstract class defines the common structure for all item types
- Abstract methods enforce consistent behavior across implementations
- Service interfaces abstract business logic from implementation details

#### 2. Inheritance
- Three concrete classes extend `OrderItem`:
  - `PhysicalProduct`: For tangible goods requiring shipping
  - `DigitalProduct`: For downloadable items
  - `GiftCard`: For electronic gift cards
- Each subclass adds specific attributes and behaviors

#### 3. Polymorphism
- Different item types can be treated uniformly through the `OrderItem` base class
- Each item type implements its specific behavior:
  - Physical products calculate shipping costs
  - Digital products generate download links
  - Gift cards handle email delivery

#### 4. Clean Code & Separation of Concerns
- Clear package structure:
  - `model`: Domain entities
  - `controller`: API endpoints
  - `service`: Business logic
  - `repository`: Data access
  - `dto`: Data transfer objects
- Use of interfaces for loose coupling
- Clear naming conventions following Java standards
- Single Responsibility Principle applied to all classes

### Design Choices & Trade-offs

1. **Single Table Inheritance**
   - Used `@Inheritance(strategy = InheritanceType.SINGLE_TABLE)` for simplicity
   - Trade-off: Less normalized database but better performance
   - Alternative considered: Table per class (rejected due to complexity)

2. **DTO Pattern**
   - Separate DTOs for requests and responses
   - Trade-off: More boilerplate but better API control
   - Benefits: Versioning flexibility, data hiding

3. **In-Memory Database**
   - H2 database for simplicity and ease of testing
   - Trade-off: Not persistent but perfect for demonstration

## API Documentation

### 1. Create Order
- **Method:** POST
- **URL:** `/api/orders`
- **Request Body:**
```json
{
  "items": [
    {
      "type": "PHYSICAL",
      "productName": "Laptop",
      "price": 10000,
      "quantity": 2,
      "shippingWeight": 1.5
    },
    {
      "type": "DIGITAL",
      "productName": "E-book",
      "price": 15000,
      "quantity": 1
    },
    {
      "type": "GIFT_CARD",
      "productName": "Gift Card",
      "price": 25000,
      "quantity": 1,
      "recipientEmail": "recipient@example.com"
    }
  ]
}
```
- **Response:**
```json
{
  "orderId": 1,
  "items": [
    {
      "type": "PHYSICAL",
      "productName": "Laptop",
      "price": 10000,
      "quantity": 2,
      "totalPrice": 20000,
      "shippingCost": 1500
    },
    {
      "type": "DIGITAL",
      "productName": "E-book",
      "price": 15000,
      "quantity": 1,
      "totalPrice": 15000,
      "downloadLink": "https://download.example.com/1"
    },
    {
      "type": "GIFT_CARD",
      "productName": "Gift Card",
      "price": 25000,
      "quantity": 1,
      "totalPrice": 25000
    }
  ],
  "totalPrice": 60000,
  "totalShippingCost": 1500,
  "grandTotal": 61500
}
```

### 2. Get Order
- **Method:** GET
- **URL:** `/api/orders/{id}`
- **Response:** Same as create order response

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. Clone the repository:
```bash
git clone [repository-url]
cd ecommerce-order-processing
```

2. Build the project:
```bash
./mvnw clean install
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

## Testing Instructions

### 1. Using Postman

#### Setting up the Collection

1. Download [Postman](https://www.postman.com/downloads/)
2. Create a new Collection named "E-commerce Order Processing"
3. Set up a collection variable:
   - Click on the collection → Variables
   - Add a variable named `base_url` with initial and current value: `http://localhost:8080`

#### Creating an Order

1. Create a new request:
   - Method: `POST`
   - URL: `{{base_url}}/api/orders`
   - Headers:
     ```
     Content-Type: application/json
     ```
   - Body (raw JSON):
     ```json
     {
       "items": [
         {
           "type": "PHYSICAL",
           "productName": "Laptop",
           "price": 10000,
           "quantity": 2,
           "shippingWeight": 1.5
         },
         {
           "type": "DIGITAL",
           "productName": "E-book",
           "price": 15000,
           "quantity": 1
         },
         {
           "type": "GIFT_CARD",
           "productName": "Gift Card",
           "price": 25000,
           "quantity": 1,
           "recipientEmail": "recipient@example.com"
         }
       ]
     }
     ```

#### Testing Different Scenarios

1. Physical Product Only:
```json
{
  "items": [
    {
      "type": "PHYSICAL",
      "productName": "Desktop Computer",
      "price": 50000,
      "quantity": 1,
      "shippingWeight": 5.0
    }
  ]
}
```

2. Digital Product Only:
```json
{
  "items": [
    {
      "type": "DIGITAL",
      "productName": "Software License",
      "price": 30000,
      "quantity": 1
    }
  ]
}
```

3. Gift Card Only:
```json
{
  "items": [
    {
      "type": "GIFT_CARD",
      "productName": "Birthday Gift Card",
      "price": 10000,
      "quantity": 1,
      "recipientEmail": "birthday@example.com"
    }
  ]
}
```

#### Error Testing

1. Test Invalid Item Type:
```json
{
  "items": [
    {
      "type": "INVALID_TYPE",
      "productName": "Test Product",
      "price": 1000,
      "quantity": 1
    }
  ]
}
```

2. Test Missing Required Fields:
```json
{
  "items": [
    {
      "type": "PHYSICAL",
      "price": 1000,
      "quantity": 1
    }
  ]
}
```

### 2. Using cURL

Create an order:
```bash
curl -X POST http://localhost:8080/api/orders \
-H "Content-Type: application/json" \
-d '{
  "items": [
    {
      "type": "PHYSICAL",
      "productName": "Laptop",
      "price": 10000,
      "quantity": 2,
      "shippingWeight": 1.5
    },
    {
      "type": "DIGITAL",
      "productName": "E-book",
      "price": 15000,
      "quantity": 1
    },
    {
      "type": "GIFT_CARD",
      "productName": "Gift Card",
      "price": 25000,
      "quantity": 1,
      "recipientEmail": "recipient@example.com"
    }
  ]
}'
```

Retrieve an order:
```bash
curl http://localhost:8080/api/orders/1
```

### 3. Using H2 Console

1. Open http://localhost:8080/h2-console
2. Use these settings:
   - JDBC URL: `jdbc:h2:mem:ecommercedb`
   - Username: `sa`
   - Password: (leave empty)
3. Click "Connect"
4. You can now browse the database tables:
   - Check `ORDERS` table for order details
   - Check `ORDER_ITEM` table for item details

## Project Structure
```
src/main/java/com/ecommerce/
├── InterviewApplication.java
├── model/
│   ├── OrderItem.java
│   ├── PhysicalProduct.java
│   ├── DigitalProduct.java
│   ├── GiftCard.java
│   └── Order.java
├── controller/
│   └── OrderController.java
├── service/
│   ├── OrderService.java
│   └── OrderServiceImpl.java
├── repository/
│   └── OrderRepository.java
└── dto/
    ├── OrderRequest.java
    └── OrderResponse.java
```

## Future Improvements

1. Add authentication and authorization
2. Implement order status management
3. Add inventory tracking
4. Include payment processing
5. Add more comprehensive error handling
6. Implement caching for better performance
7. Add event-driven architecture for order processing
8. Include comprehensive unit and integration tests

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details

## Acknowledgments

* Spring Boot documentation
* Maven documentation
* H2 Database
* Lombok project

---

For any additional questions or support, please open an issue in the repository.
- Laurent Irakarama 😉

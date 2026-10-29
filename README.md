
# E-commerse Website name : Zestora

# file structure : 
```
E-commerse/
├── .mvn/
│   └── wrapper/
├── admin-mode/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
|   |── .dockerignore
│   ├── Dockerfile
│   └── pom.xml
├── auth-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
|   |── .dockerignore
│   ├── Dockerfile
│   └── pom.xml
├── gateway-eventbus/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
|   |── .dockerignore
│   ├── Dockerfile
│   └── pom.xml
├── infrastructure/
│   ├── postgres/
│   ├── redis/
├── merchant-mode/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
|   |── .dockerignore
│   ├── Dockerfile
│   └── pom.xml
├── user-mode/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
|   |── .dockerignore
│   ├── Dockerfile
│   └── pom.xml
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
├── LICENSE
├── .gitattributes
└── .gitignore

```

# Architecture :
<div align="center">
<img width="402" height="612" alt="image" src="https://github.com/user-attachments/assets/28b830ab-7afc-48ff-903f-0a53c4dd67a0" />
</div>

## 1. Routing, RateLimiting, Authentication :
### Routing : 
As Zestora is a big Microservice project, for a clean and modular architecture, I used Spring Cloud Framework for the routing and the gateway-eventBus act as the single point of entrence. Spring Cloud Gateway sits in front on port 8080 and has 4 routes : usermode, merchant mode, admin mode, keycloak.
### RateLimiting : 
Rate Limiting is done in Zestora with Token Bucket Algorithm which is implemented through Bucket4j library. In the system, each client IP gets its own bucket with a capacity of 20 tokens that refill at a rate of 20 tokens every minute ( amount is changeable anytime ). If a request can consume one token it is allowed through, otherwise the gateway returns a 429 Too Many Requests response.
### Authentication : 
Authentication in Zestora is handled by a dedicated auth-service using Spring Security and JWT (jjwt). Users register and log in via the auth endpoints; passwords are stored with BCrypt hashing. On successful login the service issues a short-lived JWT containing the user’s ID, email, username, and role. Protected requests must include this token as Authorization: Bearer …; a filter validates it and sets the security context, and the gateway shares the same JWT secret so it can enforce the token before traffic reaches other services.
## 2. Database Schema :
### User-Mode Database Schema :
<div align="center">
<img width="806" height="800" alt="image" src="https://github.com/user-attachments/assets/0864a18e-3450-4c5e-bbb4-5b431bebf5d2" />
</div>

Explanation :-

- User Management (user, Address): Stores customer profiles and links them to multiple addresses (OneToMany Relationship).
- Product Catalog (product): Manages items, pricing, status, and descriptions (linked via publicStoreId).
- Shopping Cart (cart, cartItem): Tracks active user carts and the specific product quantities selected before checkout.
- Order Processing (order, order_item): Captures completed orders and item snapshots (price/name at the time of purchase) to protect history from later product updates.
- Transactions (transaction): Logs payment details, methods, amounts, and statuses linked directly to orders.

### Merchant-Mode Database Schema :
<div aligh="center">
<img width="908" height="766" alt="image" src="https://github.com/user-attachments/assets/c467bd89-3831-48ed-a373-f47bd9d54b2f" />
</div>

Explanation :-

- Merchant Management (MerchantAccount): Stores merchant profiles with contact details, status, and timestamps.
- Store Management (Store, StoreAddress, StoreContact): Defines stores linked to a merchant, with address details and contact information (one-to-one for address/contact).
- Sales Tracking (StoreSales, StoreSalesItem): Records store sales summaries by time period and the individual product items sold (with name/quantity/amount snapshots).
- Product Catalog (Product, Inventory): Manages store products (pricing, status, description) and their stock levels (one-to-one inventory link).

## Codebase Details :
<div align="center">
<img width="1627" height="368" alt="image" src="https://github.com/user-attachments/assets/c16279af-adb5-4a63-a556-ff55e4fd39dc" />
</div>

> More is scheduled and will be added as the project slowly progress

---

<div align="center">
Author : Muhaimin Mukammel 
</div>

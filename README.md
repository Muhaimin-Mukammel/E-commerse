
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
│   └── ecommerce-realm.json
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
As this project is a big Microservice project, for a clean and modular architecture, I used Spring Cloud Framework for the routing and the gateway-eventBus act as the single point of entrence. Spring Cloud Gateway sits in front on port 8080 and has 4 routes : usermode, merchant mode, admin mode, keycloak.
### RateLimiting : 
Rate Limiting is done in Zestora with Token Bucket Algorithm which is implemented through Bucket4j library. In the system, each client IP gets its own bucket with a capacity of 20 tokens that refill at a rate of 20 tokens every minute ( amount is changeable anytime ). If a request can consume one token it is allowed through, otherwise the gateway returns a 429 Too Many Requests response.
### Authentication : 
Authentication of Zestora is done using OAuth2 with keycloak. This Auth system only allow’s /realms/** endpoints and everything else needs a valid JWT token. The Keycloak also take’s care of login and registration.

## 2. Database Schema :
### User-Mode Database Schema :
<div align="center">
<img width="647" height="813" alt="image" src="https://github.com/user-attachments/assets/7abb6e39-e183-4248-8135-13b5bf3c36b8" />
</div>

Explanation :-

- User Management (user, address): Stores customer profiles and links them to multiple addresses (OneToMany Relationship).
- Product Catalog (product, category): Manages items, pricing, inventory quantities, and organizes products into categories (Categories not Implemented).
- Shopping Cart (cart, cartItem): Tracks active user carts and the specific product quantities selected before checkout.
- Order Processing (order, order_item): Captures completed orders and item snapshots (price/name at the time of purchase) to protect history from latest updates/ensure accurate data.
- Transactions (transaction): Logs payment details, methods, amounts, and statuses (pending/success/failed) linked directly to orders (OneToMany Relationship).
- Feedback (reviews): Connects users and products to store ratings and written reviews (Not Implemented).

## Codebase Details :
<div align="center">
<img width="1616" height="368" alt="image" src="https://github.com/user-attachments/assets/9cbfd4d1-8a04-437b-9211-a72649beedf5" />
</div>

> More is scheduled and will be added as the project slowly progress

---

<div align="center">
Author : Muhaimin Mukammel 
</div>


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
│   ├── Dockerfile
│   └── pom.xml
├── core-library/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── gateway-eventbus/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
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
│   ├── Dockerfile
│   └── pom.xml
├── user-mode/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
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
<img width="402" height="612" alt="image" src="https://github.com/user-attachments/assets/28b830ab-7afc-48ff-903f-0a53c4dd67a0" />

## 1. Routing, RateLimiting, Authentication :
### Routing : 
As this project is a big Microservice project, for a clean and modular architecture, I used Spring Cloud Framework for the routing and the gateway-eventBus act as the single point of entrence. Spring Cloud Gateway sits in front on port 8080 and has 4 routes : usermode, merchant mode, admin mode, keycloak.
### RateLimiting : 
Rate Limiting is done in Zestora with Token Bucket Algorithm which is implemented through Bucket4j library. In the system, each client IP gets its own bucket with a capacity of 20 tokens that refill at a rate of 20 tokens every minute ( amount is changeable anytime ). If a request can consume one token it is allowed through, otherwise the gateway returns a 429 Too Many Requests response.
### Authentication : 
Authentication of Zestora is done using OAuth2 with keycloak. This Auth system only allow’s /realms/** endpoints and everything else needs a valid JWT token. The Keycloak also take’s care of login and registration.

## 2. Database Setup :
Zestora has a database-per-service setup using Change Data Capture (CDC) instead of cluttering everything into one giant shared database. When a seller updates an item, the app writes it directly to the isolated merchant-db. Right away, Debezium grabs that raw change straight from PostgreSQL's internal logs and throws it into Apache Kafka as a quick event message. The user-mode service listens to Kafka and mirrors that updated data into its own local user-db cache.This keeps the shopping side incredibly fast since buyers aren't locking up rows that sellers are trying to edit. The best part? Because all our data changes flow through Kafka as a continuous stream, we can easily plug in a centralized analytics database later down the road. Another database can suck up those identical event streams for heavy data crunching and admin dashboards, without slowing down the live shop for our customers.


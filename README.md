
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

> More is scheduled and will be added as the project slowly progress

---

<div align="center">
Author : Muhaimin Mukammel 
</div>

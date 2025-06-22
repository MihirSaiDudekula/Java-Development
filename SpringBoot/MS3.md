Awesome, Mihir! Let’s move into **Phase 3: API Gateway using Spring Cloud Gateway** — a crucial piece of your microservices architecture. Think of this like the **"front door"** to your entire system.

---

# 🚪 Phase 3: API Gateway – Spring Cloud Gateway

---

## 🧠 What is an API Gateway?

Imagine you have many microservices (`user`, `product`, `order`...).

🔁 Without a gateway:

* The frontend or client has to know and call multiple ports/services directly.
* You have to repeat things like CORS, auth checks, rate limiting in *every* service.

✅ With an API Gateway:

* It acts as a **single entry point**.
* It **routes traffic** to the correct service.
* It can **intercept, authenticate, transform, throttle**, etc.

💡 Just like a **reverse proxy** with superpowers.

---

## 📦 Step 1: Create `api-gateway` Project

Generate a new Spring Boot project using [Spring Initializr](https://start.spring.io) with these dependencies:

* ✅ `Spring Cloud Gateway`
* ✅ `Spring Boot DevTools`
* ✅ `Spring Web`
* ✅ `Eureka Discovery Client`

---

### 📁 Project Structure

```
api-gateway/
├── src/
│   └── main/java/com/yourname/gateway/ApiGatewayApplication.java
├── resources/
│   ├── application.yml
```

---

## 🧱 Step 2: Main Class

```java
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
```

---

## ⚙️ Step 3: `application.yml`

Let’s register the Gateway with Eureka and define route mappings.

```yaml
server:
  port: 8080

spring:
  application:
    name: api-gateway

  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/api/users/**

        - id: product-service
          uri: lb://product-service
          predicates:
            - Path=/api/products/**

        - id: order-service
          uri: lb://order-service
          predicates:
            - Path=/api/orders/**

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

---

### 🔍 What’s Happening Here?

* `uri: lb://user-service` means:
  🔄 "Load balance requests via Eureka to a service named `user-service`"

* `Path=/api/users/**` means:
  🚪 "If request starts with `/api/users`, route to user-service"

---

## 🔁 Example Requests

| Client Call                              | Gateway Forwards To                 |
| ---------------------------------------- | ----------------------------------- |
| `GET http://localhost:8080/api/users`    | `user-service` → `/api/users`       |
| `GET http://localhost:8080/api/products` | `product-service` → `/api/products` |
| `POST http://localhost:8080/api/orders`  | `order-service` → `/api/orders`     |

⚠️ Make sure the paths match between your services and routes.

---

## ✅ Step 4: Run Everything

### Start These (in order):

1. `discovery-server` (on port 8761)
2. `user-service`, `product-service`, `order-service` (each on its port)
3. `api-gateway` (on port 8080)

🎉 Now try hitting:

* `http://localhost:8080/api/users`
* `http://localhost:8080/api/products`
* `http://localhost:8080/api/orders`

All routed through one port — the gateway!

---

## 🔐 Preview: Securing the Gateway (Coming in Phase 7)

Spring Cloud Gateway is the best place to:

* Intercept JWTs and validate authentication before forwarding
* Apply global security rules, CORS, rate limiting

We’ll do this after setting up our `auth-service` later in the course.

---

## 🧪 Mini-Project – Gateway Integration

✅ Build the `api-gateway`
✅ Register it in Eureka
✅ Route to 3 services: user, product, order
✅ Hit the `/api/...` endpoints via the gateway port `8080`
✅ Verify dynamic discovery (try stopping `product-service` and see it disappear)

---

## ✅ Summary

| Topic                | You Now Understand 💡               |
| -------------------- | ----------------------------------- |
| Spring Cloud Gateway | Single entry point to microservices |
| Eureka Integration   | Dynamic routing with `lb://...`     |
| Path-based Routing   | Match routes to services cleanly    |
| Port Consolidation   | Clients only see port `8080`        |

---

## 🔜 Next: Phase 4 – Synchronous Inter-Service Calls

We’ll now see how one service (e.g., `order-service`) **calls another** (e.g., `user-service`) using:

* `WebClient` (recommended)
* Load-balanced calls using service names

Let me know if you'd like help **dockerizing** this setup yet or if you're ready for the next phase!

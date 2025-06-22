Great, Mihir! Let’s jump into **Phase 4 – Synchronous Inter-Service Calls** in Spring Boot Microservices.

---

# 🔄 Phase 4: Synchronous Inter-Service Communication

## 🧠 Why Do Services Need to Talk to Each Other?

In microservices, services are **autonomous**, but they often depend on one another.

📦 For example:

* The `order-service` needs **user info** from `user-service`
* The `product-service` may need **stock** info from `inventory-service`

These are **synchronous** (blocking) HTTP calls:
Service A calls Service B and waits for a response.

---

## 🔧 Tools of the Trade

| Option         | Description                              | Status       |
| -------------- | ---------------------------------------- | ------------ |
| `RestTemplate` | Old-style, still used in legacy projects | ✅ Deprecated |
| `WebClient`    | New non-blocking reactive HTTP client    | ✅ Preferred  |

We’ll use **WebClient** — it works in both normal and reactive Spring apps.

---

## ✅ Step 1: Add Dependencies

In `order-service` (the one making the HTTP call), add:

```xml
<!-- Add WebClient -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>

<!-- For Eureka service name resolution -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

> ⚠️ Even if you’re not doing reactive programming, WebClient needs `webflux`.

---

## ✅ Step 2: Enable Load-Balancing with Service Discovery

You want to say:

🔁 "I want to call `http://user-service/api/users/{id}`
...not hardcoded IPs or ports."

### So you configure a `WebClient` bean like this:

```java
@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced  // 💡 Enables Eureka-based service name resolution
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
```

---

## ✅ Step 3: Use `WebClient` in Your Service

Let’s say your `OrderService` wants to fetch user data from `user-service`.

### Example:

```java
@Service
public class OrderService {

    private final WebClient.Builder webClientBuilder;

    public OrderService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public UserResponse getUserDetails(String userId) {
        return webClientBuilder.build()
                .get()
                .uri("http://user-service/api/users/{id}", userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block(); // Synchronous call
    }
}
```

📌 Explanation:

* `http://user-service/...`: This resolves via Eureka
* `.block()`: makes it a synchronous call
* `UserResponse`: your DTO representing the user’s data

---

## 🧪 Step 4: Test It

Make sure all services are:

* ✅ Registered in Eureka
* ✅ Exposing the right endpoints
* ✅ Reachable from the `order-service`

Then call your API:

```bash
curl http://localhost:8080/api/orders/123
```

Internally, it will call the `user-service` like this:

```bash
GET http://user-service/api/users/42
```

And compose the full response.

---

## 📁 Folder Summary So Far

```
api-gateway/           # Front door
discovery-server/      # Eureka server
user-service/          # Registers with Eureka
order-service/         # Registers, also calls user-service
```

---

## 💬 Common Use Case: Data Aggregation

You’ll often use this to:

* Enrich responses (e.g., Order includes User + Product info)
* Validate something across services
* Fetch reference data

---

## 💡 Best Practices

| Practice                    | Why                                        |
| --------------------------- | ------------------------------------------ |
| Use DTOs                    | Don’t expose internal models               |
| Handle failures             | Add try-catch or fallback logic            |
| Don’t call too deep         | Avoid long chains (A→B→C→D)                |
| Prefer async where possible | We’ll do that in Phase 5 (Kafka, RabbitMQ) |

---

## ✅ Mini-Project Idea

Make `order-service` call `user-service`:

* Fetch user details
* Combine with order info
* Return a "Rich Order" response

---

## 🔜 Up Next: Phase 5 – Asynchronous Communication

We’ll now explore **decoupling services using events**:

* 🔥 Kafka for publishing/subscribing to **streams**
* 📨 RabbitMQ for handling **commands/tasks** (e.g., "send email")

Async messaging is **faster**, **looser** and **more scalable**.

Let me know if you'd like to start with Kafka or RabbitMQ first — or go in the order of both!

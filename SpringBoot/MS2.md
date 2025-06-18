Awesome! Welcome to **Phase 2 – Eureka Service Discovery**, Mihir. This is where your microservices will start **"talking to each other"** intelligently without hardcoded URLs. Let's break this down, one step at a time, in our usual beginner-friendly + hands-on approach.

---

# 🌐 Phase 2: Eureka Service Discovery

---

## 🧠 Concept First – What is Eureka?

Eureka is a **Service Discovery** tool from Netflix, and it works like a **phone directory** for your microservices.

### 💡 Problem:

Without Eureka, you'd hardcode IPs or URLs in every service (e.g., `http://localhost:8081/user-service`). That doesn't scale!

### ✅ With Eureka:

Each microservice **registers itself** with Eureka (like checking in to a hotel).
Other services ask Eureka **“Hey, where’s product-service?”**, and Eureka gives them the current IP/port.

---

## 🧩 Tools We'll Use

| Tool                     | Purpose                                 |
| ------------------------ | --------------------------------------- |
| **Eureka Server**        | The registry itself                     |
| **Eureka Client**        | Each microservice that registers itself |
| **Spring Cloud Netflix** | Spring’s wrapper around Eureka          |

---

## 🔧 Step-by-Step Guide

### 1️⃣ Create a Eureka Server

You can either:

* Use a new project (`discovery-server`)
* Or include it in your mono-repo

---

### 📦 Generate Eureka Server with Spring Initializr

**Dependencies**:

* `Spring Boot DevTools`
* `Spring Web`
* `Spring Cloud Discovery → Eureka Server`

---

### 🧱 `DiscoveryServerApplication.java`

```java
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
```

---

### ⚙️ `application.yml` (for `discovery-server`)

```yaml
server:
  port: 8761

spring:
  application:
    name: discovery-server

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

> 🔍 Note: Since the Eureka **server** doesn’t register itself, both flags are set to `false`.

---

### ✅ Run it!

Visit [http://localhost:8761](http://localhost:8761) — you’ll see Eureka’s dashboard.
Initially, it will say "No instances available" — that’s okay! Let’s fix that next.

---

## 2️⃣ Register a Service with Eureka (e.g., `user-service`)

---

### 📦 Add Dependency to `user-service`

In your `pom.xml`:

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

Also, make sure you have a compatible Spring Cloud BOM in your `dependencyManagement`:

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-dependencies</artifactId>
      <version>2022.0.3</version> <!-- Or whatever is latest compatible -->
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

---

### 🧱 Add Annotation

```java
@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
```

---

### ⚙️ `application.yml`

```yaml
server:
  port: 8081

spring:
  application:
    name: user-service

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

---

### 🏁 Run & Verify

1. Start `discovery-server`
2. Start `user-service`
3. Go to [http://localhost:8761](http://localhost:8761)
4. 🎉 You’ll see `user-service` listed under Registered Instances!

---

## 3️⃣ Repeat for Other Services

Just copy the same steps for:

* `product-service`
* `order-service`
* `notification-service`
* `auth-service`

Each one:

* Adds Eureka client dependency
* Adds `@EnableDiscoveryClient`
* Sets a unique `spring.application.name`
* Connects to the same Eureka server at `localhost:8761`

---

## 📦 Example: `product-service/application.yml`

```yaml
server:
  port: 8082

spring:
  application:
    name: product-service

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

---

## ✅ What We Achieved in Phase 2

* 🎯 Central Eureka Server at port 8761
* 🧾 Each microservice now registers itself dynamically
* 🛰 No need to hardcode addresses for communication (we’ll do dynamic lookups in next phase)

---

## 🧪 Mini-Project (End of Phase 2)

✅ Create `discovery-server`
✅ Register `user-service`, `product-service`, and `order-service`
✅ Verify discovery via Eureka dashboard

---

## 🔜 Coming Up: Phase 3 – API Gateway

Here, we’ll:

* Introduce Spring Cloud Gateway
* Route all requests through the gateway (e.g., `/api/products → product-service`)
* Begin basic authentication filtering

Let me know when you're ready to continue, or if you'd like me to help you **set up a local `docker-compose` file** for running all services together!

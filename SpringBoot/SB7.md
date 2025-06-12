Perfect, Mihir. We're now entering **Phase 6 – Data Access & Transactions**, and this is where we **interact meaningfully with databases** using Spring’s abstraction: **Spring Data JPA**.

Let’s break this phase into digestible parts:

---

# 🧱 Phase 6 – Data Access & Transactions

---

## 🔶 Part 1 – Spring Data JPA: Foundations

---

### 💡 What's JPA?

* **JPA (Jakarta Persistence API)** is a Java specification to **map Java classes to database tables**.
* Think of it as ORM: Object → Relational Mapping.

Spring doesn't **implement** JPA; instead, it uses **Hibernate** (a popular JPA implementation) under the hood.

---

### 📦 Spring Data JPA

Spring Boot makes JPA super simple by giving you:

* `@Entity` to mark Java classes as DB tables
* `JpaRepository` interface to generate queries
* Auto-configuration for data sources

Let’s see it in action step-by-step.

---

## ✅ Step-by-Step Code Walkthrough

---

### Step 1: Dependencies (`pom.xml`)

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
  <scope>runtime</scope>
</dependency>
```

We’re using H2 (an in-memory database) for simplicity.

---

### Step 2: Configuration (`application.properties` or `.yml`)

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

* `ddl-auto=update` → Automatically creates/updates tables
* `h2.console.enabled=true` → Lets you access DB in browser (`/h2-console`)

---

### Step 3: Define an Entity (Table)

```java
@Entity
public class Product {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private double price;

  // Getters/Setters
}
```

**Explanation:**

| Annotation        | Meaning                      |
| ----------------- | ---------------------------- |
| `@Entity`         | Tells JPA this is a DB table |
| `@Id`             | Marks the primary key        |
| `@GeneratedValue` | Auto-generates IDs           |

---

### Step 4: Create Repository

```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
}
```

Because `JpaRepository` is generic, we just tell it:

```java
<Product, Long> → Entity type and ID type
```

It gives us built-in methods:

* `save()`
* `findById()`
* `findAll()`
* `delete()`
* And we can define queries like `findByName()`

---

### Step 5: Use in a Service

```java
@Service
public class ProductService {
  
  private final ProductRepository repo;

  public ProductService(ProductRepository repo) {
    this.repo = repo;
  }

  public Product addProduct(Product p) {
    return repo.save(p);
  }

  public List<Product> getAll() {
    return repo.findAll();
  }

  public Optional<Product> getById(Long id) {
    return repo.findById(id);
  }
}
```

---

### Step 6: Controller

```java
@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  @PostMapping
  public Product create(@RequestBody Product p) {
    return service.addProduct(p);
  }

  @GetMapping
  public List<Product> all() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> byId(@PathVariable Long id) {
    return service.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }
}
```

---

## 🎯 TL;DR — CRUD Flow Recap

```text
Request → Controller → Service → Repository → DB
                      ←     ←     ←     ←
```

---

## 🔄 Part 2 – Transactions

---

### 💡 Why Transactions?

Imagine two DB operations:

1. Deduct money from A
2. Add money to B

If step 1 succeeds but step 2 fails, your data is corrupted. You want both to **commit together**, or **roll back together**.

---

### ✅ Use `@Transactional`

```java
@Service
public class BankService {

  private final AccountRepository repo;

  public BankService(AccountRepository repo) {
    this.repo = repo;
  }

  @Transactional
  public void transfer(Long fromId, Long toId, double amount) {
    Account from = repo.findById(fromId).orElseThrow();
    Account to = repo.findById(toId).orElseThrow();

    from.setBalance(from.getBalance() - amount);
    to.setBalance(to.getBalance() + amount);

    repo.save(from);
    repo.save(to);
  }
}
```

Spring manages the transaction for you — it commits at the end or rolls back on exception.

---

### 🛡️ Tips

* Place `@Transactional` on **service-layer** methods, not repo.
* Exceptions can **trigger rollbacks**. Customize with `rollbackFor = ...` if needed.
* You can nest transactional calls, but be cautious — Spring uses **proxy-based AOP** to manage this.

---

## 🧪 Practice Exercises

| Task                                                    | Description             |
| ------------------------------------------------------- | ----------------------- |
| 🛒 Create `Order`, `Product`, and `Customer` entities   | 1-to-many relationships |
| 🧾 Add a transaction to create order + deduct inventory |                         |
| 💥 Simulate a failure midway and ensure rollback        |                         |
| 🔍 Use `findBy` queries with custom fields              |                         |

---

## 🧠 Intuition

| Concept          | Why It Matters                     |
| ---------------- | ---------------------------------- |
| `@Entity`        | Treat Java class like DB table     |
| `JpaRepository`  | You write less SQL                 |
| `@Transactional` | Prevent data corruption in failure |

---

Let me know when you're ready to proceed to **Phase 7: Production-Ready Spring Boot**, where we’ll explore **Actuator**, **Docker**, and **cloud deployment** concepts!

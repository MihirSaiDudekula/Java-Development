
---

Before jumping into code that uses JPA (`@Entity`, `JpaRepository`) and H2, **we should have first explained the following concepts**:

---

## 🧠 What is JPA?

**JPA (Java Persistence API)** is a specification (not an implementation) that lets you **map Java objects to database tables**. It provides a clean way to store and retrieve objects in a relational database—without writing SQL manually.

### Why JPA?

* It abstracts away boilerplate JDBC code.
* You work with **objects**, not rows or SQL statements.
* Clean integration with Spring via **Spring Data JPA**.

---

## 🔌 What is Spring Data JPA?

Spring Data JPA is **Spring’s implementation layer** on top of JPA that makes working with databases even easier.

You define a simple interface like:

```java
public interface TodoRepository extends JpaRepository<Todo, Long> {}
```

And Spring auto-generates all CRUD methods (like `save()`, `findAll()`, etc.) at runtime.

---

## 🗃 What is H2 Database?

**H2** is a lightweight, fast, **in-memory relational database**. It’s great for development and testing because:

* It requires no setup.
* It runs in-memory (nothing persists unless configured).
* You can view it using a browser console (`/h2-console`).

Think of it as your practice DB before you ever connect to a real one like MySQL or PostgreSQL.

---

## 🏷 What is `jakarta.persistence`?

This package comes from the JPA specification (which used to be under `javax.persistence`, but got renamed to `jakarta.persistence`).

Here are the key annotations from it:

| Annotation        | Meaning                                               |
| ----------------- | ----------------------------------------------------- |
| `@Entity`         | Marks a Java class as a table in the database         |
| `@Id`             | Denotes the primary key field                         |
| `@GeneratedValue` | Auto-generates values for the ID (e.g., incrementing) |

---

## 🧩 Why will We Use All This?

To implement a **real, persistent Todo app**, we needed a:

| Need                    | Tool We Used                              |
| ----------------------- | ----------------------------------------- |
| Store todos in a table  | `@Entity`, `@Id`, `JpaRepository`         |
| Perform CRUD operations | Spring Data JPA (`save`, `findAll`, etc.) |
| Temporary, fast DB      | H2 in-memory DB                           |
| Clean app structure     | Controller → Service → Repository         |

---

That’s amazing to hear, Mihir! 🙌 I’m glad you’re enjoying it — that’s how learning should be: **hands-on, chunked, and fun**.

You're absolutely right to ask for a check — let’s review **what was in Phase 1 originally**:

---

## ✅ Original Phase 1 (from your roadmap):

### 1.1 Build a Simple REST API (Modern First Approach)

* ✅ Create Spring Boot project using [Spring Initializr](https://start.spring.io)
* ✅ Concepts:

  * `@RestController`, `@GetMapping`, `@PostMapping`
  * Application structure: Controller → Service → Repository
  * `@Entity`, `JpaRepository`, basic CRUD
* ✅ Run & test using the embedded Tomcat server (`main()` method runs it)
* ✅ Goal: Confidence boost—you’ll see results fast

### 1.2 Dependency Injection & Annotations

* ✅ What is DI? Let Spring supply objects (e.g., services, repositories)
* ✅ Types:

  * Constructor injection (✔️ preferred)
  * `@Autowired` for fields and setters
* ✅ Stereotypes: `@Component`, `@Service`, `@Repository`, `@Controller`

---

## 📌 Phase 1 – Part 1 Covered:

* ✅ Getting project running with embedded server
* ✅ `@RestController`, `@GetMapping`
* ✅ Code structure with Controller → Service
* ✅ What DI is and how constructor injection works
* ✅ Basic JSON response

What’s **left** for **Phase 1 – Part 2**:

* 🔲 `@PostMapping` → Accepting data from user
* 🔲 `@Entity`, `JpaRepository` → Database access
* 🔲 Complete CRUD (Create, Read, Update, Delete)

---

# 🚀 Phase 1 – Part 2: Real CRUD with Spring Boot + H2 Database

---

## 🎯 What You'll Build

A full **Todo backend API** that supports:

* `GET /todos` → Read all todos
* `POST /todos` → Add a new todo
* `DELETE /todos/{id}` → Delete a todo
* `PUT /todos/{id}` → Update a todo

---

## 🧱 Step 1: Add Dependencies (if you haven’t yet)

In `pom.xml`, make sure you have:

```xml
<dependencies>
    <!-- Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- JPA (Java Persistence API) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- H2 In-Memory Database -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

✅ Now Spring Boot can talk to a database.

---

## 🗃 Step 2: Create the `Todo` Entity

```java
package com.example.todo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity  // Tells Spring this maps to a DB table
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long id;

    private String title;
    private boolean done;

    // Constructors
    public Todo() {}  // Required by JPA

    public Todo(String title, boolean done) {
        this.title = title;
        this.done = done;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public boolean isDone() { return done; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDone(boolean done) { this.done = done; }
}
```

---

## 🧠 Intuition:

> `@Entity` turns your Java object into a table.
> The fields become table columns.
> Spring Data JPA gives you database access *without* writing SQL.

---

## 🗂 Step 3: Create a Repository (Database Access Layer)

```java
package com.example.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // Nothing to write! Spring gives CRUD for free
}
```

✅ You now have:

* `save()`, `findAll()`, `findById()`, `deleteById()` out of the box.

---

## 🧠 Intuition:

> Think of `JpaRepository` as a "magic" interface. Spring detects it and gives you full database operations instantly.

---

## 🧩 Step 4: Connect Repository to Service

```java
package com.example.todo;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public Todo updateTodo(Long id, Todo updatedTodo) {
        return todoRepository.findById(id)
            .map(existing -> {
                existing.setTitle(updatedTodo.getTitle());
                existing.setDone(updatedTodo.isDone());
                return todoRepository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("Todo not found"));
    }
}
```

---

## 🧑‍💻 Step 5: Update Controller with Full CRUD

```java
package com.example.todo;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/todos")  // All endpoints start with /todos
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoService.addTodo(todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        return todoService.updateTodo(id, updatedTodo);
    }
}
```

---

## 🧪 Step 6: Test It With Postman or cURL

### 📬 GET All Todos

```
GET http://localhost:8080/todos
```

### ➕ POST New Todo

```
POST http://localhost:8080/todos
Content-Type: application/json

{
  "title": "Learn Spring Boot",
  "done": false
}
```

### 🔄 PUT to Update

```
PUT http://localhost:8080/todos/1
Content-Type: application/json

{
  "title": "Learn Spring Boot (Updated)",
  "done": true
}
```

### ❌ DELETE Todo

```
DELETE http://localhost:8080/todos/1
```

---

## 🔎 BONUS: H2 Console to See Your Database

Add this in `application.properties`:

```properties
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
```

Then visit:
👉 `http://localhost:8080/h2-console`
Use JDBC URL: `jdbc:h2:mem:testdb`

Now you can see your tables live!

---

## 🧠 Recap – Phase 1 (Completed!)

| Feature           | Tool / Concept                                   |
| ----------------- | ------------------------------------------------ |
| Build REST API    | `@RestController`, `@GetMapping`, `@PostMapping` |
| Web server        | Embedded Tomcat (auto-run with `main()`)         |
| Project structure | Controller → Service → Repository                |
| Database          | H2 in-memory DB                                  |
| ORM               | `@Entity`, `JpaRepository`                       |
| DI                | Constructor-based (recommended)                  |
| JSON I/O          | Auto-handled by Spring using Jackson             |

---

## 🚀 Coming Up Next

**Phase 2: Core Spring Foundations**

We’ll dive deeper into:

* How Spring wires your beans (IoC, ApplicationContext)
* Bean scopes (`singleton`, `prototype`)
* Managing multiple beans (`@Primary`, `@Qualifier`)

Would you like to start Phase 2 now? Or do a mini-practice project with what we just learned?



That's excellent and really constructive feedback, Mihir — thank you for calling that out. You're absolutely right: introducing JPA, `@Entity`, or H2 without even briefly explaining what they *are* or *why they're used* can easily break intuition, especially for someone starting fresh. I’ll make sure that from here on, **every new concept or tool gets a short, intuitive introduction** before jumping into usage. 💯

---

# 🌱 Phase 2 – Core Spring Foundations

Welcome to **Phase 2**, where we slow down a bit and dig deeper into the core engine behind Spring — the **Inversion of Control container**, **beans**, and **scopes**. Think of this as learning how Spring is *powering* all the magic we just used.

---

## 🧩 Part 1 – Inversion of Control (IoC) & ApplicationContext

---

### 🧠 What is *Inversion of Control*?

Normally in Java, **you create objects** like this:

```java
Service s = new Service();
```

But in Spring, **you don’t `new` anything**. Instead:

* You let Spring **create**, **configure**, and **manage** the object for you.
* You just *tell Spring* what classes should be managed (with annotations like `@Component` or `@Service`).
* This inversion of who controls what is called **Inversion of Control (IoC)**.

---

### 🧪 Simple Example Without Spring

```java
public class BillingService {
    private EmailService emailService = new EmailService(); // tight coupling
}
```

Hard to test, hard to replace `EmailService`.

---

### ✅ With Spring:

```java
@Service
public class BillingService {
    private final EmailService emailService;

    public BillingService(EmailService emailService) {
        this.emailService = emailService;
    }
}
```

Now Spring:

* Finds the `EmailService` bean
* Creates it
* Injects it into `BillingService` without you writing any `new` keyword

---

### 🔌 What is ApplicationContext?

The **ApplicationContext** is Spring’s **container** — it:

* Reads your annotations and configurations
* Creates objects (called **beans**)
* Wires dependencies
* Manages object lifecycles

You can think of it like:

```plaintext
Your Code --> Spring Container (ApplicationContext) --> Creates & Connects your Beans
```

---

### 🧪 Let's See This in Code:

#### 1. Create Two Simple Services

```java
package com.example.phase2demo;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendEmail(String to) {
        System.out.println("📧 Email sent to: " + to);
    }
}
```

```java
package com.example.phase2demo;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final EmailService emailService;

    public UserService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void registerUser(String email) {
        System.out.println("✅ Registered user: " + email);
        emailService.sendEmail(email);
    }
}
```

#### 2. Use It in the Main App

```java
package com.example.phase2demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Phase2DemoApplication implements CommandLineRunner {

    private final UserService userService;

    public Phase2DemoApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Phase2DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        userService.registerUser("mihir@example.com");
    }
}
```

---

## 💡 Intuition Recap

| Concept                   | Meaning                                                                                |
| ------------------------- | -------------------------------------------------------------------------------------- |
| IoC                       | You don't control object creation — Spring does.                                       |
| ApplicationContext        | A container that holds and wires your app's components                                 |
| `@Service` / `@Component` | Tells Spring to treat your class as a *bean* (i.e., a managed object in the container) |
| Constructor Injection     | Spring figures out what dependencies to pass in and "plugs them in" using constructors |

---

## 🧩 Part 2 – Beans & Scopes

---

### 🧠 What is a Bean?

A **bean** is just a **Java object** managed by Spring.
If you annotate a class with `@Component`, `@Service`, or `@Repository` → Spring treats it as a bean.

### 🤔 Why does this matter?

Because Spring:

* Controls when it’s created
* Decides how long it lives
* Can inject it into other places

---

### 🧪 Let’s Check Bean Scope

Create a quick class:

```java
package com.example.phase2demo;

import org.springframework.stereotype.Component;

@Component
public class MyCounter {
    private int count = 0;

    public int incrementAndGet() {
        return ++count;
    }
}
```

Now use it in two different components:

```java
package com.example.phase2demo;

import org.springframework.stereotype.Service;

@Service
public class TaskOne {
    private final MyCounter counter;

    public TaskOne(MyCounter counter) {
        this.counter = counter;
    }

    public void doSomething() {
        System.out.println("TaskOne: " + counter.incrementAndGet());
    }
}
```

```java
package com.example.phase2demo;

import org.springframework.stereotype.Service;

@Service
public class TaskTwo {
    private final MyCounter counter;

    public TaskTwo(MyCounter counter) {
        this.counter = counter;
    }

    public void doSomething() {
        System.out.println("TaskTwo: " + counter.incrementAndGet());
    }
}
```

And finally call them from `CommandLineRunner`:

```java
@Override
public void run(String... args) {
    taskOne.doSomething();
    taskTwo.doSomething();
}
```

---

### 🔍 Output:

```shell
TaskOne: 1
TaskTwo: 2  ✅ Same instance of MyCounter shared
```

---

### 💡 Default Scope: `singleton`

By default, every bean is created **once**, and the **same instance** is used everywhere.

Other scopes:

| Scope       | Meaning                                       |
| ----------- | --------------------------------------------- |
| `singleton` | Only one instance in the entire app (default) |
| `prototype` | A new instance every time it’s requested      |
| `request`   | One per HTTP request (for web apps)           |
| `session`   | One per session (for web apps)                |

You can specify it like this:

```java
@Component
@Scope("prototype")
public class MyCounter {
    // now a new object each time
}
```

---

## 🧩 Part 3 – `@Primary`, `@Qualifier`: Multiple Beans of Same Type

Let’s say you have two beans of the same type:

```java
@Component
public class SMSService implements NotificationService {
    public void send(String to) {
        System.out.println("Sent SMS to " + to);
    }
}

@Component
public class EmailService implements NotificationService {
    public void send(String to) {
        System.out.println("Sent Email to " + to);
    }
}
```

Now Spring will say “❓ Which one do you want?”

---

### ✅ Solution 1: Use `@Primary`

```java
@Primary
@Component
public class EmailService implements NotificationService {
    ...
}
```

Now EmailService will be used *by default* when you `@Autowired` a `NotificationService`.

---

### ✅ Solution 2: Use `@Qualifier`

```java
@Service
public class AlertService {

    private final NotificationService notificationService;

    public AlertService(@Qualifier("smsService") NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void alert() {
        notificationService.send("mihir@example.com");
    }
}
```

---

## ✅ Summary of Phase 2

| Concept            | You Learned                                                               |
| ------------------ | ------------------------------------------------------------------------- |
| IoC                | Let Spring control object creation and wiring                             |
| ApplicationContext | Core container that manages all Spring beans                              |
| Bean Scopes        | `singleton` (default), `prototype`, `request`, `session`                  |
| Bean Qualifiers    | Use `@Primary` or `@Qualifier` when multiple beans of the same type exist |

---

## 🧪 Your Practice Task (Optional)

Try creating a new feature in your todo app:

* Add a `NotificationService` interface
* Create two implementations: `ConsoleNotifier` and `EmailNotifier`
* Inject one using `@Qualifier` and call it in the service when a new todo is added



---

## **1. Introduction to Spring Application and Dependency Injection**

Spring is a **dependency injection (DI)**-based Java framework. In a Spring application:

* You define **beans** (objects managed by Spring).
* You specify how these beans are created and injected (provided) to other components.

### **Key Concept: Dependency Injection**

* It is a design pattern in which an object receives other objects it depends on.
* In Spring, DI is handled via:

  * **XML-based configuration**
  * **Annotation-based configuration**
  * **Java-based configuration**

---

## **2. XML-Based Configuration**

### **Overview:**

* XML files define beans and their dependencies explicitly.
* You provide Spring with an XML config file (e.g., `spring.xml`).

### **Steps:**

1. **Define Bean:**

   ```xml
   <bean id="vehicle" class="com.example.Bike" />
   ```
2. **Create Application Context:**

   ```java
   ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
   Vehicle vehicle = (Vehicle) context.getBean("vehicle");
   vehicle.drive();
   ```

### **Advantages:**

* Clear separation of configuration.
* Good for non-annotated legacy classes.

---

## **3. Annotation-Based Configuration**

### **Annotations Used:**

* `@Component`: Marks a class as a Spring bean.
* `@Autowired`: Automatically injects dependencies.
* `@ComponentScan`: Tells Spring where to search for annotated components.

### **Steps:**

1. **Mark class with `@Component`:**

   ```java
   @Component
   public class Car {
       public void drive() {
           System.out.println("Car is running...");
       }
   }
   ```

2. **Enable scanning in the configuration:**

   ```java
   @Configuration
   @ComponentScan(basePackages = "com.example")
   public class AppConfig {
   }
   ```

3. **Create Application Context:**

   ```java
   ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
   Car car = context.getBean(Car.class);
   car.drive();
   ```

### **Default Bean Naming:**

* If `@Component` is used without a name, the default bean ID is the class name with the first letter lowercase (e.g., `car` for `Car`).

---

## **4. Mixing XML and Annotation Configuration (Hybrid Configuration)**

You can use both XML and annotations in one project.

### **Example:**

* Use `@Component` on the `Tire` class.
* Define `Car` bean in XML and inject `Tire` using `@Autowired`.

```java
@Component
public class Tire {
    public String toString() {
        return "Tire brand is MRF";
    }
}
```

```java
public class Car {
    @Autowired
    private Tire tire;

    public void drive() {
        System.out.println("Driving with " + tire);
    }
}
```

```xml
<bean id="car" class="com.example.Car" />
<context:component-scan base-package="com.example" />
```

---

## **5. Property Injection via XML**

Spring supports **setter-based DI** using `<property>` tags:

```xml
<bean id="tire" class="com.example.Tire">
    <property name="brand" value="MRF" />
</bean>
```

Corresponding Java class:

```java
public class Tire {
    private String brand;

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String toString() {
        return "Tire brand: " + brand;
    }
}
```

---

## **6. Constructor Injection in XML**

Instead of using setter methods, you can inject values using constructors:

### **XML:**

```xml
<bean id="tire" class="com.example.Tire">
    <constructor-arg value="MRF" />
</bean>
```

### **Java:**

```java
public class Tire {
    private String brand;

    public Tire(String brand) {
        this.brand = brand;
    }

    public String toString() {
        return "Tire brand: " + brand;
    }
}
```

---

## **7. Using Maven with Spring**

To manage dependencies automatically, Spring apps often use **Maven**.

### **Add Spring Context Dependency:**

In `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>4.1.9.RELEASE</version> <!-- or latest stable -->
    </dependency>
</dependencies>
```

Maven downloads required JARs and sets up classpath.

---

## **8. Complete Example Using Annotations Only**

### **`SamsungS7.java`:**

```java
@Component
public class SamsungS7 {
    public void config() {
        System.out.println("Octa-core, 4GB RAM, 12MP Camera");
    }
}
```

### **`AppConfig.java`:**

```java
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
}
```

### **`App.java`:**

```java
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        SamsungS7 s7 = context.getBean(SamsungS7.class);
        s7.config();
    }
}
```

---

## **9. Summary of Key Annotations and Concepts**

| **Annotation**   | **Purpose**                                           |
| ---------------- | ----------------------------------------------------- |
| `@Component`     | Declares a class as a Spring bean                     |
| `@Autowired`     | Injects dependencies automatically                    |
| `@ComponentScan` | Tells Spring where to look for beans                  |
| `@Configuration` | Declares a class as a Java-based configuration source |

| **Concept**                   | **Explanation**                             |
| ----------------------------- | ------------------------------------------- |
| **Dependency Injection (DI)** | Decouples object creation from object usage |
| **Bean**                      | An object managed by the Spring container   |
| **ApplicationContext**        | The central interface for accessing beans   |

---


### ✅ **Goal of the Demo**

To build a small Spring app where:

* You have a **`MobileProcessor` interface**
* Two classes implement it: `Snapdragon` and `MediaTek`
* A `Samsung` class depends on a `MobileProcessor`
* We want Spring to **automatically inject the correct processor into Samsung**
* You see how annotations (`@Component`, `@Autowired`, `@Qualifier`, etc.) help you achieve this **without writing XML**

---

## 🧩 Step-by-Step Explanation

---

### 1. **Create an Interface**

```java
public interface MobileProcessor {
    void process();
}
```

This is the **common contract**. Any CPU (Snapdragon or MediaTek) will "process", but the exact implementation is up to them.

---

### 2. **Create Implementing Classes**

```java
@Component
public class Snapdragon implements MobileProcessor {
    public void process() {
        System.out.println("World's best CPU");
    }
}
```

```java
@Component
public class MediaTek implements MobileProcessor {
    public void process() {
        System.out.println("Second-best CPU");
    }
}
```

🧠 **Intuition**: By adding `@Component`, Spring knows, “I need to manage objects of this class.” It puts them into the **Spring Container** — think of it like Spring’s object storage.

---

### 3. **Create Dependent Class**

```java
@Component
public class Samsung {

    @Autowired
    @Qualifier("snapdragon") // or "mediaTek"
    MobileProcessor cpu;

    public void config() {
        System.out.println("Samsung Phone with:");
        cpu.process(); // Will call whichever CPU was injected
    }
}
```

🧠 **Key Concepts**:

* `@Autowired`: Spring tries to inject the dependency **automatically**
* `@Qualifier("snapdragon")`: Helps when **multiple beans** implement the same interface

---

### 4. **Application Configuration**

You can do this in two ways:

#### Option A: Manual Bean Setup (Traditional Java Config)

```java
@Configuration
public class AppConfig {
    
    @Bean
    public MobileProcessor processor() {
        return new Snapdragon(); // Or MediaTek
    }

    @Bean
    public Samsung phone() {
        return new Samsung();
    }
}
```

Here, you're **manually telling Spring** how to create objects.

#### Option B: Auto-Configuration (Preferred Modern Way)

Just use:

```java
@Configuration
@ComponentScan(basePackages = "com.your.package")
public class AppConfig {
}
```

🧠 `@ComponentScan` tells Spring: “Look in this package for any class marked with `@Component` and manage it.”

---

### 5. **Main Application to Run**

```java
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Samsung phone = context.getBean(Samsung.class);
        phone.config();
    }
}
```

---

## 🧪 What If Two Implementations Exist?

### ❌ Without `@Qualifier`

Spring gets confused — it throws:

```
NoUniqueBeanDefinitionException
```

Because it doesn’t know whether to use `Snapdragon` or `MediaTek`.

### ✅ How to Fix?

You have two options:

1. Add `@Primary` to the preferred bean class

   ```java
   @Component
   @Primary
   public class Snapdragon implements MobileProcessor {
   ```
2. Use `@Qualifier` where you inject the dependency

---

## 🔁 Summary of Key Annotations

| Annotation       | Purpose                                                           |
| ---------------- | ----------------------------------------------------------------- |
| `@Component`     | Marks a class as a Spring-managed bean                            |
| `@Autowired`     | Injects the bean automatically (by type)                          |
| `@Qualifier`     | Resolves ambiguity if multiple beans of the same type exist       |
| `@Primary`       | Marks one bean as the default choice if multiple candidates exist |
| `@Configuration` | Marks a class as a Java config file                               |
| `@ComponentScan` | Tells Spring to scan a package for `@Component`-annotated classes |
| `@Bean`          | Used in `@Configuration` classes to explicitly declare beans      |

---

## 🎯 Intuition Behind the Problem Solving

* **Why annotations?** They remove boilerplate and XML. Spring reads metadata from annotations and does the setup.
* **Why dependency injection?** To make your code loosely coupled. `Samsung` doesn’t care **which CPU** it gets; just needs **a CPU**.
* **Why `@ComponentScan`?** To avoid manually registering every bean.
* **Why `@Qualifier`?** To avoid confusion when more than one candidate exists for injection.

---



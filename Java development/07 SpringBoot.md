# **Advanced Notes on Spring Boot**  

---

## **1. Introduction to Spring Boot**  
### **What is Spring Boot?**  
Spring Boot is a framework built on top of the **Spring Framework**, designed to simplify the development of Java-based applications. It provides:  
- **Auto-configuration** to minimize boilerplate code.  
- **Embedded servers** like Tomcat, Jetty, and Undertow to run applications without external configuration.  
- **Production-ready features** such as health monitoring, metrics, and logging.  
- **Dependency management** through **Gradle** or **Maven** for easy integration of required libraries.  

---

## **2. Inversion of Control (IoC) & Dependency Injection (DI)**
### **Understanding Inversion of Control (IoC)**  
- IoC is a **design principle** where the control of object creation and management is transferred from the developer to the **Spring container**.  
- This helps in making applications **loosely coupled** and easier to maintain.  

### **What is Dependency Injection (DI)?**  
- DI is a technique where dependencies (objects that a class needs) are **injected** into a class rather than creating them manually using `new`.  
- Spring Boot uses **DI** extensively to manage beans efficiently.  

### **Example of DI**  
Instead of:  
```java
public class UserService {
    private OrderService orderService = new OrderService();  // Manual dependency creation
}
```
We use:  
```java
public class UserService {
    private final OrderService orderService;
    
    @Autowired  // Injecting dependency
    public UserService(OrderService orderService) {
        this.orderService = orderService;
    }
}
```
Here, Spring **injects** the `OrderService` object automatically, following the **IoC** principle.

---

## **3. Dependency Management in Spring Boot (Using Gradle)**
### **What is Gradle?**  
Gradle is a **dependency management tool** used to handle project libraries efficiently. It provides:  
- Better performance than Maven.  
- A DSL-based approach (`build.gradle` file) for managing dependencies.  

### **build.gradle Configuration for Spring Boot**  
```gradle
plugins {
    id 'org.springframework.boot' version '3.0.0'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'   // Web dependencies
    testImplementation 'org.springframework.boot:spring-boot-starter-test'  // Testing dependencies
}

java {
    sourceCompatibility = '17'
}
```
- The **Spring Boot plugin** (`org.springframework.boot`) helps in configuring the project automatically.  
- **Spring Boot Starter Web** provides the necessary dependencies for creating RESTful services.  
- **Java 17** is specified as the compatible version.  

---

## **4. Beans in Spring Boot**
### **What are Beans?**  
- **Beans** are Java objects managed by the **Spring IoC container**.  
- They are created, wired, and managed by the **ApplicationContext**.  

### **Creating a Bean using `@Component` Annotation**
```java
@Component  // Marks this class as a Spring-managed bean
public class UserService {
    public String getUser() {
        return "User found!";
    }
}
```
### **Injecting a Bean using `@Autowired`**
```java
@RestController
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/user")
    public String getUser() {
        return userService.getUser();
    }
}
```
Here, `UserService` is **injected** into `UserController` using `@Autowired`.  

---

## **5. Spring Boot Application Setup**
### **Project Initialization Using Gradle**
To create a Spring Boot project using Gradle:  
```shell
gradle init
```
Then, select **Java application** and configure the **Gradle build file (`build.gradle`)**.

### **Spring Boot Application Class (Main Entry Point)**
Every Spring Boot application requires a **main class** that bootstraps the project:  
```java
@SpringBootApplication  // Marks this as a Spring Boot application
public class MySpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
    }
}
```
- `@SpringBootApplication` is a **combination of three annotations**:
  - `@Configuration` – Allows bean definitions.
  - `@EnableAutoConfiguration` – Enables automatic configuration.
  - `@ComponentScan` – Scans the package for components.  

---

## **6. Running a Spring Boot Application as a Server**
By default, Spring Boot applications do **not** run as servers. To enable it:
1. **Add Web Dependency in `build.gradle`**  
   ```gradle
   dependencies {
       implementation 'org.springframework.boot:spring-boot-starter-web'
   }
   ```
2. **Create a Simple REST Controller**
   ```java
   @RestController
   public class HelloController {
       @GetMapping("/hello")
       public String sayHello() {
           return "Hello, Spring Boot!";
       }
   }
   ```
3. **Run the Application**
   ```shell
   ./gradlew bootRun
   ```
   The application runs on `http://localhost:8080/hello`.  

---

## **7. Dependency Injection in Detail**
### **Why Use Dependency Injection?**  
Without DI, we need to manually create instances, leading to **tight coupling** and **higher memory usage**.  

### **Singleton Bean Scope (Saving Memory)**
- When `@Service` or `@Component` is used, the bean is created **only once** and shared across multiple classes.  
- This reduces memory usage compared to creating a new instance every time.

### **Example of Singleton Beans**
```java
@Service
public class OrderService {
    public void placeOrder() {
        System.out.println("Order placed!");
    }
}

@Service
public class UserService {
    private final OrderService orderService;

    @Autowired
    public UserService(OrderService orderService) {
        this.orderService = orderService;
    }
}
```
- Here, `OrderService` is created **once**, and the same instance is shared with `UserService`.  
- This is managed by the **ApplicationContext (IoC Container)**.  

---

## **8. ApplicationContext (Spring Container)**
- The **ApplicationContext** acts as the **IoC container** that manages all Spring beans.  
- It is responsible for:
  - Creating **singleton instances** of services.  
  - Injecting dependencies automatically.  
  - Managing bean lifecycle.  

### **Example: Retrieving Beans from ApplicationContext**
```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
OrderService orderService = context.getBean(OrderService.class);
orderService.placeOrder();
```
- Here, `context.getBean(OrderService.class)` retrieves the **already created instance** from the container.

---

## **9. Conclusion**  
This guide covers the fundamental concepts of **Spring Boot**, including:  
- What **Spring Boot** is.  
- How **Dependency Injection (DI)** works.  
- How to set up a **Spring Boot project using Gradle**.  
- Understanding **Beans, ApplicationContext, and IoC**.  
- Running a **Spring Boot application as a server**.  

These concepts form the foundation for building scalable and maintainable Spring Boot applications.

---

## **1. Basic Class and Object Creation in Java**

### **Key Concepts:**

* **Class**: A blueprint for creating objects.
* **Object**: Instance of a class.
* **Method**: Function defined inside a class.

### **Code Snippet:**

```java
package com.java.telescope;

public class Car {
    public void drive() {
        System.out.println("Children, we have rights. It's working!");
    }
}
```

```java
public class App {
    public static void main(String[] args) {
        Car car = new Car(); // Class name = Car, reference variable = car
        car.drive();
    }
}
```

### **Problem Identified**:

If in future, we want to replace `Car` with `Bike`, we’ll have to change all `new Car()` references to `new Bike()` manually. This causes **tight coupling**.

---

## **2. Extending to Multiple Classes**

We add another class `Bike` with a similar `drive` method.

```java
public class Bike {
    public void drive() {
        System.out.println("Bike is running!");
    }
}
```

In `main`, if we now want to use `Bike`, we change:

```java
Bike bike = new Bike();
bike.drive();
```

Now the main code is dependent on the specific class (`Car` or `Bike`). This becomes problematic if you want the logic to work **irrespective** of the type of vehicle.

---

## **3. Solution with Interface for Loose Coupling**

### **Why Interface?**

To avoid rewriting or changing code for each new vehicle, define a **common contract** using an **interface**.

### **Code:**

```java
public interface Vehicle {
    void drive(); // All methods in interface are public by default
}
```

Make both `Car` and `Bike` implement `Vehicle`:

```java
public class Car implements Vehicle {
    public void drive() {
        System.out.println("Children, we have rights. Car is running.");
    }
}

public class Bike implements Vehicle {
    public void drive() {
        System.out.println("Bike is running!");
    }
}
```

Now in `main`, we can write:

```java
Vehicle obj = new Car(); // or new Bike();
obj.drive();
```

### **Benefit**:

We can switch between `Car` and `Bike` without changing rest of the logic. But we still have one place where we hardcode the class. Can we remove even that?

---

## **4. Introduction to Spring Framework and Dependency Injection**

### **Key Concept:**

**Dependency Injection (DI)** is a design pattern where dependencies (like `Car`, `Bike`) are provided by an external system (like Spring), not manually created using `new`.

---

## **5. Using Spring Framework’s ApplicationContext**

Instead of hardcoding `new Car()` or `new Bike()`, use **Spring's IoC container** to inject the required object.

### **Key Interfaces in Spring**:

* **BeanFactory**: Lightweight container, suitable for simple apps.
* **ApplicationContext**: A more feature-rich container, preferred for enterprise applications.

### **Why ApplicationContext?**

It is a **superset of BeanFactory** and includes features like:

* Bean lifecycle management
* Event propagation
* Internationalization

---

## **6. Code with Spring ApplicationContext**

### **Java Code**:

```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Vehicle obj = (Vehicle) context.getBean("vehicle"); // 'vehicle' is bean id
        obj.drive();
    }
}
```

---

## **7. XML Configuration File (`spring.xml`)**

To tell Spring which class to inject for the `vehicle` interface:

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="vehicle" class="com.java.telescope.Car"/> 
    <!-- Change to Bike to switch implementation -->
</beans>
```

### **How It Works**:

* Spring reads the XML file.
* When `getBean("vehicle")` is called, it returns an instance of the class specified (`Car` or `Bike`) and casts it to `Vehicle`.
* No need to change the Java code when switching implementations.
---

Let's **build intuition** step-by-step to understand **how beans** and **dependency injection (DI)** solve the problem of **manually editing code**, and how switching between `Bike` and `Car` becomes seamless.

---

## 🚗 THE CORE PROBLEM

Let’s say you're writing this:

```java
Vehicle obj = new Car();
obj.drive();
```

Now, if tomorrow you want to use a `Bike` instead of a `Car`, you **have to open this Java file** and change it to:

```java
Vehicle obj = new Bike();
```

Imagine doing this in **dozens of files**, or **thousands of lines** in a large project. This creates:

* **Tight coupling** (your main code is dependent on `Car` or `Bike`)
* **Harder maintainability**
* **Violates Open/Closed Principle** (your code should be open for extension, closed for modification)

---

## ✅ SOLUTION WITH SPRING BEANS & DEPENDENCY INJECTION

### **What is a Bean?**

A **bean** is just a **Java object that Spring manages** for you.

Instead of saying:

```java
Vehicle obj = new Car();
```

You let Spring do:

```java
Vehicle obj = (Vehicle) context.getBean("vehicle");
```

So Spring now decides: "What does 'vehicle' mean? Should I give a `Car` or a `Bike`?"

---

## 🔁 HOW SWITCHING WORKS

### 1. **In your Java code**:

```java
ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
Vehicle obj = (Vehicle) context.getBean("vehicle");
obj.drive();
```

Here, `"vehicle"` is just an **ID** — a **key** to a bean definition in your `spring.xml` file.

### 2. **In your `spring.xml` config file**:

#### To use `Car`:

```xml
<bean id="vehicle" class="com.example.Car"/>
```

#### To use `Bike`:

```xml
<bean id="vehicle" class="com.example.Bike"/>
```

Now to switch from `Car` to `Bike`, you don’t touch Java code.
You just **edit one line in XML**.

This is **Inversion of Control (IoC)** — your class no longer decides *what* object it uses, Spring does.

---

## 💡 WHY IS THIS POWERFUL?

* You can switch implementations **without recompiling Java code**.
* The **client code (main app)** stays the same — only the configuration changes.
* You can easily inject dependencies based on:

  * **Environment (e.g., dev/test/prod)**
  * **Configuration profiles**
  * **Command-line arguments**

In fact, Spring supports even **runtime injection** or **annotation-based injection** — so you can write zero XML too.

---

## 🔁 Step-by-Step Flow Recap

1. Java code asks Spring: "Give me a `Vehicle` bean."
2. Spring looks into `spring.xml`, sees:

   ```xml
   <bean id="vehicle" class="com.example.Car"/>
   ```
3. Spring creates an object of `Car`, typecasts it to `Vehicle`, and gives it to your app.
4. Later, to use `Bike`, you only change the XML to:

   ```xml
   <bean id="vehicle" class="com.example.Bike"/>
   ```

That’s it. No Java code modification.

---

## 🔧 Optional: Making This Even Better

You can also use annotations like:

```java
@Component("vehicle")
public class Car implements Vehicle {
    public void drive() {
        System.out.println("Driving Car");
    }
}
```

And let Spring scan the classpath with:

```xml
<context:component-scan base-package="com.example"/>
```

Now you don’t even need to define `<bean>` manually in XML.

---

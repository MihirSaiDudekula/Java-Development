## **Advanced Notes on Java Streams, Functional Interfaces, and Mapping in Java**

---

### **1. Java Streams Overview**
Java *Streams* provide a functional approach to handling collections of data. Instead of using traditional loops and conditions, Java Streams allow data processing using a **pipeline-based approach**.

#### **Key Characteristics of Streams:**
- Streams do **not store data**; they operate on the original collection.
- They provide **lazy evaluation**, meaning elements are processed only when necessary.
- They support **method chaining**, allowing multiple operations in a single statement.
- Streams can be **sequential or parallel**, improving efficiency.

#### **Example: Creating a Stream from a List**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
Stream<Integer> numStream = numbers.stream(); // Creating a Stream
```

---

### **2. Filtering Data with Streams**
Streams provide the `filter()` method to remove unwanted elements based on a **predicate** (a function returning `true` or `false`).

#### **Example: Filtering Even Numbers**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

List<Integer> evenNumbers = numbers.stream()  
    .filter(num -> num % 2 == 0) // Only numbers divisible by 2
    .collect(Collectors.toList()); // Convert stream to list

System.out.println(evenNumbers); // Output: [2, 4, 6, 8, 10]
```

**Key Concept:**
- `.filter(num -> num % 2 == 0)`: Uses a **lambda expression** (`num -> num % 2 == 0`) to check divisibility.

---

### **3. Mapping Data in Streams**
The `map()` method transforms each element of a stream using a **function**.

#### **Example: Squaring Each Number in a List**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

List<Integer> squaredNumbers = numbers.stream()  
    .map(num -> num * num)  // Square each number
    .collect(Collectors.toList());

System.out.println(squaredNumbers); // Output: [1, 4, 9, 16, 25]
```

**Key Concept:**
- `.map(num -> num * num)`: Each element is transformed using a lambda function.

---

### **4. Parallel Streams**
Java allows parallel processing using `.parallelStream()` or `.parallel()`.

#### **Example: Using Parallel Streams**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

List<Integer> evenNumbers = numbers.parallelStream()  
    .filter(num -> num % 2 == 0) 
    .collect(Collectors.toList());

System.out.println(evenNumbers);
```
**Key Concept:**
- `parallelStream()`: **Splits workload across multiple CPU cores**, improving performance on large datasets.

---

### **5. Collecting Results from Streams**
After performing operations on a stream, results need to be collected. The `Collectors.toList()` method is commonly used.

#### **Example: Collecting Filtered Results**
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

List<String> filteredNames = names.stream()  
    .filter(name -> name.startsWith("A"))  // Keep only names starting with 'A'
    .collect(Collectors.toList());

System.out.println(filteredNames); // Output: [Alice]
```

---

### **6. Functional Interfaces in Java**
A **functional interface** is an interface with **only one abstract method**. These are used in **lambda expressions** and **method references**.

#### **Key Functional Interfaces in Java:**
| Interface | Method | Description |
|-----------|--------|-------------|
| `Predicate<T>` | `boolean test(T t)` | Takes an input and returns `true` or `false` |
| `Function<T, R>` | `R apply(T t)` | Takes an input and returns an output |
| `Consumer<T>` | `void accept(T t)` | Consumes an input but does not return anything |
| `Supplier<T>` | `T get()` | Produces an output with no input |

#### **Example: Using Predicate with Streams**
```java
Predicate<Integer> isEven = num -> num % 2 == 0;

List<Integer> evenNumbers = numbers.stream()
    .filter(isEven)
    .collect(Collectors.toList());

System.out.println(evenNumbers);
```

---

### **7. Understanding Lambda Expressions**
A **lambda expression** is an anonymous function used to implement functional interfaces.

#### **Syntax of Lambda Expressions**
```java
(parameter) -> expression
```

#### **Example: Using Lambda in Streams**
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

List<Integer> doubled = numbers.stream()
    .map(n -> n * 2) // Lambda function: Multiply each element by 2
    .collect(Collectors.toList());

System.out.println(doubled); // Output: [2, 4, 6, 8, 10]
```

---

### **8. Stream API with Object Mapping**
When working with objects, we often need to extract or transform fields.

#### **Example: Extracting Names from a List of User Objects**
```java
class User {
    String name;
    int age;
    
    User(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return name;
    }
}

List<User> users = Arrays.asList(
    new User("Alice", 25),
    new User("Bob", 30),
    new User("Charlie", 35)
);

List<String> names = users.stream()
    .map(User::getName)  // Extract names using method reference
    .collect(Collectors.toList());

System.out.println(names); // Output: [Alice, Bob, Charlie]
```

---

### **9. Custom Predicate in Java**
We can define custom predicates for filtering data.

#### **Example: Filtering Based on a Condition**
```java
Predicate<User> isAdult = user -> user.age >= 18;

List<User> adults = users.stream()
    .filter(isAdult)
    .collect(Collectors.toList());
```

---

### **10. Summary of Important Concepts**
- **Streams**: Used for efficient data processing.
- **filter()**: Removes elements based on a **predicate**.
- **map()**: Transforms elements.
- **collect()**: Converts stream results into collections.
- **Parallel Streams**: Speed up processing for large datasets.
- **Functional Interfaces**: Enable passing functions as parameters.
- **Lambda Expressions**: Provide a compact way to define functions.

This is a **powerful approach** to functional programming in Java, making code **concise**, **readable**, and **efficient**.
# **Advanced Notes on Ticket Booking Implementation – Mini Project (Backend Mastery)**

## **1. Introduction to Ticket Booking System**
This lecture focuses on implementing a backend system for a **ticket booking application** using Java and Gradle. The project is structured using **Low-Level Design (LLD)** concepts, covering entities, services, and key functionalities. The aim is to create a project that showcases backend development skills in Java.

---

## **2. Project Setup and Dependencies**
### **2.1 Tools and Technologies Used**
- **Java 8**: Chosen for its **Stream API**, **Optional**, and other modern features.
- **Gradle**: A **dependency manager** for Java projects (similar to NPM for Node.js).
- **IntelliJ IDEA**: The preferred IDE for Java development.
- **JUnit 4**: For unit testing.

### **2.2 Setting Up the Project**
#### **Creating the Project Structure**
Using Gradle to initialize the project:
```sh
mkdir IRCTC
cd IRCTC
gradle init
```
- Choose `Java` as the language.
- Select `Single Module` (no multi-module setup for now).
- Use `Groovy` as the DSL (Domain-Specific Language) for Gradle.
- Select `JUnit 4` for testing.

After initialization:
```sh
intellij IRCTC
```
This opens the project in IntelliJ IDEA.

#### **Gradle Dependencies Management**
Dependencies are added in `build.gradle`:
```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter'
    testImplementation 'junit:junit:4.13.2'
}
```
- Gradle automatically downloads and manages **JAR files**.
- This prevents the need for manual **Java Archive (JAR) file** imports.

---

## **3. Low-Level Design (LLD) – Entities**
### **3.1 Understanding Java Packages**
In Java, **packages** help organize code into logical structures. For this project, entities are stored in:
```
com.example.ticketbooking.entities
```

### **3.2 User Entity**
The `User` class stores user details and their booked tickets.
```java
package com.example.ticketbooking.entities;

import java.util.List;

public class User {
    private String userId;
    private String name;
    private String password; // Passwords should ideally be hashed
    private List<Ticket> ticketsBooked;

    public User(String userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = password;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public List<Ticket> getTicketsBooked() { return ticketsBooked; }
}
```
- **Encapsulation** is used (`private` fields with `getter` methods).
- **List<Ticket>** holds all booked tickets for a user.

### **3.3 Ticket Entity**
The `Ticket` class represents a train ticket.
```java
package com.example.ticketbooking.entities;

import java.util.Date;

public class Ticket {
    private String ticketId;
    private String userId;
    private String sourceStation;
    private String destinationStation;
    private Date travelDate;
    private String trainId;

    public Ticket(String ticketId, String userId, String sourceStation, String destinationStation, Date travelDate, String trainId) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.travelDate = travelDate;
        this.trainId = trainId;
    }

    public String getTicketId() { return ticketId; }
    public String getUserId() { return userId; }
    public String getSourceStation() { return sourceStation; }
    public String getDestinationStation() { return destinationStation; }
    public Date getTravelDate() { return travelDate; }
    public String getTrainId() { return trainId; }
}
```
- Each ticket has **unique identifiers** for the user, source, destination, and train.

### **3.4 Train Entity**
The `Train` class stores information about a train and its available seats.
```java
package com.example.ticketbooking.entities;

import java.util.List;
import java.util.Map;
import java.util.Date;

public class Train {
    private String trainId;
    private String trainNumber;
    private List<List<Integer>> seats;  // 2D List representing seat availability
    private Map<String, Date> stationTimes; // Key: Station Name, Value: Arrival Time
    private List<String> stations; // List of all stations the train passes through

    public Train(String trainId, String trainNumber, List<List<Integer>> seats, Map<String, Date> stationTimes, List<String> stations) {
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.seats = seats;
        this.stationTimes = stationTimes;
        this.stations = stations;
    }

    public String getTrainId() { return trainId; }
    public String getTrainNumber() { return trainNumber; }
    public List<List<Integer>> getSeats() { return seats; }
    public Map<String, Date> getStationTimes() { return stationTimes; }
    public List<String> getStations() { return stations; }
}
```
- Uses **2D List for seat management** (`0` for empty, `1` for booked).
- **Maps arrival times** for different stations.

---

## **4. Service Layer**
The service layer handles business logic, such as **user authentication**, **ticket booking**, and **train management**.

### **4.1 User Booking Service**
Handles **User Authentication and Ticket Booking**.
```java
package com.example.ticketbooking.services;

import com.example.ticketbooking.entities.User;

public class UserBookingService {
    private User loggedInUser;

    public UserBookingService(User user) {
        this.loggedInUser = user;
    }

    public void login(User user) {
        this.loggedInUser = user;
        System.out.println("User logged in: " + user.getName());
    }

    public void bookTicket() {
        if (loggedInUser == null) {
            System.out.println("User not logged in!");
            return;
        }
        System.out.println("Ticket booked for: " + loggedInUser.getName());
    }
}
```
- **Encapsulates user data** (`loggedInUser` is private).
- **Uses constructor dependency injection** for passing logged-in users.

### **4.2 Train Service**
Handles **train searches and availability checks**.
```java
package com.example.ticketbooking.services;

import com.example.ticketbooking.entities.Train;
import java.util.List;

public class TrainService {
    private List<Train> trains;

    public TrainService(List<Train> trains) {
        this.trains = trains;
    }

    public Train findTrain(String source, String destination) {
        for (Train train : trains) {
            if (train.getStations().contains(source) && train.getStations().contains(destination)) {
                return train;
            }
        }
        return null;
    }
}
```
- **Searches for a train** given source and destination.

---

## **5. Implementation Insights**
### **5.1 Encapsulation and Data Hiding**
- All entity fields are `private`, exposing only necessary information via **getters**.
- This prevents unauthorized access to **sensitive data** like passwords.

### **5.2 Constructor Dependency Injection**
- **UserBookingService** and **TrainService** require an object in their constructor.
- This promotes **loose coupling** and improves **testability**.

### **5.3 Data Persistence Considerations**
- **Currently, there is no database.**
- A future enhancement could involve integrating **MySQL/PostgreSQL** or an **in-memory database like H2**.

---

## **6. Next Steps**
- Implement **actual database storage** (JDBC, Hibernate).
- Introduce **Spring Boot for API development**.
- Implement **unit tests** using JUnit.

This project lays the foundation for a **backend-based ticket booking system**, following structured **LLD principles** and best coding practices.

# **Advanced Notes on Java File Handling and Object Mapping**

## **1. Introduction to Java File Handling and Object Mapping**
Java provides built-in capabilities to handle files and map structured data (like JSON) into Java objects. In this lecture, we explore:  
- **Reading files** and fetching stored users  
- **Mapping JSON data to Java objects** using the **Jackson ObjectMapper**  
- **Handling lists and nested data structures** in Java  
- **Using serialization and deserialization** for data transformation  

---
## **2. Understanding JSON Mapping in Java**
### **2.1. Why Do We Need JSON Mapping?**
When working with real-world applications, data is often stored in JSON format. Java does not natively understand JSON, so we use libraries like **Jackson** to convert JSON data into Java objects and vice versa.

For example, if we receive this JSON:
```json
{
    "userId": 1,
    "name": "John Doe",
    "email": "john@example.com"
}
```
We want to map it to a Java object:
```java
public class User {
    private int userId;
    private String name;
    private String email;
    
    // Getters and Setters
}
```
---
## **3. File Handling in Java**
### **3.1. Reading a File in Java**
In Java, we can read a file using the `File` class and `Scanner` or `BufferedReader`. However, since we need to read structured JSON data, we use **Jackson's ObjectMapper**.

### **3.2. Defining the File Path**
The user data is stored in a JSON file. We define the **file path** as:
```java
private static final String USERS_PATH = "../localDB/users.json";
```
- **`static`**: The variable belongs to the class, not to an instance.  
- **`final`**: The value cannot be changed after initialization.  

Using `static final` ensures that the path is constant throughout execution.

### **3.3. Using Java's `File` Class**
```java
File file = new File(USERS_PATH);
```
This object represents the file located at `USERS_PATH`.

---
## **4. Using Jackson's `ObjectMapper` for JSON Parsing**
### **4.1. What is `ObjectMapper`?**
The **`ObjectMapper`** class from Jackson is used for **serializing** (Java object → JSON) and **deserializing** (JSON → Java object).

### **4.2. Setting Up `ObjectMapper`**
To use Jackson:
1. **Add dependency in `build.gradle` (for Gradle projects):**
   ```gradle
   implementation 'com.fasterxml.jackson.core:jackson-databind:2.14.0'
   ```
2. **Initialize ObjectMapper in Java:**
   ```java
   private static final ObjectMapper objectMapper = new ObjectMapper();
   ```
---
## **5. Deserializing JSON to Java Objects**
### **5.1. Reading JSON from File**
To read the JSON file and convert it into Java objects:
```java
List<User> users = objectMapper.readValue(new File(USERS_PATH), new TypeReference<List<User>>() {});
```
- `readValue(File, TypeReference<List<User>>)`  
  - Reads the file contents as plain text.  
  - Maps the data to a list of `User` objects.  

### **5.2. Understanding `TypeReference<List<User>>`**
- Java **Generics** do not retain type information at runtime.  
- We use **`TypeReference<List<User>>() {}`** to inform Jackson that we expect a list of users.  
- Without this, Jackson would not be able to determine that the JSON should be mapped to a **List of Users**.

---
## **6. Handling Lists and Nested Objects in JSON**
### **6.1. Example JSON Structure for Train Tickets**
```json
{
    "tickets": [
        {
            "ticketId": "123",
            "userId": "1",
            "source": "Delhi",
            "destination": "Mumbai",
            "train": {
                "trainId": "T101",
                "trainNumber": "12345",
                "seats": [
                    ["A1", "A2", "A3"],
                    ["B1", "B2", "B3"]
                ]
            }
        }
    ]
}
```
### **6.2. Mapping Nested JSON to Java Objects**
```java
public class Ticket {
    private String ticketId;
    private String userId;
    private String source;
    private String destination;
    private Train train;
    
    // Getters and Setters
}

public class Train {
    private String trainId;
    private String trainNumber;
    private List<List<String>> seats; // List of lists to store seat rows

    // Getters and Setters
}
```
### **6.3. Deserializing a List of Tickets**
```java
List<Ticket> tickets = objectMapper.readValue(new File("tickets.json"), new TypeReference<List<Ticket>>() {});
```
Jackson automatically maps the **nested `train` object** and **list of lists (`seats`)** properly.

---
## **7. Handling Exceptions in Java File Handling**
### **7.1. `IOException` Handling**
What if the file doesn't exist or there's an error reading it?  
We must **handle exceptions** properly.
```java
try {
    List<User> users = objectMapper.readValue(new File(USERS_PATH), new TypeReference<List<User>>() {});
} catch (IOException e) {
    System.out.println("Error reading users.json: " + e.getMessage());
}
```
### **7.2. Why Declare `throws IOException`?**
If we do not handle the exception inside the method, we can **declare it at the method level**:
```java
public List<User> loadUsers() throws IOException {
    return objectMapper.readValue(new File(USERS_PATH), new TypeReference<List<User>>() {});
}
```
Now, any method that calls `loadUsers()` must **handle the exception**.

---
## **8. Working with Java Streams and Filtering Data**
### **8.1. Fetching a Specific User by Name**
Using **Java Streams**, we can filter users based on criteria:
```java
Optional<User> user = users.stream()
    .filter(u -> u.getName().equalsIgnoreCase("John Doe"))
    .findFirst();
```
- `.stream()` - Converts `List<User>` into a stream.  
- `.filter(...)` - Filters users where `getName()` matches `"John Doe"`.  
- `.findFirst()` - Returns the first match as `Optional<User>`.  

---
## **9. Accessing Private Fields in Java Objects**
### **9.1. Why Can't We Access Private Fields Directly?**
```java
System.out.println(user.name); // ERROR: name has private access
```
The `name` field is `private`, meaning we **must use getters**:
```java
System.out.println(user.getName()); // Correct way
```
If the class doesn't have **getter methods**, we can:
1. **Add getters in the class**  
2. **Use Java Reflection (advanced)**  

---
## **10. Key Takeaways**
- **Jackson's `ObjectMapper`** converts JSON to Java objects and vice versa.  
- **`TypeReference<T>`** helps map generic types like `List<User>`.  
- **File reading** requires handling exceptions (`IOException`).  
- **Java Streams** allow filtering and searching in lists efficiently.  
- **Encapsulation (private fields)** ensures data security; use **getter methods** to access them.  


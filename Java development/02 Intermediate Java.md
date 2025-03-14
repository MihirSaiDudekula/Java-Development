# Java Essentials: Advanced Topics on Arrays, Lists, Maps, and Optionals

## 1. **Overview**
In this lecture, we delve into intermediate to advanced Java topics, including arrays, lists, maps, optionals, and streams. The aim is to build a solid understanding of these concepts and how to effectively use them in Java.

## 2. **Arrays and Lists**

### 2.1 **Arrays**
- **Arrays** are basic data structures that store a fixed-size sequence of elements of the same type.
- **Syntax for creating an array**:
  ```java
  int[] array = new int[]{1, 2, 3};
  ```
- **Accessing elements**:
  ```java
  System.out.println(array[0]); // Output: 1
  ```

### 2.2 **Lists**
- **Lists** are part of Java's **Collection Framework** and are more flexible than arrays. They can grow and shrink dynamically.
- **ArrayList** is a common implementation of the **List** interface.

#### 2.2.1 **ArrayList vs List**
- **List Interface**:
  - Defines methods for list operations, such as `add()`, `remove()`, and `size()`.
  - Implementations include `ArrayList`, `LinkedList`, etc.
- **ArrayList Class**:
  - Implements the `List` interface.
  - Provides methods to manipulate the list such as `add()`, `remove()`, `get()`, and `size()`.

#### 2.2.2 **Generic Lists**
- Generics allow you to specify the type of elements in a list.
  ```java
  List<String> stringList = new ArrayList<>();
  stringList.add("Hello");
  ```
- **Syntax**:
  ```java
  List<E> list = new ArrayList<>();
  ```
  where `E` is a placeholder for the type of elements the list will hold.

## 3. **Maps**

### 3.1 **Overview**
- **Maps** store key-value pairs where each key is unique.
- Common implementations include `HashMap` and `TreeMap`.

### 3.2 **HashMap**
- A **HashMap** allows **null** values and one **null** key.
- **Syntax**:
  ```java
  Map<String, Integer> map = new HashMap<>();
  map.put("John", 100);
  map.put("Jane", 200);
  ```
- **Accessing Values**:
  ```java
  int score = map.get("John"); // Output: 100
  ```

### 3.3 **Iterating over Maps**
- Using **entrySet()** to iterate:
  ```java
  for (Map.Entry<String, Integer> entry : map.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
  }
  ```

## 4. **Optionals**

### 4.1 **Purpose**
- **Optionals** help to avoid `NullPointerException` by providing a container object which may or may not contain a value.
- **Syntax**:
  ```java
  Optional<String> optional = Optional.of("Hello");
  ```

### 4.2 **Common Methods**
- **`isPresent()`**: Checks if a value is present.
  ```java
  if (optional.isPresent()) {
      System.out.println(optional.get());
  }
  ```
- **`ifPresent()`**: Executes a lambda expression if a value is present.
  ```java
  optional.ifPresent(value -> System.out.println(value));
  ```
- **`orElse()`**: Provides a default value if no value is present.
  ```java
  String value = optional.orElse("Default");
  ```

## 5. **Streams**

### 5.1 **Overview**
- **Streams** provide a high-level abstraction for working with sequences of elements.
- **Stream API** allows parallel processing and complex data manipulation with less code.

### 5.2 **Common Operations**
- **Filtering**:
  ```java
  List<String> filteredList = list.stream()
                                  .filter(s -> s.startsWith("A"))
                                  .collect(Collectors.toList());
  ```
- **Mapping**:
  ```java
  List<Integer> lengths = list.stream()
                              .map(String::length)
                              .collect(Collectors.toList());
  ```
- **Reduction**:
  ```java
  int sum = list.stream()
                .mapToInt(String::length)
                .sum();
  ```

## 6. **Interfaces and Implementation**

### 6.1 **Concept of Interfaces**
- **Interfaces** define a contract for classes without providing implementation details.
- **Syntax**:
  ```java
  interface MyInterface {
      void myMethod();
  }
  ```

### 6.2 **Implementation of Interfaces**
- Classes implementing interfaces must provide concrete implementations of all interface methods.
  ```java
  class MyClass implements MyInterface {
      public void myMethod() {
          System.out.println("Method implementation.");
      }
  }
  ```

## 7. **Code Examples**

### 7.1 **List Example**
```java
import java.util.ArrayList;
import java.util.List;

public class ListExample {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        for (String item : list) {
            System.out.println(item);
        }
    }
}
```

### 7.2 **Map Example**
```java
import java.util.HashMap;
import java.util.Map;

public class MapExample {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("Alice", 90);
        map.put("Bob", 85);

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
```

### 7.3 **Optional Example**
```java
import java.util.Optional;

public class OptionalExample {
    public static void main(String[] args) {
        Optional<String> optional = Optional.of("Hello");

        optional.ifPresent(value -> System.out.println(value));
        String result = optional.orElse("Default");
        System.out.println(result);
    }
}
```

### 7.4 **Stream Example**
```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamExample {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("apple", "banana", "cherry");

        List<String> filteredList = list.stream()
                                        .filter(s -> s.startsWith("b"))
                                        .collect(Collectors.toList());
        filteredList.forEach(System.out::println);
    }
}
```

These notes cover key Java concepts such as lists, maps, optionals, and streams. Each topic is crucial for advanced Java programming, providing you with the tools to handle data effectively and robustly.
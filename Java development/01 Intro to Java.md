Here are detailed notes based on the lecture transcript titled "EP01: 🔥 Java Basics | Backend mastery":

---

### **Introduction to Java Programming**

**1. Getting Started with Java**
   - Ensure **Visual Studio Code** and **Java Development Kit (JDK)** are installed.
   - **JDK** includes all necessary libraries and the **Java Virtual Machine (JVM)**.

**2. Creating and Running a Basic Java Program**
   - **Create a Java file**: `Test.java`
   - **Basic Structure of Java Code**:
     ```java
     public class Test {
         public static void main(String[] args) {
             System.out.println("Hello World");
         }
     }
     ```
   - **Explanation**:
     - `public class Test`: Defines a public class named `Test`.
     - `public static void main(String[] args)`: Entry point of the Java program where execution starts.
     - `System.out.println("Hello World")`: Prints "Hello World" to the console.

**3. Java Fundamentals**
   - **Classes and Objects**:
     - **Class**: A blueprint for creating objects. For example, `Car` is a class.
     - **Object**: An instance of a class. For example, `car1` is an object of class `Car`.
   - **Example**:
     ```java
     public class Car {
         int tires;
         int seats;
         
         public void drive() {
             // Procedure to drive the car
         }
     }
     ```

**4. Memory Management in Java**
   - **Heap Memory**:
     - Stores all objects and class instances.
     - Accessible by all threads.
   - **Stack Memory**:
     - Used for method calls and local variables.
     - Operates on a Last In, First Out (LIFO) principle.
   - **Example**:
     - When a method is called, it is pushed onto the stack. Upon completion, it is popped off.

**5. Memory Allocation Details**
   - **Stack vs. Heap**:
     - **Stack**: Stores method frames and local variables (e.g., `int x = 2`).
     - **Heap**: Stores objects and class instances (e.g., `Car car1 = new Car();`).
   - **Garbage Collection**:
     - Automatically deallocates memory for objects no longer in use.
     - Ensures efficient memory use by cleaning up unused objects.

**6. Java Program Execution**
   - **Compilation**:
     - The Java compiler (`javac`) converts `.java` files into bytecode (`.class` files).
   - **Execution**:
     - The JVM runs the bytecode and executes the program.
   - **Example**:
     ```bash
     javac Test.java
     java Test
     ```

**7. Understanding the `main` Method**
   - **Static Method**:
     - The `main` method must be `static` to allow the JVM to call it without creating an instance of the class.
   - **Parameters**:
     - `String[] args` holds command-line arguments passed to the program.

**8. Handling Arguments in Java**
   - **Accessing Arguments**:
     - Command-line arguments are accessed using `args` array.
     - Example to print arguments:
       ```java
       public class Test {
           public static void main(String[] args) {
               for (int i = 0; i < args.length; i++) {
                   System.out.println("Argument " + i + ": " + args[i]);
               }
           }
       }
       ```

**9. Code Example and Execution**
   - **Full Example**:
     ```java
     public class Test {
         public static void main(String[] args) {
             System.out.println("Hello World");
             if (args.length > 0) {
                 System.out.println("First Argument: " + args[0]);
             }
         }
     }
     ```
   - **Compile and Run**:
     ```bash
     javac Test.java
     java Test arg1
     ```
     Output:
     ```
     Hello World
     First Argument: arg1
     ```

**10. Java Syntax and Conventions**
   - **Access Modifiers**:
     - `public`: Accessible from anywhere.
   - **Method Types**:
     - `void`: Method does not return a value.
     - `String`: Method returns a `String`.

**11. Stack and Heap Usage**
   - **Stack**:
     - Stores method calls and local variables.
   - **Heap**:
     - Stores objects and their associated data.
     - Managed automatically by Java's garbage collector.

---

These notes cover the key concepts introduced in the lecture, including basic Java syntax, memory management, and how to write and run simple Java programs. If you have any specific code or concepts you'd like more detail on, feel free to ask!
# Understanding Classes and Objects

**Definition and Relationship:**
A class is a template for an object, and an object is an instance of a class. A class establishes a new data type for creating objects.

class in similar to structs in C.
infact the syntax in java is

```java
class Student
{
	String name; 
	int age;
	float marks;
}
```

which is similar to that of C as

```c
typedef struct 
{ 
	int data; 
	char name[50];  
	float marks; 
	
} Student;
```

**Object Declaration:**
When you declare an object of a class, you instantiate that class, creating an instance with physical reality in memory.

in java
```java
Student s; // declares (not defines) a new student which can be refenced using s
```

#### remember
ex:
When you declare a reference variable `a` of type `Shape` like this:

```java
Shape a;
```

It means that `a` is a reference variable of type `Shape`, and it can refer to any object that is an instance of `Shape` or any of its subclasses. 

and also 

Since `Shape` is the declared type of `a`, you can ONLY DIRECTLY ACCESS methods and fields that are defined in the `Shape` class, NOT ANY OTHER class methods, not even subclasses like Circle ,etc.


**Properties of Objects:**
Objects possess three fundamental properties: *state*, *identity*, and *behavior*.

- *State:* Represents the value from the object's data type.
- *Identity:* Distinguishes one object from another, often related to its memory address.
- *Behavior:* Refers to the actions or effects of operations on the object's data.

**Object Operations:**
The dot operator connects the object's name with its instance variables, facilitating data manipulation.

**Dynamic Memory Allocation:**
The 'new' keyword dynamically allocates memory for objects, returning a reference that points to the allocated memory.

**Understanding References:**
In Java, object references must be dynamically allocated, ensuring safety and preventing arbitrary memory manipulation.

# A Closer Look at 'new'

**Instantiation Syntax:**
```java
classname class-var = new classname();

Student s = new Student();
```
- *class-var:* Variable of the class type being created.
- *classname:* Name of the class to instantiate.
- *Constructor:* Specifies actions upon object creation.


explanation:
assume the following c code

```c
typedef struct 
{ 
	int data; 
	char name[50];  
	float marks; 
	
} Student;

Student* s = (Student*)malloc(sizeof(Student));
s->data=75;

```

here `wkt`,
`s` is a pointer to a node (the student structure). `malloc` is responsible to allocate these memory items dynamically during run-time and `->` is used to access the struct variables

similarly
```java
class Student
{
	String name; 
	int age;
	float marks;
}

Student s = new Student();
s.age=17;

```

`s` is like a pointer, which we call here a `reference variable`, present in the stack memory, which refers to the object , (the instance of the struct present in the heap memory).

`new` keyword is similar to that of malloc as it allocates memory dynamically.

and the dot `.` oprerator has similar functionality to that of
`->` as we see, it is used to reference the object variables.that means we can read the variables value and manipulate its existing value.However, unlike C, you cannot directly manipulate memory addresses or perform pointer arithmetic with `Node` references. Java's memory management and safety features ensure that you can only access objects through their references, providing a level of protection against common programming errors and vulnerabilities.  


**Primitive Types vs. Objects:**
Java's primitive types are not implemented as objects for efficiency reasons, unlike classes.

**Runtime Memory Allocation:**
The 'new' operator allocates memory for objects during runtime, enabling dynamic memory management.

### dynamic memory allocation:

`Student s = new Student();`

`Student s ` - compile time 
`new Student ();`  - run time

during compile time, the compiler just compiles the code without allocating memory at that time,as at that time it is only concerned with checking for errors and proper compilation of the code(The compiler only ensures syntactic correctness and type safety during compilation). so  it kind of compiles the left hand side of the expression and remembers for later that memory has to be allocated dynamically during runtime.

during runtime, when the program is finally about to run, at that time memory is allocated and program runs. this is how new and malloc work.

# constructors 
are special methods that are automatically invoked when an instance of a class is created (i.e., when you use the `new` operator followed by class-name() ).

```java
Student s = new Student(); // Student() is the constructor
```

1. **Automatic Invocation**: When you write `new Student()`, the `Student()` constructor is automatically called. Constructors are typically used to initialize the object's state, such as setting initial values for member variables.

2. **Return Type**: Constructors do not have a return type, not even `void`. This is because the purpose of a constructor is to initialize an object, not to return any value. When you see a method with the same name as the class and no return type, it's a constructor.

Here's a breakdown of the line `Box mybox1 = new Box();`:

- `new Box()`: This part of the line creates a new instance of the `Box` class by invoking the `Box` constructor. The parentheses `()` indicate that no arguments are passed to the constructor.

- `Box mybox1 = ...`: This part of the line declares a variable `mybox1` of type `Box` and initializes it with the result of the `new Box()` expression, which is a reference to the newly created `Box` object.
## this keyword

**Reference to Current Object**: `this` is always a reference to the object on which the method was invoked. It allows methods to refer to the object's own members (fields or methods)

also, it is useful when you need to differentiate between instance variables and constructor parameters with the same name. It allows you to explicitly refer to the instance variables of the object.

Constructors are responsible for initializing the state of an object. By using `this`, you can set the values of instance variables directly. This helps clarify the code and makes it clear that you're initializing the object's own state.

```java
Student Rahul = new Student();
Student Kunal = new Student();
Kunal.greeting(); // Hello i'm default

class Student
{
	String name; 
	int age;
	float marks;
	void greeting()
	{
		System.out.println("Hello i'm "+this.name);
		//for both cases itl give Hello i'm default
		//as both names have been set to = 'default'
	}
	Student()
	{
		this.name='default';
		this.age=0;
		this.marks=0;
		// initialises all object variables of   student class to these given values. so kunal.name='default and rahul.name=default
	}

}
```

## Parameterized Constructor

constructor initializes the object based on the arguments provided to the constructor function.
```java
public class Main {
    public static void main(String[] args) {
        Student Rahul = new Student("Rahul",18,95);
        Student Kunal = new Student("Kunal",19,87);

        Kunal.greeting(); // Output: Hello, I'm Kunal
    }
}

class Student {
    String name;
    int age;
    float marks;

    // Parameterized constructor
    Student(String name,int age,float marks) {
        this.name = name;
        this.age = 0;
        this.marks = 0;
    }

    void greeting() {
        System.out.println("Hello, I'm " + this.name);
    }
}

```

## Constructor Overloading

when we use multiple constructor functions, which are overloaded such that, one is for default(non-parameterised condition) and the other has parameters (for parameterised).
it is called constructor overloading.

```java
	//non Parameterised constructor
	Student()
	{
		this.name='default';
		this.age=0;
		this.marks=0;
		// initialises all object variables of   student class to these given values. so kunal.name='default and rahul.name=default
	}
	// Parameterized constructor
    Student(String name,int age,float marks) {
        this.name = name;
        this.age = 0;
        this.marks = 0;
    }
```

# Object Reference Assignment

**Sharing Object References:**
Assigning one object reference to another doesn't create a copy but shares the reference to the same object.

**Impact of Reference Assignment:**
Changes made through one reference affect the object referenced by others, as they all point to the same object.

# Method Parameters and Arguments

**Parameter vs. Argument:**
A parameter is a method-defined variable receiving values upon method invocation, while an argument is the value passed to the method during invocation.

**Example:**
```java
int square(int i){
    return i * i;
}
```
Here, 'i' is a parameter, while '100' in 'square(100)' is an argument passed to the method.

# Note
Compiler checks the reference (lhs) while the JVM manages the object (rhs).

# constructor with object argument

In Java, a constructor can take an object as a parameter just like any other data type. This allows you to initialize an object using another object's properties or state. Let's illustrate this with an example:

Suppose you have a class called `Person` with various attributes such as name, age, and gender. You want to create a constructor for this class that takes another `Person` object as a parameter and initializes the new object with the same attributes as the provided `Person` object.

Here's how you can define such a constructor in Java:

```java
public class Person {
    private String name;
    private int age;
    private String gender;

    // Constructor taking another Person object as parameter
    public Person(Person otherPerson) {
        // Initialize the new object with the attributes of the other Person object
        this.name = otherPerson.getName();
        this.age = otherPerson.getAge();
        this.gender = otherPerson.getGender();
    }

    // Other constructors and methods of the Person class...
}
```

In this example:

- We define a constructor that takes a `Person` object `otherPerson` as a parameter.
- Inside the constructor, we use the getter methods (`getName()`, `getAge()`, and `getGender()`) of the `otherPerson` object to retrieve its attributes and initialize the corresponding attributes of the new `Person` object being created.

This allows you to create a new `Person` object by providing an existing `Person` object, making it easy to copy the state of one object to another during initialization.

# primitives in Java

In Java, primitive types such as `int`, `double`, `boolean`, etc., are not instantiated using the `new` keyword like objects. Instead, they are treated as basic data types directly supported by the Java language.

# wrapper classes 

Wrapper classes in Java provide a way to treat primitive data types as objects. They offer methods and utilities for working with these primitive types in an object-oriented context. Here's a summary:

- **Wrapper Classes for Primitive Types**: Each primitive data type has a corresponding wrapper class:
  - `Byte` for `byte`
  - `Short` for `short`
  - `Integer` for `int`
  - `Long` for `long`
  - `Float` for `float`
  - `Double` for `double`
  - `Character` for `char`
  - `Boolean` for `boolean`

- **Conversion Between Primitive Types and Wrapper Objects**:
  - **Boxing**: Converting a primitive type to its corresponding wrapper object.
  - **Unboxing**: Converting a wrapper object to its corresponding primitive type.

- **Auto-boxing and Auto-unboxing**: Java provides automatic conversion between primitive types and their corresponding wrapper classes.

- **Usage Examples**:
  - **ArrayList**: Storing primitive types in collections like `ArrayList`.
  - **Method Arguments**: Passing primitive types as arguments to methods expecting objects.
  - **Object-oriented Operations**: Performing object-oriented operations on primitive types.

**Example: `int` - `Integer` Interconversion and Usage**:
```java
// Primitive type
int num = 10;

// Converting to wrapper object (Boxing)
Integer obj = Integer.valueOf(num);

// Unboxing: Converting back to primitive type
int extractedNum = obj.intValue();

// Auto-boxing: Implicit conversion from primitive to object
ArrayList<Integer> numbers = new ArrayList<>();
numbers.add(num);

// Auto-unboxing: Implicit conversion from object to primitive
int extractedFromList = numbers.get(0);
```

When you add `num` to the `ArrayList`, which is declared to hold `Integer` objects, Java automatically converts `num` into an `Integer` object before adding it to the list. This is referred to as auto-boxing because the conversion is implicit and done automatically by the Java compiler.

similarly

Since the `ArrayList` contains `Integer` objects, Java automatically converts the `Integer` object back into an `int` primitive value when assigning it to the `int` variable `extractedFromList`. 

# final keyword

The `final` keyword in Java is used to define a constant value or to indicate that a variable, method, or class cannot be changed or overridden after it has been initialized or defined. Here's a detailed explanation along with examples:

##### Constant Fields:
When a field is declared as `final`, it means that its value cannot be modified once it's initialized. This effectively makes the field a constant. The `final` keyword ensures that the value assigned to the field remains constant throughout the program's execution.

We should always initialize while declaring final variables.

**Example:**
```java
public class Constants {
    // Declaring a final constant field
    public static final int MAX_SIZE = 100;
    public static final String APP_NAME = "MyApp";
}
```

In this example:
- `MAX_SIZE` and `APP_NAME` are declared as `final` fields.
- Once initialized, their values cannot be changed throughout the program.

##### Initialization Requirement:
When a field is declared as `final`, it must be initialized either when it's declared or within the constructor of the class. This ensures that the final field has a defined value before it's used.

**Example:**
```java
public class Example {
    // Declaring and initializing a final field
    public final int value;

    public Example(int value) {
        this.value = value; // Initializing the final field in the constructor
    }
}
```

In this example:
- The `value` field is declared as `final`.
- It's initialized within the constructor, ensuring that it has a value before any operations are performed on it.

### Naming Convention:
It's a common coding convention to use all uppercase identifiers for final fields to distinguish them from regular variables. This convention helps improve code readability and signals that the field is a constant.

**Example:**
```java
final int FILE_OPEN = 2;
```

### Immutability for Reference Types:
When a reference variable is declared as `final`, it means that the reference itself cannot be changed to point to another object. However, the state of the object it refers to can still be modified.

**Example:**
```java
public class ImmutableExample {
    // Declaring a final reference variable
    private final StringBuilder builder;

    public ImmutableExample() {
        builder = new StringBuilder(); // Initializing the final reference variable
    }

    public void append(String text) {
        builder.append(text); // Modifying the state of the object the reference points to
    }
}
```

In this example:
- The `builder` variable is declared as `final`.
- While the reference stored in `builder` cannot be changed, the state of the `StringBuilder` object it refers to can be modified.

# destructor 

is a special method or function that is automatically called when an object is destroyed or goes out of scope. It is used for performing cleanup tasks or releasing resources associated with the object before its memory is deallocated. 

### Functionality of Destructor:

1. **Cleanup**: The primary purpose of a destructor is to perform cleanup tasks or release resources associated with the object. This could involve closing file handles, releasing memory, disconnecting from a database, etc.

2. **Automatic Invocation**: Destructors are automatically invoked by the runtime system when an object is destroyed or goes out of scope. This ensures that cleanup tasks are performed reliably and automatically, regardless of how the object's lifetime ends.

### Example:

Let's consider a simple example in C++ to demonstrate the use of constructors and destructors:

```cpp
#include <iostream>

class Resource {
public:
    Resource() {
        std::cout << "Resource acquired\n";
    }

    ~Resource() {
        std::cout << "Resource released\n";
    }
};

int main() {
    Resource res; // Creating an object of Resource class

    // Some operations using the resource object

    return 0;
} // Destructor of Resource class called here automatically (implicitly like constructors) when 'res' object goes out of scope
```

In this example:
- We have a class `Resource` with a constructor and a destructor.
- When an object of the `Resource` class is created in the `main` function, the constructor is automatically called, acquiring the resource.
- After the `main` function finishes executing, or when the `res` object goes out of scope, the destructor is automatically called, releasing the resource.

Output:
```
Resource acquired
Resource released
```

This demonstrates the automatic invocation of the destructor when the object goes out of scope, performing cleanup tasks or releasing resources associated with the object.
##### in java

In Java, unlike in C++, explicit destructors are not provided, and Java relies on garbage collection for memory management. Garbage collection automatically deallocates memory for objects that are no longer referenced by the program. However, you can achieve similar cleanup functionality using methods like `finalize()`

#### The finalize( ) Method:

Sometimes an object will need to perform some action when it is destroyed

To handle such situations, Java provides a mechanism called finalization.

By using finalization,
you can define specific actions that will occur when an object is just about to be reclaimed by the garbage collector.
To add a finalizer to a class, you simply define the finalize( ) method.

The Java run time calls that method whenever it is about to recycle an object of that class. Right before an asset is freed, the Java run time calls the finalize( ) method on the object.

```java
protected void finalize( ) {
    // finalization code here
}
```

Here's a Java example demonstrating cleanup using the `finalize()` method:

### DONT WRITE JUST UNDERSTAND syntax

```java
public class Resource {
    public Resource() {
        System.out.println("Resource acquired");
    }

    public void doSomething() {
        System.out.println("Doing something with the resource");
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            // Perform cleanup actions here
            System.out.println("Resource released");
        } finally {
            super.finalize();
        }
    }

    public static void main(String[] args) {
        Resource resource = new Resource();

        // Use the resource
        resource.doSomething();

        // The resource object becomes eligible for garbage collection
        resource = null;

        // Force garbage collection (for demonstration purposes; not recommended in production)
        System.gc();
    }
}
```

In this example:
- We have a `Resource` class with a constructor for resource acquisition and a method `doSomething()` for using the resource.
- We override the `finalize()` method to perform cleanup actions before the object is garbage collected. The `finalize()` method is called by the garbage collector before reclaiming memory for the object.
- In the `main` method, we create a `Resource` object and use it. After using it, we set the reference to `null` to make it eligible for garbage collection.
- We manually invoke the garbage collector using `System.gc()` for demonstration purposes. Note that it's generally not recommended to manually invoke the garbage collector in production code.

When you run this code, you'll see output similar to the following:
```
Resource acquired
Doing something with the resource
Resource released
``` 

# Garbage collection 

is a mechanism in programming languages, particularly in languages like Java and C#, for automatically reclaiming memory occupied by objects that are no longer needed or referenced by the program. Garbage collection eliminates the need for manual memory management and helps prevent memory leaks and memory-related bugs.

##### what is considered as garbage:

-the objects in the heap which are not being referenced by any variable (earlier a variable was pointing to it but now it points elsewhere).ex:

```java
Student a = new Student();
Student b = new Student();
a=b;//refernrce variable a now points to b
//therefore the object it was pointing earlier has no reference variable pointing to it
//hence it qualifies as garbage
```

A garbage collector is a part of the runtime system that is responsible for performing garbage collection. It continuously monitors the heap (the area of memory used for dynamic memory allocation) to identify and reclaim memory occupied by objects that are no longer in use. The garbage collector uses various algorithms and strategies to determine which objects are eligible for collection and reclaim their memory.

### How Garbage Collection Works:

1. **Marking Phase**: The garbage collector traverses all reachable objects starting from a set of root objects (e.g., global variables, local variables, static variables) and marks them as reachable. Any objects not reachable from the root set are considered garbage.

2. **Sweeping Phase**: Once all reachable objects are marked, the garbage collector sweeps through the heap and reclaims memory occupied by unmarked (garbage) objects. This memory is then made available for future object allocations.

3. **Compacting (Optional)**: In some garbage collection algorithms, a compacting phase may be performed to defragment the heap by moving live objects together and freeing up contiguous memory.

### Garbage Collection Algorithms:

- **Mark-Sweep**: This algorithm traverses all reachable objects, marks them, and then sweeps through the heap to reclaim memory occupied by unmarked objects.
  
- **Copying**: This algorithm divides the heap into two spaces (from-space and to-space). Live objects are copied from the from-space to the to-space, and then the from-space is cleared.
  
- **Mark-Compact**: This algorithm combines marking and compacting phases. It marks reachable objects, moves them together to one end of the heap, and updates references accordingly.
  
- **Generational**: This algorithm divides the heap into generations (e.g., young generation, old generation) based on object age. Younger objects are collected more frequently, while older objects are collected less frequently.

### Advantages of Garbage Collection:

- **Automatic Memory Management**: Eliminates the need for manual memory management, reducing the risk of memory leaks and memory-related bugs.
  
- **Increased Productivity**: Developers can focus on writing application logic rather than managing memory, leading to increased productivity and faster development cycles.

- **Dynamic Memory Allocation**: Supports dynamic memory allocation, allowing objects to be created and destroyed as needed without worrying about memory management.

- **Memory Safety**: Helps ensure memory safety by preventing dangling pointers, use-after-free errors, and other memory-related vulnerabilities.

### Disadvantages of Garbage Collection:

- **Overhead**: Garbage collection introduces overhead in terms of CPU and memory usage, as the garbage collector needs to continuously monitor and reclaim memory.
  
- **Non-deterministic**: Garbage collection is non-deterministic, meaning the timing and frequency of garbage collection cycles are not predictable, which can lead to unpredictable performance.

- **Fragmentation**: Some garbage collection algorithms may lead to heap fragmentation, where memory becomes fragmented over time, reducing available contiguous memory.

# Singleton classes

Singleton pattern is a design pattern used to ensure that a class has only one instance and provides a global point of access to that instance. This is useful when exactly one object is needed to coordinate actions across the system.

Here's how you can implement a singleton class in Java:

```java
public class Singleton {
    // Private static variable to hold the single instance of the class
    private static Singleton instance;

    // Private constructor to prevent instantiation from outside the class
    private Singleton() {
    }

    // Public static method to get the single instance of the class
    public static Singleton getInstance() {
        // Lazy initialization: Create the instance only when it's needed
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    // Example method
    public void showMessage() {
        System.out.println("Hello, I am a singleton instance!");
    }
}
```

In this example:

- The `Singleton` class has a private static variable `instance` which holds the single instance of the class.
- The constructor is private to prevent instantiation of the class from outside.
- The public static method `getInstance()` provides a way to access the single instance of the class. It uses lazy initialization to create the instance only when it's needed.
- The class can contain other methods and attributes as needed.

You can use the `getInstance()` method to access the singleton instance and call its methods:

```java
public class Main {
    public static void main(String[] args) {
        // Get the singleton instance
        Singleton s = Singleton.getInstance();

        // Call a method on the singleton instance
        s.showMessage();
    }
}
```

Output:
```
Hello, I am a singleton instance!
```

Singleton pattern ensures that there is only one instance of the class throughout the application, which can be useful in scenarios like managing configuration settings, database connections, thread pools, and more. However, it's important to note that singleton classes can introduce global state and tight coupling, so use them judiciously.

# Packages in Java

In Java, packages are used to organize classes and interfaces into namespaces, providing a way to group related classes and avoid naming conflicts. Packages help in modularizing and organizing large codebases, making it easier to manage and maintain Java applications. They also facilitate access control by controlling the visibility of classes and interfaces within and outside the package.

Here's an explanation with an example:

```java
// File: com/example/MyClass.java

package com.example;

public class MyClass {
    public void printMessage() {
        System.out.println("Hello from MyClass");
    }
}
```

In this example:
- We declare a class named `MyClass`.
- We place this class in the `com.example` package using the `package` statement at the beginning of the file. This statement specifies that the `MyClass` class belongs to the `com.example` package.
- The package name is a hierarchical naming structure, where `com` is the top-level package, and `example` is a subpackage.
- The directory structure reflects the package hierarchy. The file `MyClass.java` is located in a directory named `com/example`.
- Inside the class, we have a method `printMessage()` that prints a message.

Now, let's create another class in the same package:

```java
// File: com/example/AnotherClass.java

package com.example;

public class AnotherClass {
    public static void main(String[] args) {
        MyClass obj = new MyClass();
        obj.printMessage();
    }
}
```

In this class:
- We create a class named `AnotherClass` in the same `com.example` package.
- Inside the `main` method, we create an instance of `MyClass` and call its `printMessage()` method.

When you compile and run the `AnotherClass` program, it will successfully compile and execute `MyClass.printMessage()` method, demonstrating how classes in the same package can access each other.

Packages provide several benefits:
1. **Organization**: Packages help in organizing classes and interfaces into meaningful groups, making it easier to locate and manage related code.
2. **Access Control**: Packages allow you to control the visibility of classes and interfaces. Classes marked with default access are accessible only within the same package.
3. **Name Clashes Prevention**: Packages prevent naming conflicts by providing namespaces. Classes in different packages can have the same name without conflicting with each other.
4. **Encapsulation**: Packages promote encapsulation by allowing you to hide certain implementation details within a package, exposing only the necessary interfaces to other packages.

In Java, packages serve as containers for organizing and managing classes and interfaces. They provide a way to compartmentalize the class namespace, preventing naming conflicts and allowing developers to create classes with common names without worrying about collisions. Packages are stored in a hierarchical manner and are used both as a naming convention and a visibility control mechanism.

also something interesting that can be done in packages:

In Java, you can define all the methods within a class and then create another class, typically named `Main`, where you include the `public static void main(String[] args)` method. This separation of concerns is a common practice and follows the principle of modular programming, where each class has a specific responsibility or functionality. Here's how you can do it:

1. **Defining Methods in a Class**:
   
   Let's say you have a class named `MyClass` with various methods:

    ```java
    public class MyClass {
        // Method to calculate the sum of two numbers
        public int add(int a, int b) {
            return a + b;
        }

        // Method to calculate the product of two numbers
        public int multiply(int a, int b) {
            return a * b;
        }

        // Other methods can be defined here...
    }
    ```

2. **Creating the Main Class**:
   
   Now, you create another class, typically named `Main`, where you include the `main` method. This class will serve as the entry point of your Java application:

    ```java
    // Import MyClass if it's in a different package import packageName.MyClass;
//no need to in the same package
    public class Main {
        public static void main(String[] args) {
            // Create an instance of MyClass
            MyClass myObject = new MyClass();

            // Call methods from MyClass
            int sum = myObject.add(10, 20);
            System.out.println("Sum: " + sum);

            int product = myObject.multiply(5, 4);
            System.out.println("Product: " + product);

            // Other operations...
        }
    }
    ```

In this `Main` class, within the `main` method, you can create an instance of `MyClass` using the `new` keyword and call its methods as needed. This allows you to keep your classes organized and separate concerns, making your code more maintainable and easier to understand.
# Access specifiers

Access specifiers in Java are keywords used to define the accessibility or visibility of classes, methods, and variables within a Java program. There are four access specifiers in Java:

1. **private**: The `private` access specifier restricts access to members (fields and methods) only within the same class. They are not accessible from outside the class, including subclasses. 

2. **default (no specifier)**: If no access specifier is specified, then it is considered to have default access. Members with default access are accessible only within the same package. They are not visible to classes in different packages, even if they are subclasses.

3. **protected**: Members declared with the `protected` access specifier are accessible within the same package and also by subclasses, regardless of whether they are in the same package or different packages.

4. **public**: The `public` access specifier allows members to be accessible from any other class in the same project, including subclasses, and from any other package.


Java’s access modifiers are public, private, and protected. Java also defines a default access level.
protected applies only when inheritance is involved.

When no access modifier is used, then by default the member of a class is public within its own package,
but cannot be accessed outside of its package.

accessiblity table:

|             | Class   | Package   | Subclass (same pkg)    | Subclass (diff pkg)    | Subclass (diff pkg & not subclass)    |
| ----------- | ------- | --------- | ---------------------- | ---------------------- | ------------------------------------- |
| public      | +       | +         | +                      | +                      | +                                     |
| protected   | +       | +         | +                      | +                      |                                       |
| no modifier | +       | +         | +                      |                        |                                       |
| private     | +       |           |                        |                        |                                       |

### Usage of private

with this example, we can illustrate that

- public fields and methods can be accessed from anywhere, from any class, it need not be a subclass and need not even be in the same package to be accessed. this is why it becomes a huge security concern with public access specifier.
- if there is no access specifier specified, it can be accessed from any class/subclass of the given class, as long as they are in the same package 
- private access specifier strictly exclusive for the class the field and method are defined in and cannot even be accessed in subclass
```java
// File: MyClass.java
package com.example;

public class MyClass {
    private int privateField;
    int defaultField;
    protected int protectedField;
    public int publicField;

    private void privateMethod() {
        System.out.println("Private method");
    }

    void defaultMethod() {
        System.out.println("Default method");
    }

    protected void protectedMethod() {
        System.out.println("Protected method");
    }

    public void publicMethod() {
        System.out.println("Public method");
    }
}
```

```java
// File: AnotherClass.java
package com.example;

public class AnotherClass {
    public static void main(String[] args) {
        MyClass obj = new MyClass();

        // Accessing fields and methods from the same class
        obj.defaultField = 10;
        obj.defaultMethod();

        // Accessing fields and methods from a subclass
        obj.protectedField = 20;
        obj.protectedMethod();

        // Accessing public fields and methods from any class
        obj.publicField = 30;
        obj.publicMethod();

        // Cannot access private fields and methods from outside the class
        // obj.privateField = 40;  // Error: privateField has private access in MyClass
        // obj.privateMethod();     // Error: privateMethod() has private access in MyClass
    }
}
```

but 

### protected access

In Java, the `protected` access specifier allows members (fields, methods, constructors) of a class to be accessible within the same package and by subclasses, regardless of whether these subclasses are in the same package or a different one. Let's illustrate this with an example:

```java
// Class C in packageA
package packageA;

public class C {
    protected int member;

    public C(int member) {
        this.member = member;
    }
}

// Class S in packageB (a different package)
package packageB;

import packageA.C;

public class S extends C {
    public S(int member) {
        super(member);
    }

    public void displayMember() {
        System.out.println("Member value from subclass S: " + member);
    }
}
```

In this example:

- `C` class has a `protected` member `member`.
- `S` class, which is a subclass of `C`, extends `C` and can access the `protected` member `member` due to its inheritance relationship.
- Even though `S` is in a different package (`packageB`), it can still access the `protected` member `member` inherited from `C`.
- The constructor of `S` uses `super(member)` to call the constructor of `C` and initialize the `protected` member.
- The `displayMember()` method in class `S` demonstrates accessing the `protected` member `member` from the subclass `S`.

This example shows how the `protected` access specifier allows subclasses, even if they are in different packages, to access the `protected` members of their superclass.

# Getters and Setters

In Java, getter and setter methods are used to access and modify the private fields of a class. They provide a way to encapsulate the access to these fields, enabling better control over how they are accessed and modified by other parts of the program. Here's a breakdown of getter and setter methods:

1. **Getter Method**: 
   - A getter method is a public method in a class used to retrieve the value of a private field.
   - It typically follows the naming convention `getPropertyName()` or `isPropertyName()` for boolean properties.
   - Getter methods do not modify the state of the object; they simply return the value of the field.
   - Here's an example of a getter method for a private field `name`:

    ```java
    public class MyClass {
        private String name;

        // Getter method for 'name'
        public String getName() {
            return name;
        }
    }
    ```

2. **Setter Method**:
   - A setter method is a public method in a class used to modify the value of a private field.
   - It typically follows the naming convention `setPropertyName()`.
   - Setter methods can perform validation or other operations before assigning the value to the field.
   - Here's an example of a setter method for the same private field `name`:

    ```java
    public class MyClass {
        private String name;

        // Setter method for 'name'
        public void setName(String newName) {
            this.name = newName;
        }
    }
    ```

Using getter and setter methods helps in achieving encapsulation, which is one of the pillars of object-oriented programming. 
Encapsulation hides the internal implementation details of a class from the outside world and only exposes the necessary functionalities through well-defined interfaces (i.e., methods). 

# Static

static members - which are static variables or static methods 
are:
- Static members are members, which belong to the class as a whole rather than to individual instances of the class. They are shared among all instances of the class.
- Static variables are initialized only once when the class is loaded into memory, and their values are shared across all instances of the class.
- Static methods can be called directly using the class name, without the need to create an instance of the class.
	- so we use `classname.varname` instead of `this.varname`, as `this.varname` doesnt make sense, as its not something that is specific to `this` object but it is common to the whole class. ex: there may be a class of students each with different non-static variables like name, age, height,etc. but nationality= Indian is something that is common to all students (hypothetically).
- Non-static methods can access static members, but static methods cannot access non-static members directly (unless through an object reference).

```java
public class MyClass {
    // Static variable
    public static int staticVariable = 10;

    // Static method
    public static void staticMethod() {
        System.out.println("Inside staticMethod()");
    }

    // Non-static method
    public void nonStaticMethod() {
        System.out.println("Inside nonStaticMethod()");
    }

    public static void main(String[] args) {
        // Accessing static variable and method directly using class name
        System.out.println("Static variable: " + MyClass.staticVariable);
        MyClass.staticMethod();

        // Creating an instance of MyClass
        MyClass obj = new MyClass();

        // Accessing static variable and method using object reference (not recommended)
        System.out.println("Static variable using object: " + obj.staticVariable);
        obj.staticMethod();

        // Accessing non-static method using object reference
        obj.nonStaticMethod();
    }
}
```

When you run this program, you'll see the following output:
```
Static variable: 10
Inside staticMethod()
Static variable using object: 10
Inside staticMethod()
Inside nonStaticMethod()
```


A static method can access only static data. It cannot access non-static data (instance variables)
A non-static member belongs to an instance. It's meaningless without somehow resolving which instance of a class you
are talking about. In a static context, you don't have an instance, that's why you can't access a non-static member
without explicitly mentioning an object reference.
In fact, you can access a non-static member in a static context by specifying the object reference explicitly :

```java
public class Human {

    String message = "Hello World";
    //non static variable 

    public static void display(Human human){
	    //static method accesses non-static method after explicitly mentioning the object that it references
        System.out.println(human.message);
    }

    public static void main(String[] args) {
        Human kunal = new Human();
        kunal.message = "Kunal's message";
        Human.display(kunal);
    }

}
```

- A static method can call only other static methods and cannot call a non-static method from it.
- A static method can be accessed directly by the class name and doesn’t need any object
- A static method cannot refer to "this" or "super" keywords in anyway

If you want to do a calculation and then initialize your static variables,
you can declare a static block that gets executed exactly once, when the class is first loaded.

```java
// Demonstrate static variables, methods, and blocks.
     class UseStatic {
       static int a = 3;
       static int b;
       static void disp(int x) {
         System.out.println("x = " + x);
         System.out.println("a = " + a);
         System.out.println("b = " + b);
       }
       static {
         System.out.println("Static block initialized.");
         b = a * 4;
        }
       public static void main(String args[]) {
         disp(42);
    }
}
```

As soon as the UseStatic class is loaded, all of the static statements are run. First, a is set to 3,
then the static block executes, which prints a message and then initializes b to a*4 or 12. Then main( ) is called,
which calls disp( ), passing 42 to x. The three println( ) statements refer to the two static variables a and b,
as well as to the local variable x.

# Inheritance

Inheritance is a fundamental concept in Object-Oriented Programming (OOP) where a class (subclass or derived class) can inherit attributes and methods from another class (superclass or base class). In Java, inheritance is achieved using the `extends` keyword.

Here's a brief explanation of inheritance in Java:

1. **Superclass and Subclass:** In Java, you define a superclass (base class) with its attributes and methods. Then, you can create a subclass (derived class) that extends the superclass. The subclass inherits all the non-private attributes and methods of the superclass.

2. **Code Reusability:** Inheritance promotes code reuse. Instead of rewriting code for similar classes, you can create a hierarchy of classes where subclasses inherit common characteristics from a superclass. This helps in reducing redundancy and makes the code more maintainable.

3. **Extending Functionality:** Subclasses can add their own unique attributes and methods in addition to what they inherit from the superclass. This allows for extending the functionality of existing classes without modifying them directly.

4. **Single Inheritance:** In Java, each class can have only one direct superclass. This means that a subclass can inherit from only one superclass. Java does not support multiple inheritance, where a class can inherit from multiple superclasses.

5. **Access Control:** Subclasses can access and override superclass methods and attributes, except for those that are declared as private. Private members of a superclass are not accessible to the subclass.


Certainly! Let's create a simple example to demonstrate inheritance in Java.

Suppose we have a superclass called `Animal`:

```java
// Superclass
class Animal {
    public void eat() {
        System.out.println("Animal is eating");
    }

    public void sleep() {
        System.out.println("Animal is sleeping");
    }
}
```

Now, let's create a subclass called `Dog` that inherits from the `Animal` class:

```java
// Subclass
class Dog extends Animal {
    public void bark() {
        System.out.println("Dog is barking");
    }
}
```

In this example:

- The `Animal` class is the superclass, which defines common behaviors of animals such as eating and sleeping.
- The `Dog` class is the subclass, which extends the `Animal` class using the `extends` keyword.
- The `Dog` class inherits the `eat()` and `sleep()` methods from the `Animal` class.
- Additionally, the `Dog` class has its own method `bark()`.

Now, let's create a test class to see how inheritance works:

```java
// Test class
public class Main {
    public static void main(String[] args) {
        Dog myDog = new Dog();
        myDog.eat();    // inherited from Animal
        myDog.sleep();  // inherited from Animal
        myDog.bark();   // own method
    }
}
```

Output:
```
Animal is eating
Animal is sleeping
Dog is barking
```

As you can see, the `Dog` class inherits the `eat()` and `sleep()` methods from the `Animal` class, and it also has its own method `bark()`. This demonstrates how inheritance allows us to reuse code and create a hierarchy of classes with shared behaviors.

Sure, let's break it down step by step.

### Need for Initializing Variables in Parent Class:

In Java, when a subclass inherits from a superclass, it inherits all the fields (variables) and methods of the superclass. However, there are certain considerations regarding how these fields are accessed and initialized.

1. **Access Control:** If the fields in the superclass are declared as `private`, they are not directly accessible in the subclass. They can only be accessed through methods provided by the superclass (such as getter and setter methods).

2. **Initialization:** Even if the fields are accessible, they might not be initialized properly if they are private or if there's custom initialization logic in the superclass constructor. Therefore, it's important to ensure that the fields in the superclass are initialized correctly before accessing them in the subclass.

### Constructors in Parent and Child Classes:

A constructor is a special method that is automatically called when an object of a class is created. Constructors are used to initialize the state of an object. In Java, if a class does not explicitly define a constructor, a default constructor (with no arguments) is provided automatically.

When a subclass is created, it automatically calls the constructor of its superclass. If the superclass constructor requires arguments, the subclass constructor must pass those arguments using the `super()` keyword.

### Example:

Let's create a simple example to illustrate these concepts:

```java
// Parent class
class Parent {
    private int x;

    public Parent(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }
}

// Child class
class Child extends Parent {
    private int y;

    public Child(int x, int y) {
        super(x); // calling superclass constructor
        this.y = y;
    }

    public void display() {
        System.out.println("x: " + getX());
        System.out.println("y: " + y);
    }
}

// Test class
public class Main {
    public static void main(String[] args) {
        Child child = new Child(10, 20);
        child.display();
    }
}
```

In this example:

- The `Parent` class has a private field `x` and a constructor that initializes `x`.
- The `Child` class extends `Parent` and has its own private field `y`. Its constructor takes two arguments: `x` (which is passed to the superclass constructor using `super()`) and `y`.
- The `display()` method in the `Child` class prints the values of `x` and `y`.
- In the `Main` class, we create an object of `Child` and call the `display()` method.

Output:
```
x: 10
y: 20
```

This example demonstrates how fields are initialized in the superclass constructor and accessed in both the superclass and subclass. The `super()` keyword is used to call the superclass constructor from the subclass constructor, ensuring proper initialization of inherited fields.

The `super` keyword in Java is a reference variable that is used to refer to the immediate parent class object. It can be used in two general forms: to call the superclass constructor and to access a member of the superclass that has been hidden by a member of the subclass.

Let's break down each use case with examples.

### 1. Calling the superclass constructor:

[](https://github.com/MihirSaiDudekula/Notes/blob/master/OOPS%202.md#1-calling-the-superclass-constructor)

When a subclass constructor is invoked, the first statement should be a call to the constructor of its superclass. This is done using the `super()` keyword. Here's an example:

```java
class Box {
    private double width;
    private double height;
    private double depth;

    // Constructor
    Box(double w, double h, double d) {
        width = w;
        height = h;
        depth = d;
    }
}

class BoxWeight extends Box {
    double weight;

    // Constructor
    BoxWeight(double w, double h, double d, double m) {
        super(w, h, d); // Call superclass constructor
        weight = m;
    }
}
```

In the `BoxWeight` class, the constructor `BoxWeight()` calls `super(w, h, d)`, passing the width, height, and depth parameters to the constructor of the `Box` superclass. This allows the `Box` constructor to initialize these values using the provided arguments. By calling `super()`, the subclass `BoxWeight` does not need to re-implement the initialization of these common attributes, reducing code duplication.

### 2. Accessing a member of the superclass:

[](https://github.com/MihirSaiDudekula/Notes/blob/master/OOPS%202.md#2-accessing-a-member-of-the-superclass)

Sometimes, a member of the superclass might be hidden by a member of the subclass with the same name. In such cases, you can use `super` to explicitly refer to the member of the superclass. Here's an example:

```java
class Box {
    double width;
    double height;
    double depth;

    // Constructor
    Box(double w, double h, double d) {
        width = w;
        height = h;
        depth = d;
    }
}

class BoxWeight extends Box {
    double weight;

    // Constructor
    BoxWeight(double w, double h, double d, double m) {
        super(w, h, d); // Call superclass constructor
        weight = m;
    }

    // Method to display dimensions
    void display() {
        System.out.println("Dimensions: " + super.width + "x" + super.height + "x" + super.depth);
    }
}
```

In the `display()` method of `BoxWeight`, `super.width`, `super.height`, and `super.depth` are used to explicitly access the width, height, and depth attributes of the superclass `Box`.




### Conclusion:
[](https://github.com/MihirSaiDudekula/Notes/blob/master/OOPS%202.md#conclusion)

The `super` keyword provides a way for subclasses to interact with their superclass, either by accessing hidden superclass members or by explicitly invoking superclass constructors. This enhances code readability and facilitates better understanding of class hierarchies in Java.

In Java, constructors play a crucial role in initializing objects. When it comes to inheritance, constructors behave in a specific manner, ensuring that objects are properly initialized, both in the superclass and the subclass. Let's delve into how constructors work in inheritance in Java.

### Default Constructor and Inheritance:

[](https://github.com/MihirSaiDudekula/Notes/blob/master/OOPS%202.md#default-constructor-and-inheritance)

1. **Base Class Constructor with No Arguments:**
    
    - If a base class does not explicitly define any constructor, Java provides a default constructor automatically.
    - This default constructor is parameterless and initializes the object with default values or initializes any member variables as needed.
2. **Calling Base Class Constructor in Derived Class:**
    
    - When a derived class is created, its constructor implicitly calls the constructor of its superclass (base class).
    - If the derived class constructor does not explicitly call a superclass constructor, Java automatically inserts a call to the superclass's default constructor as the first statement in the derived class constructor.
    - This ensures that the superclass's constructor is always called before the derived class's constructor.

### Example Analysis:

[](https://github.com/MihirSaiDudekula/Notes/blob/master/OOPS%202.md#example-analysis)

Let's analyze the provided example program:

```java
class Base {
  Base() {
    System.out.println("Base Class Constructor Called ");
  }
}

class Derived extends Base {
  Derived() {
    System.out.println("Derived Class Constructor Called ");
  }
}

public class Main {
  public static void main(String[] args) {
    Derived d = new Derived();
  }
}
```

1. **Base Class Constructor Call:**
    
    - The `Derived` class constructor is called in the `main` method.
    - Since the `Derived` class constructor does not explicitly call a superclass constructor, Java inserts a call to the default constructor of the superclass (`Base`) automatically.
    - Hence, the output includes "Base Class Constructor Called" before "Derived Class Constructor Called".
2. **Inherited Default Constructors:**
    
    - If the `Derived` class had its constructor and did not explicitly call `super()`, the default constructor of `Base` would still be invoked.
3. **Parameterized Constructors:**
    
    - If a superclass has a parameterized constructor and no default constructor, and the subclass does not call any superclass constructor, it results in a compilation error. The subclass must explicitly call the superclass constructor using `super()`.

### Conclusion:

[](https://github.com/MihirSaiDudekula/Notes/blob/master/OOPS%202.md#conclusion-1)

Inheritance in Java ensures that constructors are appropriately invoked in both the superclass and the subclass. If a superclass does not define any constructor, Java provides a default constructor. When a subclass is created, it automatically calls the constructor of its superclass, either the default constructor or an explicitly specified one using `super()`. This mechanism ensures proper object initialization and maintains the integrity of the inheritance hierarchy.

Inheritance in object-oriented programming refers to the mechanism by which one class (subclass or derived class) can inherit properties and behaviors from another class (superclass or base class). There are several types of inheritance:

1. **Single Inheritance**: In single inheritance, a class inherits from only one superclass. This is the most common type of inheritance and is supported in most programming languages, including Java.

2. **Multiple Inheritance**: In multiple inheritance, a class can inherit from more than one superclass. This means that the subclass has multiple parent classes. While this can be powerful, it can also lead to complications such as the diamond problem. Java does not support multiple inheritance for classes to avoid such complications, although it does support multiple inheritance for interfaces through interface inheritance.

3. **Multilevel Inheritance**: In multilevel inheritance, a class is derived from another class, and that class is further derived from another class. This forms a chain of inheritance, where each class inherits properties and behaviors from its immediate superclass. Java supports multilevel inheritance.

4. **Hierarchical Inheritance**: In hierarchical inheritance, multiple subclasses inherit from a single superclass. This creates a hierarchical structure of classes. Each subclass inherits properties and behaviors from the same superclass. Java supports hierarchical inheritance.

5. **Hybrid (Virtual) Inheritance**: Hybrid inheritance is a combination of multiple and single inheritance. It involves a mix of different types of inheritance within a class hierarchy. Java does not directly support hybrid inheritance.

In summary, Java supports single, multilevel, and hierarchical inheritance, while it does not support multiple inheritance for classes and hybrid inheritance.

# Overloading 
methods in Java allows you to define multiple methods with the same name within the same class, as long as their parameter lists are different. This provides flexibility and clarity in method naming while allowing for variations in behavior based on the input parameters. Let's delve into the concept with an example:

```java
public class Calculator {
    
    // Method to add two integers
    public int add(int a, int b) {
        return a + b;
    }
    
    // Method to add three integers
    public int add(int a, int b, int c) {
        return a + b + c;
    }   
   
    // Method to concatenate two strings
    public String add(String a, String b) {
        return a + b;
    }

}
```

In this `Calculator` class, we have several overloaded methods:

1. **`add(int a, int b)`**: This method takes two integer parameters and returns their sum.
2. **`add(int a, int b, int c)`**: This method takes three integer parameters and returns their sum.
4. **`add(String a, String b)`**: This method takes two string parameters and returns their concatenation.


In each case, the methods have the same name (`add`), but they differ in their parameter lists. Java distinguishes between these methods based on the number and type of parameters, allowing you to call the appropriate version of the method based on the arguments provided.

For example:

```java
public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        
        // Call the first add method with two integers
        int sum1 = calculator.add(5, 10); // Calls add(int a, int b)
        
        // Call the second add method with three integers
        int sum2 = calculator.add(5, 10, 15); // Calls add(int a, int b, int c)
        
        // Call the third add method with two doubles
        double sum3 = calculator.add(2.5, 3.5); // Calls add(double a, double b)
        
        // Call the fourth add method with two strings
        String result = calculator.add("Hello", "World"); // Calls add(String a, String b)
    }
}
```

In each method call, Java determines which version of the `add` method to execute based on the types and number of arguments passed. This allows for clear and concise method naming while providing flexibility in method usage.

#### Java's automatic type conversions 
play a role in resolving which overloaded method to invoke.

Let's analyze the provided code snippet:

```java
class OverloadDemo {
    void test(double a){
        System.out.println("Inside test(double) a: " + a);
    }
}

class Overload {
    public static void main(String args[]) {
        OverloadDemo ob = new OverloadDemo();
        int i = 88;
        ob.test(i);        // this will invoke test(double)
        ob.test(123.2);    // this will invoke test(double)
    }
}
```

1. **`test(double a)` Method**:
   - The `OverloadDemo` class defines a method `test(double a)` that takes a parameter of type `double`.

2. **`main` Method**:
   - In the `main` method of the `Overload` class, an instance of `OverloadDemo` is created.
   - An integer variable `i` is initialized with the value `88`.

3. **Method Invocation**:
   - `ob.test(i);`: This line invokes the `test` method with the `int` variable `i` as an argument. However, there's no method `test(int)` defined in `OverloadDemo`.
   - `ob.test(123.2);`: This line invokes the `test` method with a `double` literal `123.2` as an argument.

4. **Automatic Type Conversion**:
   - Since there is no method `test(int)` defined in `OverloadDemo`, Java looks for other compatible methods. It finds the `test(double a)` method.
   - Java can automatically convert an `int` into a `double`, so it elevates `i` to `double` and then calls `test(double)`.
   - Thus, the call `ob.test(i)` invokes the `test(double a)` method, and the output will be "Inside test(double) a: 88.0".

5. **Exact Match vs. Type Conversion**:
   - Java employs its automatic type conversions only if no exact match is found for the method call.
   - If a method `test(int)` had been defined in `OverloadDemo`, it would have been called instead of `test(double)`, as an exact match would be found.


#### returning objects from a method in Java. 

Let's break it down and discuss how it works:

```java
class Test {
    int a;
    
    Test(int i) {
        a = i;
    }
    
    Test incrByTen() {
        Test temp = new Test(a + 10);
        return temp;
    }
}

class RetOb {
    public static void main(String args[]) {
        Test ob1 = new Test(2);
        Test ob2;
        ob2 = ob1.incrByTen();
        System.out.println("ob1.a: " + ob1.a);
        System.out.println("ob2.a: " + ob2.a);
    }
}
```

1. **`Test` Class**:
   - This class defines a simple class named `Test` with an integer field `a`.
   - It has a constructor `Test(int i)` to initialize the value of `a`.

2. **`incrByTen()` Method**:
   - This method increments the value of `a` by ten and returns a new `Test` object with the updated value.
   - Inside the method, a new `Test` object named `temp` is created with the value of `a` incremented by ten.
   - The `temp` object is then returned to the calling routine.

3. **`RetOb` Class**:
   - This class contains the `main` method to demonstrate the usage of the `Test` class.

4. **`main` Method**:
   - In the `main` method, an instance of `Test` class named `ob1` is created with the initial value of `2`.
   - Another instance of `Test` class named `ob2` is declared.
   - The `incrByTen()` method is called on `ob1`, which returns a new `Test` object with `a` incremented by ten. This object reference is assigned to `ob2`.
   - The values of `a` for both `ob1` and `ob2` are then printed.

5. **Output**:
   - The output of the program is:
     ```
     ob1.a: 2
     ob2.a: 12
     ```
     This confirms that the value of `a` for `ob1` remains unchanged at `2`, while `ob2` has its `a` value incremented to `12` after invoking `incrByTen()`.

6. **Object Lifetime and Garbage Collection**:
   - Each time `incrByTen()` is invoked, a new `Test` object is created and returned. 
   - Objects created using `new` are dynamically allocated and exist until there are references to them.
   - When there are no more references to an object, it becomes eligible for garbage collection, and its memory is reclaimed by the JVM.

In summary, the `incrByTen()` method demonstrates how objects can be returned from methods in Java, allowing for dynamic creation and manipulation of objects within a program.

# Overriding

Let's illustrate method overriding in Java with an example:

```java
class Animal {
    void makeSound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    @Override
    void makeSound() {
        System.out.println("Dog barks");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal animal = new Animal();
        animal.makeSound(); // Output: Animal makes a sound
        
        Dog dog = new Dog();
        dog.makeSound(); // Output: Dog barks
    }
}
```

In this example, we have two classes: `Animal` and `Dog`. The `Animal` class has a method `makeSound()`, which prints "Animal makes a sound". The `Dog` class extends `Animal` and overrides the `makeSound()` method to print "Dog barks".

Let's break down the key concepts based on the provided notes:

1. **Method Overriding**:
   - When a method in a subclass (`Dog`) has the same name and type signature as a method in its superclass (`Animal`), it is said to override the method in the superclass.
   - In our example, the `makeSound()` method in the `Dog` class overrides the `makeSound()` method in the `Animal` class.

2. **Invocation of Overridden Method**:
   - When an overridden method is called from within its subclass (`Dog`), it will always refer to the version of that method defined by the subclass.
   - In our example, when `makeSound()` is called on a `Dog` object, it will execute the version of `makeSound()` defined in the `Dog` class, which prints "Dog barks".

3. **Hidden Superclass Method**:
   - The version of the method defined by the superclass (`Animal`) will be hidden when an overriding method is present in the subclass (`Dog`).
   - In our example, the implementation of `makeSound()` in the `Animal` class is hidden when `makeSound()` is called on a `Dog` object.

4. **Criteria for Method Overriding**:
   - Method overriding occurs only when the names and the type signatures of the two methods are identical.
   - If they are not identical, then the methods are simply overloaded, not overridden.
   - In our example, the `makeSound()` method in the `Dog` class overrides the `makeSound()` method in the `Animal` class because their names and type signatures match.

# Polymorphism 

in Java refers to the ability of objects to take on multiple forms. There are two main types of polymorphism: compile-time polymorphism (also known as static or method overloading) and runtime polymorphism (also known as dynamic or method overriding). Here, we'll focus on runtime polymorphism achieved through method overriding.

**Method Overriding**:

Method overriding occurs when a subclass provides a specific implementation of a method that is already defined in its superclass. The subclass overrides the behavior of the superclass method with its own implementation. This allows different types of objects to respond to the same method call differently based on their specific implementations.

**Example**:

Consider the following classes:

```java
// Superclass
class Animal {
    public void makeSound() {
        System.out.println("Some generic sound");
    }
}

// Subclass
class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Bark");
    }
}

// Another Subclass
class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow");
    }
}

// Test class
public class Main {
    public static void main(String[] args) {
        Animal animal1 = new Dog(); // Upcasting
        Animal animal2 = new Cat(); // Upcasting

        animal1.makeSound(); // Calls Dog's makeSound method
        animal2.makeSound(); // Calls Cat's makeSound method
    }
}
```

In this example:

- We have a superclass `Animal` with a method `makeSound()` that prints a generic sound.
- We have two subclasses, `Dog` and `Cat`, each of which overrides the `makeSound()` method with their specific sounds.
- In the `Main` class, we create instances of `Dog` and `Cat` and assign them to variables of type `Animal` using upcasting.
- When we call the `makeSound()` method on these `Animal` objects, Java invokes the appropriate overridden method based on the actual type of the object at runtime. This is called dynamic method dispatch.
- So, even though `animal1` and `animal2` are of type `Animal`, their actual runtime types are `Dog` and `Cat`, respectively. Therefore, when we call `makeSound()` on them, Java invokes the overridden methods in `Dog` and `Cat` classes, resulting in different sounds being printed for each object.

Encapsulation is the bundling of data (attributes) and methods (behaviors) that operate on the data into a single unit or class. It allows for the implementation details of a class to be hidden from the outside world, while providing a well-defined interface for interacting with the class. Encapsulation helps in achieving data hiding, which is the concept of restricting access to certain parts of an object, preventing unauthorized access and modification of data.

Here's how encapsulation can be implemented in Java:

1. **Declaring Data Members as Private**: Data members (attributes) of a class are declared as private, which means they can only be accessed within the same class.

2. **Providing Public Methods**: Public methods are provided to access and manipulate the private data members. These methods are also known as getter and setter methods.

3. **Getter Methods**: Getter methods are used to retrieve the values of private data members. They provide read-only access to the data.

4. **Setter Methods**: Setter methods are used to set the values of private data members. They provide write-only access to the data.

5. **Access Control**: By controlling access to the data through methods, encapsulation allows for validation, error-checking, and maintaining the integrity of the data.

**Example**:

Consider a `Person` class that encapsulates the details of a person, such as name and age:

```java
public class Person {
    // Private data members
    private String name;
    private int age;

    // Getter method for name
    public String getName() {
        return name;
    }

    // Setter method for name
    public void setName(String name) {
        // Validation logic can be added here
        this.name = name;
    }

    // Getter method for age
    public int getAge() {
        return age;
    }

    // Setter method for age
    public void setAge(int age) {
        // Validation logic can be added here
        if (age > 0 && age < 150) { // Assuming reasonable age range
            this.age = age;
        } else {
            System.out.println("Invalid age!");
        }
    }
}
```

In this example:

- The `Person` class encapsulates the data members `name` and `age` as private.
- Getter and setter methods (`getName()`, `setName()`, `getAge()`, `setAge()`) are provided to access and modify the private data members.
- By encapsulating the data members and providing public methods to access them, the implementation details of the `Person` class are hidden from the outside world, ensuring data integrity and security.
  
**Advantages of Encapsulation**:
- Enhanced security and data integrity
- Flexibility to change the internal implementation without affecting the external code
- Facilitation of reusability and modular programming

Abstraction is the concept of hiding the complex implementation details and showing only the essential features of an object. It focuses on what an object does rather than how it does it. In Java, abstraction can be achieved using abstract classes and interfaces.

Here's how abstraction can be implemented in Java:

1. **Abstract Classes**:  Abstract classes in Java are classes that cannot be instantiated on their own and may contain one or more abstract methods, which are declared without a body. Abstract classes serve as blueprints for other classes to inherit from, and they can also contain concrete methods with implementations.

**Characteristics of abstract classes**:

1. Abstract classes cannot be instantiated.
2. Abstract classes may contain abstract methods, concrete methods, or both.
3. Abstract methods do not have a body and must be implemented by subclasses.
4. Subclasses of abstract classes must either implement all abstract methods or be declared as abstract themselves.

**Usage of abstract classes**:

Abstract classes are used when you want to create a common interface for a group of related classes, but you want to leave the implementation details to the subclasses. They are particularly useful when you want to enforce a certain behavior across multiple subclasses while allowing each subclass to provide its own implementation.

2. **Abstract Methods**: An abstract method is a method declared without implementation in an abstract class. Subclasses must provide concrete implementations for these abstract methods.

**Example**:

Let's consider a simple example of abstraction using shapes:

```java
// Abstract class representing a shape
abstract class Shape {
    // Abstract method to calculate area (must be implemented by subclasses)
    public abstract double calculateArea();

    // Concrete method to display shape type
    public void displayType() {
        System.out.println("This is a shape");
    }
}

// Concrete subclass representing a circle
class Circle extends Shape {
    private double radius;

    // Constructor
    public Circle(double radius) {
        this.radius = radius;
    }

    // Implementation of abstract method to calculate area
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

// Concrete subclass representing a rectangle
class Rectangle extends Shape {
    private double length;
    private double width;

    // Constructor
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    // Implementation of abstract method to calculate area
    @Override
    public double calculateArea() {
        return length * width;
    }
}

// Test class
public class Main {
    public static void main(String[] args) {
        Shape circle = new Circle(5);
        Shape rectangle = new Rectangle(4, 6);

        System.out.println("Area of circle: " + circle.calculateArea());
        System.out.println("Area of rectangle: " + rectangle.calculateArea());

        circle.displayType(); // Output: This is a shape
        rectangle.displayType(); // Output: This is a shape
    }
}
```

In this example:

- `Shape` is an abstract class that defines an abstract method `calculateArea()`, which must be implemented by its subclasses. It also contains a concrete method `displayType()`.
- `Circle` and `Rectangle` are concrete subclasses of `Shape` that implement the `calculateArea()` method.
- In the `Main` class, we create instances of `Circle` and `Rectangle`, and we can call the `calculateArea()` method on them without worrying about the specific implementation details of each shape.

**Data Hiding**:

- **Definition**: Data hiding, also known as information hiding, is the process of restricting access to certain parts of an object, typically its data members, from the outside world. It is achieved by declaring the data members as private and providing public methods (getters and setters) to access and modify these data members.
  
- **Purpose**: The main purpose of data hiding is to prevent unauthorized access to an object's internal state and to maintain the integrity of its data. By hiding implementation details, data hiding allows for better encapsulation and reduces the risk of unintended modifications or misuse of data.

**Abstraction**:

- **Definition**: Abstraction is the process of hiding the complex implementation details of an object and showing only the essential features or behaviors of the object to the outside world. It focuses on what an object does rather than how it does it. Abstraction is achieved through abstract classes and interfaces, which define a set of abstract methods that must be implemented by concrete subclasses.
  
- **Purpose**: The main purpose of abstraction is to simplify the complexity of a system by providing a clear and concise interface for interacting with objects. It allows us to work with objects at a higher level of understanding, without needing to know the intricate details of their implementation. Abstraction promotes modularity, reusability, and flexibility in software design.

**Differences**:

1. **Focus**: Data hiding primarily focuses on restricting access to an object's data members, while abstraction focuses on hiding the implementation details of an object's behaviors.

2. **Implementation**: Data hiding is implemented by declaring data members as private and providing public methods to access and modify them. Abstraction is implemented using abstract classes and interfaces to define a clear interface for interacting with objects.

3. **Purpose**: Data hiding aims to protect an object's internal state and maintain data integrity. Abstraction aims to simplify the complexity of a system and provide a clear and concise interface for interacting with objects.

**Encapsulation**:

- **Purpose**: Encapsulation is the bundling of data (attributes) and methods (behaviors) that operate on the data into a single unit or class. It focuses on hiding the internal state of an object and providing a well-defined interface for interacting with the object.
  
- **Implementation**: Encapsulation involves declaring data members as private and providing public methods (getters and setters) to access and modify these data members. This ensures that the internal state of the object is protected and that data integrity is maintained.

**Differences**:

1. **Focus**: Abstraction focuses on hiding the implementation details of an object's behaviors, while encapsulation focuses on hiding the internal state of an object and providing controlled access to it.

2. **Purpose**: Abstraction simplifies the complexity of a system by providing a clear and concise interface for interacting with objects, while encapsulation ensures data integrity and security by restricting direct access to an object's internal state.

# Intefaces
3. **Interfaces**:  An interface in Java is a reference type, similar to a class, that can contain only abstract methods, default methods, static methods, constant fields, and nested types. It represents a contract for classes that implement it, specifying a set of methods that the implementing classes must provide. 

Here are the key points about interfaces:

1. **Abstract Methods**: Interfaces can declare abstract methods, which are methods without a body. These methods are implicitly public and abstract, so they must be implemented by any class that implements the interface.

2. **Default Methods**: Starting from Java 8, interfaces can also have default methods, which are methods with a default implementation. Default methods provide a way to add new methods to existing interfaces without breaking the classes that implement them.

3. **Static Methods**: Interfaces can contain static methods, which are methods that belong to the interface itself rather than any instance of the interface.

4. **Constant Fields**: Interfaces can contain constant fields, which are implicitly public, static, and final. These fields can be accessed using the interface name followed by the field name.

5. **Implementation**: A class can implement one or more interfaces by providing concrete implementations for all the abstract methods declared in those interfaces.

6. **Multiple Inheritance**: Unlike classes, which can only extend one superclass, a class can implement multiple interfaces. This allows for a form of multiple inheritance of behavior in Java.

7. **Purpose**: Interfaces are used to define a contract for classes, providing a way to achieve abstraction and polymorphism in Java. They allow for code reuse, modular design, and interoperability between different parts of a program.

Here's a simple example of an interface in Java:

```java
// Interface representing a shape
interface Shape {
    // Abstract method to calculate area
    double calculateArea();

    // Default method to display shape type
    default void displayType() {
        System.out.println("This is a shape");
    }

    // Static method to print information about shapes
    static void printInfo() {
        System.out.println("Shapes are geometric objects that have area and perimeter");
    }

    // Constant field for PI
    double PI = 3.14159;
}

// Concrete class representing a Circle that implements the Shape interface
class Circle implements Shape {
    private double radius;

    // Constructor
    public Circle(double radius) {
        this.radius = radius;
    }

    // Implementation of calculateArea method
    @Override
    public double calculateArea() {
        return Shape.PI * radius * radius;
    }
}

// Test class
public class Main {
    public static void main(String[] args) {
        Circle circle = new Circle(5);
        System.out.println("Area of circle: " + circle.calculateArea());

        circle.displayType(); // Output: This is a shape

        Shape.printInfo(); // Output: Shapes are geometric objects that have area and perimeter
    }
}
```

In this example:

- `Shape` is an interface that declares an abstract method `calculateArea()`, a default method `displayType()`, a static method `printInfo()`, and a constant field `PI`.
- The `Circle` class implements the `Shape` interface and provides a concrete implementation for the `calculateArea()` method.
- The `Main` class demonstrates how to use the `Circle` class and interface methods and fields.

if you want to extend an interface with another interface, you can use the `extends` keyword, similar to how you extend classes. This allows you to define a new interface that inherits the methods and variables of another interface.

Example:

```java

interface InterfaceA 
{
	void methodA(); 
}  

interface InterfaceB extends InterfaceA 
{     
	void methodB(); 
}
````

In this example, `InterfaceB` extends `InterfaceA`, so any class that implements `InterfaceB` must provide implementations for both `methodA()` and `methodB()`.

Sure, let's break down each of these concepts:

**Annotations**:

Annotations in Java provide metadata about the program, which can be used by the compiler or at runtime for various purposes. Annotations start with the `@` symbol followed by the annotation name. They can be applied to packages, types, fields, methods, and other program elements. Annotations are extensively used in Java frameworks like Spring, Hibernate, and JUnit for configuration, mapping, and testing purposes.

Example:
```java
// Annotation for deprecation
@Deprecated
class OldClass {
    // Deprecated method
    @Deprecated
    public void oldMethod() {
        // Method implementation
    }
}
```

**Nested Interfaces**:

An interface can be defined within another interface or class, making it a nested interface. Nested interfaces are used to logically group related interfaces and to encapsulate them within another interface or class. Nested interfaces can be public, private, protected, or default (package-private) and can be accessed by other classes based on their access modifiers.

Example:
```java
interface OuterInterface {
    void outerMethod();

    // Nested interface
    interface InnerInterface {
        void innerMethod();
    }
}

class Main {
    public static void main(String[] args) {
        // Implementing the nested interface
        OuterInterface.InnerInterface obj = new OuterInterface.InnerInterface() {
            @Override
            public void innerMethod() {
                System.out.println("Inner method implementation");
            }
        };

        obj.innerMethod(); // Output: Inner method implementation
    }
}
```

In this example, `InnerInterface` is a nested interface within the `OuterInterface`. It can be implemented by providing an anonymous class implementation, as shown in the `main` method.

# Object class

In Java, the `Object` class is a fundamental class defined in the `java.lang` package. It serves as the root class for all other classes in Java's class hierarchy. Every class in Java, either directly or indirectly, inherits from the `Object` class. As a result, the methods defined in the `Object` class are available to all Java objects.

Here are some of the key methods provided by the `Object` class:

1. **`hashCode()`**: This method returns a hash code value for the object. The hash code is used in hashing-based data structures such as `HashMap`, `HashSet`, etc., to efficiently store and retrieve objects. It's important that if two objects are equal according to the `equals()` method, they must have the same hash code.

2. **`equals(Object obj)`**: This method compares the current object with the specified object for equality. By default, it compares object references, but it can be overridden in subclasses to provide custom equality semantics based on object contents.

3. **`toString()`**: This method returns a string representation of the object. By default, it returns a string consisting of the class name followed by an "@" symbol and the object's hash code. It's often overridden in subclasses to provide a more meaningful string representation.

4. **`getClass()`**: This method returns the runtime class of the object. It returns an instance of the `Class` class representing the class of the object.

5. **`finalize()`**: This method is called by the garbage collector before reclaiming an object's memory. Subclasses can override this method to perform cleanup operations or resource deallocation.

6. **`clone()`**: This method creates and returns a copy of the object. To use this method, the class must implement the `Cloneable` interface. By default, the `clone()` method performs a shallow copy, but subclasses can override it to implement deep copy semantics.

7. **`notify()`, `notifyAll()`, `wait()`, `wait(long timeout)`, `wait(long timeout, int nanos)`**: These methods are used for inter-thread communication and synchronization using the object's intrinsic lock. They are typically used in multi-threaded programming scenarios.

8. **`instanceofOperator`**: Although not a method, the `instanceof` operator is often used with objects to check whether an object is an instance of a particular class or implements a specific interface. It returns `true` if the object is an instance of the specified type, or a subtype thereof; otherwise, it returns `false`.

These are some of the most commonly used methods provided by the `Object` class in Java, which are essential for understanding and working with Java objects.

### array of objects

`Object[]` is an array type in Java that can hold references to objects of any class. It is a special type of array where each element can refer to an object of any class, including instances of user-defined classes or built-in classes like `String`, `Integer`, etc.

Here's a basic example of creating and using an `Object[]` array:

```java
Object[] array = new Object[3]; // Creating an array of Object type with size 3

// Assigning objects of different classes to the array
array[0] = "Hello"; // String object
array[1] = 10;      // Integer object
array[2] = new MyClass(); // User-defined class object

// Retrieving objects from the array
String str = (String) array[0]; // Casting to String type
int num = (int) array[1];        // Casting to int type
MyClass obj = (MyClass) array[2]; // Casting to MyClass type

// Using the retrieved objects
System.out.println(str); // Output: Hello
System.out.println(num); // Output: 10
obj.myMethod();          // Call a method of MyClass
```

In this example:

- `Object[] array` declares an array that can hold references to objects of any type.
- We assign objects of different classes (`String`, `Integer`, and `MyClass`) to the array elements.
- When retrieving objects from the array, we need to cast them to the appropriate type because the array itself is of type `Object[]`.
- The ability to store objects of any class in an `Object[]` array makes it a flexible choice for scenarios where you need to work with objects of diverse types in a single array. However, it also requires explicit casting when retrieving objects from the array, which can introduce runtime type errors if not done correctly.
# Generics

in Java provide a way to create classes, interfaces, and methods that operate on objects of specified types. They allow you to define classes and methods with type parameters, which can be replaced by actual types when instances of those classes or calls to those methods are made. Generics provide compile-time type safety and enable code reuse by allowing you to write code that works with a variety of types.

**Problem solved by Generics**:

Let's consider a custom ArrayList implementation without generics, and then we'll see how generics can solve the problem it presents.

**ArrayList without Generics:**

```java
public class CustomArrayList {
    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public CustomArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public void add(Object item) {
        if (size == elements.length) {
            resize();
        }
        elements[size++] = item;
    }

    private void resize() {
        Object[] newArray = new Object[elements.length * 2];
        System.arraycopy(elements, 0, newArray, 0, elements.length);
        elements = newArray;
    }

    public Object get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return elements[index];
    }
}
```

In this implementation:

- The `CustomArrayList` class uses an array of type `Object[]` to store elements.
- The `add` method accepts any type of object, so we need to store objects of different types in the array.
- When retrieving elements from the list using the `get` method, we need to cast the returned object to the appropriate type.

**Problem:**

1. **Lack of Type Safety**: Since the `CustomArrayList` class can store objects of any type, there's no compile-time type checking to ensure that only objects of a specific type are added to the list. This can lead to runtime errors if we accidentally add an object of the wrong type to the list.

2. **Casting Required**: When retrieving elements from the list using the `get` method, we need to cast the returned object to the appropriate type. If we cast to the wrong type, it can result in a `ClassCastException` at runtime.

3. If at all we decide to use the an `ArrayList` implementation which strictly uses int arrays, we will have to re-copy the code and re-implement it again for a String, float, etc. types. this causes redundancy. So, we are looking for a way that we create a template, and then later change the type of this template according to our usage requirement.


**Solution with Generics:**

By introducing generics, we can make the `CustomArrayList` class type-safe and eliminate the need for casting when retrieving elements from the list.

```java
public class CustomArrayList<T> {
    private T[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public CustomArrayList() {
        this.elements = (T[]) new Object[DEFAULT_CAPACITY];
        //we typecast the Object array to the T type and make a T[] array
        this.size = 0;
    }

    public void add(T item) {
        if (size == elements.length) {
            resize();
        }
        elements[size++] = item;
    }

    private void resize() {
        T[] newArray = (T[]) new Object[elements.length * 2];
        System.arraycopy(elements, 0, newArray, 0, elements.length);
        elements = newArray;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return elements[index];
    }
}
```

With generics:

- We declare the `CustomArrayList` class with a type parameter `<T>`, which represents the type of elements stored in the list.
- The array `elements` is declared as an array of type `T[]`, so it can only store elements of type `T`.
- The `add` method accepts an argument of type `T`, ensuring that only objects of type `T` can be added to the list.
- The `get` method returns an object of type `T`, eliminating the need for casting when retrieving elements from the list.

# Lambda function

In Java, the `Function` interface is part of the `java.util.function` package introduced in Java 8 as a part of the Java Functional Programming API. It represents a function that accepts one argument and produces a result. The `Function` interface has a single abstract method called `apply()` that takes an argument of a certain type (`T`) and returns a result of another type (`R`).

Here's the declaration of the `Function` interface:

```java
@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
}
```

- `T`: Represents the type of the input argument to the function.
- `R`: Represents the type of the result produced by the function.

The `Function` interface is a functional interface because it has only one abstract method (`apply()`), making it suitable for use with lambda expressions.

**Why Interfaces are Used with Lambda Expressions:**

Lambda expressions in Java are primarily used with functional interfaces. Interfaces provide a way to define contracts for behavior, and functional interfaces specifically define contracts for functions. By using interfaces, lambda expressions can be used as instances of these interfaces, providing a concise way to define behavior inline.

**Implementation and Working of Lambda Functions:**

1. **Defining a Lambda Expression:**
   Lambda expressions are defined using the syntax `(parameters) -> expression_or_statement`. For example, `(x) -> x * 2` represents a lambda expression that takes one parameter `x` and returns `x * 2`.

2. **Using with Functional Interfaces:**
   Lambda expressions are typically used with functional interfaces. In the example of `Function`, the lambda expression `(x) -> x * 2` can be used to create an instance of the `Function` interface that doubles its input.

3. **Passing Lambda Expressions as Arguments:**
   Lambda expressions can be passed as arguments to methods that expect functional interfaces. For example, you can pass a lambda expression to a method that takes a `Function` as an argument.

4. **Executing the Lambda Expression:**
   When a method that expects a functional interface with a lambda expression as an argument is called, the lambda expression is executed. In the case of `Function`, when you call the `apply()` method on an instance of `Function`, the lambda expression inside it is executed with the provided argument, and the result is returned.

Here's a brief example demonstrating the implementation and working of a lambda function using the `Function` interface:

```java
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        // Lambda expression to double a number
        Function<Integer, Integer> doubler = (x) -> x * 2;

        // Applying the lambda expression
        int result = doubler.apply(5); // Output: 10
        System.out.println("Result: " + result);
    }
}
```

In this example, the lambda expression `(x) -> x * 2` doubles its input, and it's used with the `Function` interface to create a `doubler` function. When `apply()` is called with an argument of `5`, the lambda expression is executed, and the result `10` is returned.

# Exception handling

In Java, the `Throwable` class is the superclass of all errors and exceptions. It forms the root of the exception hierarchy, from which the classes `Error` and `Exception` inherit. Both errors and exceptions are types of `Throwable`, but they represent different types of problems in a Java program.

**Errors**: Errors are exceptional conditions that are typically caused by the environment in which the application is running. These are serious and typically cannot be recovered from. Examples of errors include `OutOfMemoryError`, `StackOverflowError`, etc. Errors should not typically be caught or handled in code because they generally indicate a severe problem that cannot be resolved programmatically.

**Exceptions**: Exceptions, on the other hand, are exceptional conditions that occur during the execution of a program. These are typically caused by faults in the program's logic or unexpected input data. Exceptions are further divided into two main categories: checked exceptions and unchecked exceptions.

- Checked exceptions are those that the compiler forces you to handle explicitly by either catching them or declaring that your method throws them. Examples include `IOException`, `SQLException`, etc.

- Unchecked exceptions, also known as runtime exceptions, are those that do not need to be declared in a method's throws clause and are not enforced by the compiler. Examples include `NullPointerException`, `ArrayIndexOutOfBoundsException`, `ArithmeticException`, etc.

**Exception Handling in Java**:

Java provides mechanisms to handle exceptions through try-catch blocks, try-with-resources statements, and the throw keyword.

1. **try-catch Block**: A try-catch block is used to catch exceptions that may occur within a block of code. The syntax is as follows:

```java
try {
    // Code that may throw an exception
} catch (ExceptionType e) {
    // Code to handle the exception
}
```

Example:

```java
try {
    int result = 10 / 0; // ArithmeticException
} catch (ArithmeticException e) {
    System.out.println("Error: " + e.getMessage()); // Output: Error: / by zero
}
```

2. **try-with-resources Statement**: The try-with-resources statement is used to automatically close resources such as files, sockets, etc., that are opened within the try block. It ensures that the resources are closed even if an exception occurs.

```java
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
    // Read from the file
} catch (IOException e) {
    // Handle IOException
}
```

3. **Throwing Exceptions**: You can explicitly throw an exception using the `throw` keyword.

```java
if (condition) {
    throw new ExceptionType("Error message");
}
```

Example:

```java
public int divide(int dividend, int divisor) throws ArithmeticException {
    if (divisor == 0) {
        throw new ArithmeticException("Divisor cannot be zero");
    }
    return dividend / divisor;
}
```

in
```java
public int divide(int dividend, int divisor) throws ArithmeticException {..}
```

The `throws ArithmeticException` clause indicates that the `divide` method may throw an `ArithmeticException` during its execution.

# Object cloning

In Java, object cloning refers to the process of creating a duplicate or copy of an existing object. The `Cloneable` interface is used to mark objects as being eligible for cloning. However, it's important to note that cloning is not automatically enabled for all objects in Java; only classes that implement the `Cloneable` interface can be cloned.

**Concept of Object Cloning:**

1. **The `Cloneable` Interface**:
   - The `Cloneable` interface is a marker interface in Java, meaning it doesn't have any methods. Its sole purpose is to indicate to the Java runtime system that the objects of a class implementing this interface are allowed to be cloned using the `clone()` method.

2. **The `clone()` Method**:
   - The `clone()` method is defined in the `Object` class as a protected method. It is used to create a copy of an object. However, since it's declared as protected, it cannot be called directly from outside the class.
   - In order to clone an object, the class of that object must override the `clone()` method and make it public, or call `super.clone()` from within the method. Additionally, the class must implement the `Cloneable` interface to indicate that it supports cloning.

3. **Shallow Copy vs. Deep Copy**:
   - By default, the `clone()` method creates a shallow copy of the object, meaning it duplicates only the object's fields. If the object contains references to other objects, those references are copied, but the objects themselves are not cloned. This can lead to shared references between the original and cloned objects.
   - To achieve a deep copy, where all internal objects are also cloned recursively, the class must override the `clone()` method to perform a deep copy manually. This involves creating new instances of any referenced objects and copying their state into the cloned object.

**Example:**

Here's an example demonstrating the concept of object cloning:

```java
class MyClass implements Cloneable {
    private int value;

    public MyClass(int value) {
        this.value = value;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); // Shallow copy
    }
}

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        MyClass obj1 = new MyClass(10);
        MyClass obj2 = (MyClass) obj1.clone();

        System.out.println("Original object: " + obj1.getValue()); // Output: 10
        System.out.println("Cloned object: " + obj2.getValue()); // Output: 10
    }
}
```

In this example:

- The `MyClass` implements the `Cloneable` interface to indicate that it supports cloning.
- The `clone()` method in `MyClass` is overridden to perform a shallow copy of the object using `super.clone()`.
- The `main` method creates an instance of `MyClass` named `obj1`, and then clones it to create `obj2`.
- Both `obj1` and `obj2` are independent objects, but they share the same values because `clone()` performs a shallow copy.

### Deep and Shallow copies

In Java, object cloning refers to the process of creating an exact copy of an existing object. There are two types of object cloning: shallow copy and deep copy.

1. **Shallow Copy**:
   - In shallow copy, only the top-level structure of the object is duplicated. If the object contains references to other objects, those references are copied, but the objects themselves are not cloned.
   - In other words, a shallow copy creates a new object and then copies the references to the internal objects from the original object to the new object.
   - Changes made to the internal objects in the clone will affect the original object and vice versa.
   
2. **Deep Copy**:
   - In deep copy, not only the top-level structure but also all the internal objects are duplicated recursively.
   - Each object and its internal objects are cloned to create completely independent copies.
   - Changes made to the internal objects in the clone will not affect the original object and vice versa.

**Example:**

Let's consider a class `Person` that contains a reference to another class `Address`. We'll create an object of `Person` and clone it to demonstrate both shallow and deep copy.

```java
class Address {
    String city;

    public Address(String city) {
        this.city = city;
    }
}

class Person implements Cloneable {
    String name;
    Address address;

    public Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Address address = new Address("New York");
        Person person1 = new Person("John", address);

        // Shallow copy
        Person person2 = (Person) person1.clone();
        System.out.println("Shallow Copy:");
        System.out.println("Before modification:");
        System.out.println("person1: " + person1.address.city); // Output: New York
        System.out.println("person2: " + person2.address.city); // Output: New York

        person2.address.city = "Los Angeles";
        System.out.println("After modification:");
        System.out.println("person1: " + person1.address.city); // Output: Los Angeles
        System.out.println("person2: " + person2.address.city); // Output: Los Angeles

        // Deep copy
        Person person3 = new Person(person1.name, new Address(person1.address.city));
        System.out.println("\nDeep Copy:");
        System.out.println("Before modification:");
        System.out.println("person1: " + person1.address.city); // Output: Los Angeles
        System.out.println("person3: " + person3.address.city); // Output: Los Angeles

        person3.address.city = "Chicago";
        System.out.println("After modification:");
        System.out.println("person1: " + person1.address.city); // Output: Los Angeles
        System.out.println("person3: " + person3.address.city); // Output: Chicago
    }
}
```

In the example:

- For shallow copy, changes to the `address.city` in `person2` affect `person1`, as they both share the same reference to the `Address` object.
- For deep copy, changes to the `address.city` in `person3` do not affect `person1`, as they have independent copies of the `Address` object.

put much simply

- **Shallow Copy**: Copies the top-level object and its primitive fields(like int age, int usn ), but for non-primitive (reference) fields(like String, arrays), it copies the references only, not the objects themselves. So, both the original object and the cloned object will share the same referenced objects. If you modify the referenced objects through one object, the changes will be visible through the other object as well.

- **Deep Copy**: Creates a new instance of the top-level object and recursively copies all non-primitive fields as well. So, not only are the primitive fields copied, but also new instances of the referenced objects are created. As a result, the original and cloned objects have their own separate copies of all referenced objects, and changes made to one object do not affect the other.

# Collection frameworks

In Java, collections refer to data structures and classes that are used to store and manipulate groups of objects. The Java Collections Framework provides a set of interfaces and classes that implement various collection types, algorithms, and utilities to work with collections. It provides a unified architecture for representing and manipulating collections, making it easier to work with collections of objects in Java.

**Key Interfaces in the Java Collections Framework:**

1. **Collection**: The `Collection` interface is the root interface in the Java Collections Framework. It represents a group of objects known as elements. Subinterfaces of `Collection` include `List`, `Set`, and `Queue`.

2. **List**: The `List` interface represents an ordered collection of elements where elements can be inserted or accessed by their index. Examples include `ArrayList`, `LinkedList`, etc.

3. **Set**: The `Set` interface represents a collection of unique elements where duplicates are not allowed. Examples include `HashSet`, `TreeSet`, etc.

4. **Map**: The `Map` interface represents a collection of key-value pairs where each key is associated with exactly one value. Examples include `HashMap`, `TreeMap`, etc.

5. **Queue**: The `Queue` interface represents a collection designed for holding elements before processing. It follows the FIFO (First-In-First-Out) order. Examples include `LinkedList`, `PriorityQueue`, etc.

**Key Classes in the Java Collections Framework:**

1. **ArrayList**: Implements the `List` interface using a dynamic array. It allows fast random access and dynamic resizing of the underlying array.

2. **LinkedList**: Implements the `List` interface using a doubly-linked list. It provides efficient insertion and deletion operations.

3. **HashSet**: Implements the `Set` interface using a hash table. It does not maintain order but provides constant-time performance for basic operations.

4. **TreeSet**: Implements the `Set` interface using a red-black tree. It maintains elements in sorted order and provides logarithmic-time performance for most operations.

5. **HashMap**: Implements the `Map` interface using a hash table. It provides constant-time performance for basic operations and allows null keys and values.

6. **TreeMap**: Implements the `Map` interface using a red-black tree. It maintains key-value pairs in sorted order and provides logarithmic-time performance for most operations.

**Examples:**

Here's a simple example demonstrating the usage of some classes from the Java Collections Framework:

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Creating and adding elements to an ArrayList
        List<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Orange");
        System.out.println("ArrayList: " + list);

        // Creating and adding elements to a HashMap
        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 10);
        map.put("Banana", 20);
        map.put("Orange", 30);
        System.out.println("HashMap: " + map);
    }
}
```


# Vector

In Java, the `Vector` class is a part of the Java Collections Framework and is used to create dynamic arrays that can grow or shrink in size as needed. Like `ArrayList`, `Vector` internally uses an array to store elements. It automatically grows or shrinks its internal array as needed when elements are added or removed..

In a `Vector`, when one thread is accessing or modifying the `Vector` (for example, iterating over its elements or adding/removing elements), other threads must wait until the first thread completes its operation before they can access or modify the `Vector` again. This is because `Vector` provides synchronized methods, which means that each method call is synchronized on the `Vector` instance, ensuring that only one thread can execute a synchronized method on the `Vector` at a time. This is vector synchronisation.

In contrast, with an `ArrayList`, the methods are not synchronized by default. Therefore, multiple threads can access and modify an `ArrayList` instance simultaneously without any thread synchronization. However, this concurrent access can lead to data corruption or inconsistency if proper synchronization is not implemented externally when dealing with concurrent access to an `ArrayList`.


```java
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        // Creating a synchronized Vector
        Vector<Integer> vector = new Vector<>();

        // Adding elements to the vector
        vector.add(1);
        vector.add(2);
        vector.add(3);
        }
    }
}
```

# enums

In Java, an enumeration, often referred to as an enum, is a special data type used to define a collection of named constants. Enumerations provide a way to create a fixed set of related constants that represent possible values for a particular variable or property.

**Key Points about Enumerations:**

1. **Definition**: An enumeration is created using the `enum` keyword in Java. It defines a class type, allowing you to define a set of named constants within that class.

2. **Enum Declaration**: Enum declarations can be done outside a class or inside a class, but not inside a method.

3. **Enum Constants**: Each constant declared within an enum is implicitly a public, static, final instance of the enum class.

4. **Enum and Inheritance**: All enums implicitly extend the `java.lang.Enum` class. Enums cannot extend other classes or be superclassed. However, an enum can implement multiple interfaces.

5. **Enum Methods**: Enums can contain concrete methods, but not abstract methods. The `toString()` method is overridden in the `Enum` class to return the name of the enum constant.

6. **Enum Constants Comparison**: Enum constants can be compared for equality using the `==` operator. The `equals()` method can also be used to compare an enumeration constant with any other object.

7. **Enum Utility Methods**: The `java.lang.Enum` class provides utility methods like `values()`, `ordinal()`, and `valueOf()` for working with enums. These methods allow you to get all values, the ordinal index of a constant, and retrieve an enum constant by its name, respectively.

8. **Enum Constructors**: Enums can have constructors, but they are executed separately for each enum constant at the time of enum class loading. Enum objects cannot be created explicitly, and enum constructors cannot be public or protected to prevent multiple object initialization.

**Example:**

```java
enum Color {
    RED, BLUE, GREEN; // Enum constants

    private Color() {
        // Constructor for each enum constant
        System.out.println("Constructor called for: " + this.name());
    }

    // Concrete method in enum
    public String getColorDescription() {
        switch (this) {
            case RED:
                return "Red color";
            case BLUE:
                return "Blue color";
            case GREEN:
                return "Green color";
            default:
                return "Unknown color";
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Accessing enum constants and methods
        Color red = Color.RED;
        System.out.println("Enum constant: " + red);
        System.out.println("Color description: " + red.getColorDescription());

        // Enum comparison
        Color color1 = Color.RED;
        Color color2 = Color.BLUE;
        System.out.println("Comparison result: " + (color1 == color2)); // false
    }
}
```

In this example:

- We define an enum `Color` with constants `RED`, `BLUE`, and `GREEN`.
- Each enum constant has a constructor that is executed separately for each constant at enum class loading time.
- We define a concrete method `getColorDescription()` in the enum to provide a description for each color.




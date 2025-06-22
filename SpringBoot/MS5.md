Awesome, Mihir! Let's now dive into **Phase 5 – Asynchronous Communication**, where microservices talk to each other without waiting for immediate replies. This is a **major shift** from synchronous calls like HTTP.

---

# 🔄 Phase 5: Async Messaging in Microservices

---

## 🧠 Why Go Asynchronous?

Let’s compare:

| Synchronous (HTTP)                  | Asynchronous (Messaging)            |
| ----------------------------------- | ----------------------------------- |
| Tight coupling – service must be up | Loose coupling – works even if down |
| Client **waits** for response       | Fire-and-forget or delayed response |
| Slower, blocks threads              | Faster, doesn’t block               |
| Good for queries                    | Great for commands/events           |

So when to go async?

✅ You don’t need an **immediate response**
✅ You want **better scalability & decoupling**

---

## 🧰 Two Key Messaging Tools

| Tool         | Purpose                         | Best for                     |
| ------------ | ------------------------------- | ---------------------------- |
| **Kafka**    | High-throughput, pub-sub stream | Events: `order-placed`, etc. |
| **RabbitMQ** | Reliable task queues            | Commands: `send-email`, etc. |

We’ll start with **Kafka**, then explore **RabbitMQ**.

---

# 🔥 Part 1: Kafka for Event Streaming

---

## 🧠 Kafka in Simple Terms

Kafka is a **distributed log** where services **publish events** and others **subscribe** to them.

📦 Example:

* `order-service`: *publishes* an `order-placed` event
* `notification-service`: *subscribes* and sends an email

---

## ✅ Step-by-Step Kafka Integration

### 🔌 Step 1: Add Dependencies

```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
```

---

### ⚙️ Step 2: Configuration (`application.yml`)

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: my-group
      auto-offset-reset: earliest
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```

---

### 🏗️ Step 3: Kafka Producer (Event Publisher)

In `order-service`:

```java
@Service
public class KafkaEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderPlaced(String orderJson) {
        kafkaTemplate.send("order-placed", orderJson);
    }
}
```

When an order is placed, send an event like:

```json
{
  "orderId": "123",
  "userId": "456",
  "total": 300.0
}
```

---

### 📬 Step 4: Kafka Listener (Event Consumer)

In `notification-service`:

```java
@Service
public class OrderPlacedListener {

    @KafkaListener(topics = "order-placed", groupId = "notif-group")
    public void handle(String orderJson) {
        System.out.println("📩 Email sent for order: " + orderJson);
        // Send actual email logic
    }
}
```

---

### 🔄 How It Works

```
Order Service ➡️ Kafka ➡️ Notification Service
             (event)      (listener)
```

⏱ No waiting → Order is placed instantly
📦 Multiple consumers can listen to the same topic

---

## 🧩 Bonus: Use Object Mapper

Instead of raw JSON, you can send a Java object using:

```java
kafkaTemplate.send("order-placed", new ObjectMapper().writeValueAsString(orderDto));
```

Or configure Kafka to serialize Java objects using `JsonSerializer`.

---

# 📨 Part 2: RabbitMQ for Task Queues

---

## 🧠 RabbitMQ in Simple Terms

RabbitMQ is a **queue** system – one message goes to **one** consumer.
Perfect for tasks like:

* Sending an email
* Generating a PDF
* Processing payments

---

## ✅ Step-by-Step RabbitMQ Integration

### 🔌 Step 1: Add Dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

---

### ⚙️ Step 2: Configuration

```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

---

### ✉️ Step 3: Declare Queue

```java
@Configuration
public class RabbitConfig {

    @Bean
    public Queue emailQueue() {
        return new Queue("email-queue", false);
    }
}
```

---

### 🏗️ Step 4: Send Task to Queue (Producer)

In `order-service`:

```java
@Service
public class EmailTaskSender {

    private final RabbitTemplate rabbitTemplate;

    public EmailTaskSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmailTask(String emailJson) {
        rabbitTemplate.convertAndSend("email-queue", emailJson);
    }
}
```

---

### 📬 Step 5: Listen & Process (Consumer)

In `notification-service`:

```java
@Component
public class EmailTaskListener {

    @RabbitListener(queues = "email-queue")
    public void handle(String emailJson) {
        System.out.println("📧 Processing email task: " + emailJson);
        // Logic to send email
    }
}
```

---

## 🧠 Kafka vs RabbitMQ — TL;DR

| Feature          | Kafka                          | RabbitMQ                      |
| ---------------- | ------------------------------ | ----------------------------- |
| Model            | Pub/Sub                        | Task Queue                    |
| Message Routing  | Topics + Partitions            | Exchanges + Queues            |
| Message Delivery | High-throughput (scalable)     | Reliable (delivery guarantee) |
| Consumers        | Many consumers per topic       | One consumer per message      |
| Use for          | Streaming, logs, state changes | Tasks, jobs, notifications    |

---

## ✅ Real-World Use Case

### Order Placement Flow:

```
User ➡️ Places Order
Order Service:
   → Save Order
   → Kafka: publish `order-placed`
   → RabbitMQ: send email notification task
```

So you can **combine both**:

* Kafka for *state events*
* RabbitMQ for *action commands*

---

## 🚀 Up Next: Phase 6 – Dockerize the Whole System

* Package all microservices, Kafka, RabbitMQ into containers
* Run them with `docker-compose`
* Build an actual production-like local setup 💥

Let me know when you're ready to begin Phase 6!

# Day 22: System Design Fundamentals 🏗️

## 🎯 Learning Objectives
- Understand system design principles
- Learn about scalability and performance
- Master CAP theorem and trade-offs
- Design reliable distributed systems

---

## 📚 Theory: Deep Dive

### 1. What is System Design?

System design is the process of defining architecture, components, and data flow for a system to satisfy specified requirements.

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    SYSTEM DESIGN PROCESS                               │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   ┌─────────────┐    ┌─────────────┐    ┌─────────────┐              │
│   │ Requirements│───▶│  High-Level │───▶│      Low    │              │
│   │ Gathering   │    │   Design    │    │   Level     │              │
│   └─────────────┘    └─────────────┘    │   Design    │              │
│         │                  │             └─────────────┘              │
│         ▼                  ▼                   ▼                       │
│   • Use Cases        • Architecture      • Class Diagrams            │
│   • User Stories     • Components       • Database Schema           │
│   • Constraints      • Tech Stack       • API Design               │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 2. Scalability Fundamentals

#### Horizontal vs Vertical Scaling

```
┌─────────────────────────────────────────────────────────────────────────┐
│                   SCALING APPROACHES                                    │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   VERTICAL SCALING                    HORIZONTAL SCALING               │
│   ===================                 =====================               │
│                                                                         │
│   ┌─────────┐                       ┌─────────┐                        │
│   │ Server  │                       │ Server  │                        │
│   │  16GB  │                       │  4GB    │                        │
│   └────┬────┘                       └────┬────┘                        │
│        │                                  │                            │
│        ▼                                  ▼                            │
│   ┌─────────┐                       ┌─────────┐ ┌─────────┐          │
│   │ Server  │       ====>           │ Server  │ │ Server  │          │
│   │  64GB  │                       │  4GB    │ │  4GB    │          │
│   └─────────┘                       └─────────┘ └─────────┘          │
│                                                                         │
│   • Easier to implement           • Better fault tolerance             │
│   • Single point of failure       • Handles more load                │
│   • Hardware limits               • Requires load balancer            │
│   • Cost-effective up to point    • Complex data consistency         │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 3. CAP Theorem

In distributed systems, you can only guarantee two of three properties:

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        CAP THEOREM                                      │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│                        CONSISTENCY                                      │
│                           ✦                                             │
│                          ╱ ╲                                            │
│                         ╱   ╲                                           │
│                        ╱     ╲                                          │
│                       ╱   ✦   ╲           You can only pick TWO!       │
│                      ╱   ╱ ╲   ╲                                      │
│                     ╱   ╱   ╲   ╲                                     │
│                    ╱   ╱     ╲   ╲                                    │
│                   ╱   ╱       ╲   ╲                                   │
│                  ╱   ╱         ╲   ╲                                  │
│                 ╱   ╱           ╲   ╲                                 │
│                ▼   ▼           ▼   ▼                                 │
│         ┌──────────┐     ┌──────────┐     ┌──────────┐              │
│         │AVAILABILITY│   │ PARTITION │   │ CONSISTENCY│             │
│         │          │     │ TOLERANCE │   │           │              │
│         │ Always   │     │  System   │   │  Linear  │              │
│         │ Available│     │  continues│   │ izable   │              │
│         │          │     │ despite   │   │  reads   │              │
│         │          │     │  failures │   │  writes  │              │
│         └──────────┘     └──────────┘     └──────────┘              │
│                                                                         │
│   Trade-offs:                                                          │
│   • CA (Traditional databases) - not partition tolerant              │
│   • CP (Zookeeper, HBase) - may be unavailable                       │
│   • AP (Cassandra, DynamoDB) - may return stale data                 │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 4. Load Balancing

```
┌─────────────────────────────────────────────────────────────────────────┐
│                      LOAD BALANCING                                     │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│                         ┌─────────────┐                                 │
│                    ┌───▶│Load Balancer│───┐                            │
│                    │    └─────────────┘   │                            │
│                    │                      │                            │
│      ┌─────────────┼──────────────────────┼─────────────┐            │
│      │             │                      │             │            │
│      ▼             ▼                      ▼             ▼            │
│   ┌──────┐    ┌──────┐              ┌──────┐    ┌──────┐          │
│   │Server│    │Server│              │Server│    │Server│          │
│   │  1   │    │  2   │              │  3   │    │  4   │          │
│   └──────┘    └──────┘              └──────┘    └──────┘          │
│                                                                         │
│   Algorithms:                                                            │
│   • Round Robin - Simple, even distribution                          │
│   • Least Connections - Routes to least active                        │
│   • IP Hash - Session persistence                                    │
│   • Weighted - Based on server capacity                              │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 5. Caching Strategies

```
┌─────────────────────────────────────────────────────────────────────────┐
│                       CACHING STRATEGIES                               │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   CACHE ASIDE                     WRITE THROUGH                        │
│   ============                     =============                        │
│                                                                         │
│   ┌─────┐    read              ┌─────┐    ┌─────┐                 │
│   │Cache│◀───────┐             │Cache│───▶│  DB │                 │
│   └─────┘        │             └─────┘    └─────┘                 │
│      │           │                │                                   │
│      ▼ miss      │                ▼ write                          │
│   ┌─────┐        │             ┌─────┐                              │
│   │  DB │────────┘             │  DB │                              │
│   └─────┘                     └─────┘                              │
│                                                                         │
│   • Read-heavy              • Write-heavy                             │
│   • Cache miss = DB load   • Slower writes                           │
│   • Stale data possible    • Always consistent                       │
│                                                                         │
│   CACHE-AHEAD                    WRITE-BACK                            │
│   =============                   ==========                            │
│                                                                         │
│   ┌─────┐    ┌─────┐          ┌─────┐    ┌─────┐                  │
│   │Cache│───▶│ App  │          │ App │───▶│Cache│                  │
│   └─────┘    └─────┘          └─────┘    └─────┘                  │
│      │                            │                                   │
│      │ populate                  │ async write                        │
│      ▼                            ▼                                   │
│   ┌─────┐                      ┌─────┐    ┌─────┐                  │
│   │  DB │                      │Cache│───▶│  DB │                  │
│   └─────┘                      └─────┘    └─────┘                  │
│                                                                         │
│   • Predictable reads      • Fast writes                              │
│   • Warm-up needed        • Risk of data loss                        │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 6. Database Concepts

#### ACID Properties

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     ACID PROPERTIES                                     │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   A - ATOMICITY:     All operations succeed or all fail              │
│   C - CONSISTENCY:   Data remains valid after transaction            │
│   I - ISOLATION:     Concurrent transactions don't interfere         │
│   D - DURABILITY:    Committed data survives system failure          │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

#### SQL vs NoSQL

| Aspect | SQL (Relational) | NoSQL (Non-Relational) |
|--------|-------------------|------------------------|
| **Schema** | Fixed schema | Flexible schema |
| **Transactions** | ACID compliant | Eventually consistent |
| **Scaling** | Vertical (mostly) | Horizontal |
| **Query Language** | SQL | Varies |
| **Examples** | MySQL, PostgreSQL | MongoDB, Cassandra |
| **Use Cases** | Financial, Transactions | Big Data, Real-time |

### 7. Message Queues

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     MESSAGE QUEUE ARCHITECTURE                          │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   PRODUCER              MESSAGE QUEUE             CONSUMER             │
│   ────────              ─────────────             ────────             │
│                                                                         │
│   ┌─────┐               ┌──────────┐            ┌─────┐              │
│   │ Msgs│──────────────▶│ Queue    │───────────▶│ Msgs│              │
│   └─────┘               └──────────┘            └─────┘              │
│                                │                                         │
│                                ▼                                         │
│                         ┌──────────┐                                    │
│                         │ Kafka    │                                    │
│                         │ RabbitMQ │                                    │
│                         │ SQS      │                                    │
│                         └──────────┘                                    │
│                                                                         │
│   Benefits:                                                            │
│   • Decoupling - Producers and consumers independent                  │
│   • Reliability - Messages persisted until processed                  │
│   • Scalability - Handle burst traffic                                │
│   • Async - Process in background                                     │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 💡 Interview Questions & Answers

### Q1: How would you design a URL shortening service like bit.ly?

**Answer:**
```
Key Components:
1. Generate unique short URL (hash or random)
2. Store mapping: short_url → original_url
3. Redirect short URL to original

Architecture:
- Load balancer → App servers → Database
- Cache frequently accessed URLs (Redis)
- Use unique ID generator (snowflake)

Database:
- SQL: URL mappings with index on short_code
- NoSQL: DynamoDB with short_code as partition key
```

### Q2: What is the difference between microservices and monolithic architecture?

**Answer:**
| Aspect | Monolithic | Microservices |
|--------|-----------|---------------|
| **Deployment** | Single unit | Independent services |
| **Scaling** | Scale entire app | Scale individual services |
| **Technology** | Single stack | Polyglot |
| **Complexity** | Lower initial | Higher operational |
| **Fault Tolerance** | Single point of failure | Isolated failures |
| **Data** | Shared database | Database per service |

### Q3: How would you handle a million concurrent connections?

**Answer:**
1. **Load Balancing**: Distribute across multiple servers
2. **Connection Pooling**: Reuse database connections
3. **Caching**: Reduce database load
4. **Async I/O**: Non-blocking servers (Netty, Node.js)
5. **CDN**: Offload static content
6. **Message Queues**: Decouple processing

### Q4: What is a circuit breaker pattern?

**Answer:**
Prevents cascading failures by failing fast when a service is down:

```java
class CircuitBreaker {
    private int failureCount = 0;
    private int threshold = 5;
    private State state = State.CLOSED;
    
    public void call() {
        if (state == State.OPEN) {
            throw new CircuitOpenException();
        }
        try {
            doCall();
            failureCount = 0;
        } catch (Exception e) {
            failureCount++;
            if (failureCount >= threshold) {
                state = State.OPEN;
            }
        }
    }
}
```

### Q5: How would you design a rate limiter?

**Answer:**
```
Approaches:
1. Token Bucket: Tokens added at fixed rate
2. Leaky Bucket: Fixed rate of processing
3. Sliding Window: Track requests per time window
4. Fixed Window: Simple but can burst at boundaries

Implementation:
- Redis with INCR + EXPIRE for distributed rate limiting
- In-memory for single server
- Consider per-IP and per-user limits
```

---

## 📋 Summary

| Concept | Key Takeaway |
|---------|-------------|
| Scalability | Horizontal vs Vertical |
| CAP Theorem | Consistency vs Availability trade-off |
| Load Balancing | Distribute traffic across servers |
| Caching | Reduce latency, improve performance |
| Message Queues | Decouple producers and consumers |
| Microservices | Independent deployable services |

---

## 🎯 Today's Exercise
Design a distributed counter system that can handle high concurrency.

**Expected Duration:** 45 minutes
**Difficulty:** ⭐⭐⭐☆☆

---

## 🔗 Additional Resources
- [System Design Primer](https://github.com/donnemartin/system-design-primer)
- [High Scalability Blog](http://highscalability.com/)

---
**"Good system design is about making the right trade-offs"** 🏗️

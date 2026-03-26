# Day 21: Advanced Concurrency Patterns 🔄

## 🎯 Learning Objectives
- Master Executor framework and thread pools
- Understand Futures and Callable
- Learn CompletableFuture for async programming
- Handle concurrent collections
- Build scalable concurrent applications

---

## 📚 Theory: Deep Dive

### 1. Executor Framework

The Executor framework provides a higher-level replacement for direct thread creation.

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    EXECUTOR FRAMEWORK ARCHITECTURE                      │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   ┌─────────────┐      ┌──────────────────┐      ┌──────────────┐  │
│   │   Task      │      │    Executor       │      │   Thread     │  │
│   │  (Runnable) │─────▶│    Service       │─────▶│    Pool      │  │
│   └─────────────┘      └──────────────────┘      └──────────────┘  │
│                                                                         │
│   ┌─────────────┐      ┌──────────────────┐      ┌──────────────┐  │
│   │   Task      │      │    Executor      │      │   Worker    │  │
│   │ (Callable)  │─────▶│    Service       │─────▶│   Threads   │  │
│   └─────────────┘      └──────────────────┘      └──────────────┘  │
│                              │                                         │
│                              ▼                                         │
│                    ┌──────────────────┐                               │
│                    │     Future       │                               │
│                    │  (Result Holder)│                               │
│                    └──────────────────┘                               │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 2. Thread Pool Types

```java
// Fixed Thread Pool - N threads, reuse
ExecutorService fixedPool = Executors.newFixedThreadPool(4);

// Cached Thread Pool - expands as needed
ExecutorService cachedPool = Executors.newCachedThreadPool();

// Single Thread - one thread at a time
ExecutorService singlePool = Executors.newSingleThreadExecutor();

// Scheduled Thread Pool - for scheduled tasks
ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(2);
```

### 3. Future and Callable

```java
// Callable - returns a value
Callable<Integer> task = () -> {
    return calculateSum(1, 100);
};

// Submit to executor
Future<Integer> future = executor.submit(task);

// Get result (blocks until available)
Integer result = future.get();

// Or with timeout
Integer result = future.get(5, TimeUnit.SECONDS);

// Check status
boolean done = future.isDone();
boolean cancelled = future.cancel(true);
```

### 4. CompletableFuture (Java 8+)

The modern way to handle async operations:

```java
// Simple async operation
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> "Hello")
    .thenApply(s -> s + " World")
    .thenApply(s -> s + "!");
    
String result = future.join();  // "Hello World!"

// Async with executor
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> fetchData(), executor)
    .thenApply(this::processData)
    .exceptionally(ex -> "Error: " + ex.getMessage());

// Combining multiple futures
CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> 10);
CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> 20);

CompletableFuture<Integer> combined = f1.thenCombine(f2, (a, b) -> a + b);
Integer result = combined.join();  // 30

// Run multiple futures and wait for all
CompletableFuture<Void> allFutures = CompletableFuture
    .allOf(f1, f2, f3);

// Run multiple futures and get first result
CompletableFuture<Object> anyFuture = CompletableFuture
    .anyOf(f1, f2, f3);
```

### 5. Concurrent Collections

Thread-safe replacements for standard collections:

```java
// Concurrent Map
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.putIfAbsent("key", 1);  // Atomic
map.computeIfAbsent("key", k -> compute());  // Atomic

// Concurrent List (Java 8+)
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

// Concurrent Queue
BlockingQueue<String> queue = new LinkedBlockingQueue<>(100);

// Concurrent Set
ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();

// Atomic variables
AtomicInteger counter = new AtomicInteger(0);
counter.incrementAndGet();
counter.compareAndSet(0, 1);
```

---

## 💡 Interview Questions & Answers

### Q1: What is the difference between submit() and execute()?

**Answer:**
| Method | Return Type | Exception Handling |
|--------|-------------|-------------------|
| submit() | Future | Wraps exceptions in ExecutionException |
| execute() | void | Propagates exceptions directly |

**Best Practice:** Always use `submit()` for better error handling.

### Q2: How would you implement a rate limiter?

**Answer:**
```java
class RateLimiter {
    private final Semaphore permits;
    private final long timeWindow;
    
    public RateLimiter(int permits, long timeWindow, TimeUnit unit) {
        this.permits = new Semaphore(permits);
        this.timeWindow = unit.toMillis(timeWindow);
    }
    
    public boolean tryAcquire() {
        return permits.tryAcquire();
    }
    
    public void acquire() throws InterruptedException {
        permits.acquire();
    }
}
```

### Q3: What is a thread pool? Why use it?

**Answer:**
A thread pool manages a group of worker threads for reuse. Benefits:
- **Reusability**: Threads are reused instead of created/destroyed
- **Resource control**: Limits thread count to prevent overload
- **Performance**: Avoids overhead of thread creation
- **Reliability**: Prevents creating too many threads (OOM)

### Q4: What is the difference between CyclicBarrier and CountDownLatch?

**Answer:**
| Aspect | CyclicBarrier | CountDownLatch |
|--------|---------------|----------------|
| **Reset** | Can be reused | Cannot be reused |
| **Direction** | All threads wait for each other | One thread waits for others |
| **Use case** | Parallel tasks sync | Waiting for completion |

### Q5: How does ForkJoinPool work?

**Answer:**
ForkJoinPool is designed for divide-and-conquer algorithms:
- Work stealing: Idle threads steal work from busy threads
- Efficient for recursive tasks
- Uses work-stealing deque

```java
ForkJoinPool pool = ForkJoinPool.commonPool();
ForkJoinTask<Integer> task = new RecursiveTask<Integer>() {
    protected Integer compute() {
        if (problemSize < threshold) {
            return solveDirectly();
        }
        // Fork
        ForkJoinTask<Integer> left = new SubTask().fork();
        ForkJoinTask<Integer> right = new SubTask().fork();
        // Join
        return left.join() + right.join();
    }
};
```

---

## 📋 Summary

| Concept | Key Takeaway |
|---------|-------------|
| ExecutorService | Thread pool management |
| Future | Async result handling |
| CompletableFuture | Chain async operations |
| Concurrent Collections | Thread-safe data structures |
| Atomic Variables | Lock-free thread safety |
| ForkJoinPool | Divide-and-conquer parallelism |

---

## 🎯 Today's Exercise
Implement an async order processing service using CompletableFuture.

**Expected Duration:** 50 minutes
**Difficulty:** ⭐⭐⭐⭐⭐

---

## 🔗 Additional Resources
- [CompletableFuture Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)
- [Java Concurrency Tutorial](https://docs.oracle.com/javase/tutorial/essential/concurrency/)

---
**"The key to concurrency is not threads, but the coordination of access to shared resources"** 🔄

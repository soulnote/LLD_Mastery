# Day 19: Concurrency Fundamentals 🧵

## 🎯 Learning Objectives
- Understand threads and processes
- Master thread creation and lifecycle
- Learn thread synchronization basics
- Handle race conditions and thread safety
- Build solid foundation for concurrent programming

---

## 📚 Theory: Deep Dive

### 1. What is Concurrency?

Concurrency is the ability of a system to handle multiple tasks simultaneously by overlapping their execution. In Java, this is achieved through multithreading.

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     CONCURRENT VS PARALLEL                             │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   CONCURRENT (Single Core)          PARALLEL (Multi Core)              │
│   =======================          =====================              │
│                                                                         │
│   Time ─────────────────►          Time ─────────────────►              │
│                                                                         │
│   ┌─────────┐                       ┌─────────┐ ┌─────────┐            │
│   │ Thread1 │░░░░░░░░░            │ Thread1 │ │ Thread2 │            │
│   └─────────┘                       └─────────┘ └─────────┘            │
│   ┌─────────┐                       ┌─────────┐ ┌─────────┐            │
│   │ Thread2 │░░░░░░░░░              │ Thread2 │ │ Thread1 │            │
│   └─────────┘                       └─────────┘ └─────────┘            │
│                                                                         │
│   - Context switching            - True simultaneous execution         │
│   - Efficient I/O handling       - CPU-bound tasks                      │
│   - Responsive UIs               - Performance optimization            │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 2. Thread Lifecycle

```
                    ┌──────────────────┐
                    │                  │
                    │    NEW           │
                    │  (Created)       │
                    │                  │
                    └────────┬─────────┘
                             │ start()
                             ▼
                    ┌──────────────────┐
                    │                  │
                    │  RUNNABLE        │
                    │  (Ready to run)  │
                    │        ┌────┐    │
                    │        │    │    │
                    └────────┤    ├────┘
                             │    │
                    ┌────────┤    ├────┐
                    │        ▼    ▼    │
                    │                  │
                    │   RUNNING        │
                    │  (Executing)    │
                    │                  │
                    └────────┬─────────┘
                             │ (wait/sleep/block)
                             ▼
                    ┌──────────────────┐
                    │                  │
                    │   BLOCKED        │
                    │  (Waiting)       │
                    │                  │
                    └────────┬─────────┘
                             │ (notify/interrupt)
                             ▼
                    ┌──────────────────┐
                    │                  │
                    │   TERMINATED     │
                    │   (Completed)    │
                    │                  │
                    └──────────────────┘
```

### 3. Thread Creation in Java

#### Method 1: Extending Thread Class

```java
class MyThread extends Thread {
    @Override
    public void run() {
        // Code to execute in new thread
        for (int i = 0; i < 5; i++) {
            System.out.println("Thread: " + i);
        }
    }
}

// Usage
MyThread thread = new MyThread();
thread.start();
```

#### Method 2: Implementing Runnable Interface

```java
class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Runnable: " + i);
        }
    }
}

// Usage
Thread thread = new Thread(new MyRunnable());
thread.start();

// Or with lambda (Java 8+)
new Thread(() -> {
    System.out.println("Lambda thread running");
}).start();
```

#### Method 3: Implementing Callable Interface

```java
class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= 10; i++) {
            sum += i;
        }
        return sum;  // Can return a value
    }
}

// Usage
ExecutorService executor = Executors.newSingleThreadExecutor();
Future<Integer> future = executor.submit(new MyCallable());
Integer result = future.get();  // Blocks until result is available
executor.shutdown();
```

### 4. Thread Synchronization

When multiple threads access shared resources, we need synchronization to prevent race conditions.

```
┌─────────────────────────────────────────────────────────────────┐
│                    RACE CONDITION                               │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│   Thread A                    Thread B                           │
│   ─────────                   ─────────                          │
│   read count (5)                                                    │
│                              read count (5)                      │
│   count = count + 1                                              │
│   write count (6)                                                │
│                              count = count + 1                   │
│                              write count (6)                     │
│                                                                 │
│   Expected: 7          Actual: 6     ← DATA CORRUPTION!        │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                  WITH SYNCHRONIZATION                           │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│   Thread A                    Thread B                           │
│   ─────────                   ─────────                          │
│   acquire lock                                                      │
│   read count (5)                                                   │
│   count = count + 1                                               │
│   write count (6)                                                  │
│   release lock                                                    │
│                              acquire lock                        │
│                              read count (6)                      │
│                              count = count + 1                    │
│                              write count (7)                      │
│                              release lock                        │
│                                                                 │
│   Expected: 7          Actual: 7     ✓ CORRECT                   │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

#### Synchronized Methods

```java
class Counter {
    private int count = 0;
    
    // Synchronized method - entire method is locked
    public synchronized void increment() {
        count++;
    }
    
    public synchronized int getCount() {
        return count;
    }
}
```

#### Synchronized Blocks

```java
class Counter {
    private int count = 0;
    private final Object lock = new Object();
    
    public void increment() {
        // Only this block is synchronized - more efficient
        synchronized (lock) {
            count++;
        }
    }
}
```

### 5. The volatile Keyword

The `volatile` keyword ensures visibility of changes across threads:

```java
class SharedData {
    // Without volatile: changes may not be visible to other threads
    // private int flag = 0;
    
    // With volatile: changes are immediately visible to all threads
    private volatile boolean flag = false;
    
    public void producer() {
        // Produce data
        flag = true;  // Will be immediately visible
    }
    
    public void consumer() {
        while (!flag) {
            // Will see the updated flag value
        }
        // Consume data
    }
}
```

**volatile vs synchronized:**
| Aspect | volatile | synchronized |
|--------|----------|---------------|
| Visibility | ✓ Yes | ✓ Yes |
| Atomicity | ✗ No | ✓ Yes |
| Performance | Faster | Slower |
| Use case | Flags, counters | Complex operations |

### 6. Inter-thread Communication

#### wait(), notify(), notifyAll()

```java
class SharedResource {
    private Object lock = new Object();
    private boolean dataReady = false;
    
    public void produce() {
        synchronized (lock) {
            // Do work...
            dataReady = true;
            lock.notify();  // Wake up waiting thread
        }
    }
    
    public void consume() {
        synchronized (lock) {
            while (!dataReady) {
                try {
                    lock.wait();  // Release lock and wait
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            // Consume data
        }
    }
}
```

### 7. Common Thread Methods

```java
// Thread control
thread.start();           // Start thread
thread.join();            // Wait for thread to complete
thread.join(1000);        // Wait with timeout
Thread.sleep(1000);       // Sleep for 1 second
thread.interrupt();       // Interrupt thread
thread.isInterrupted();   // Check if interrupted
Thread.interrupted();     // Clear and check interrupt

// Thread information
Thread.currentThread();   // Get current thread
thread.getName();        // Get thread name
thread.getId();          // Get thread ID
thread.getState();       // Get thread state
thread.isAlive();        // Check if thread is running

// Thread priority
thread.setPriority(Thread.MAX_PRIORITY);
thread.getPriority();
```

---

## 💡 Interview Questions & Answers

### Q1: What is the difference between Thread and Runnable?

**Answer:**
| Aspect | Thread | Runnable |
|--------|--------|----------|
| **Inheritance** | Can extend Thread (single inheritance limitation) | Implements Runnable (can extend other class) |
| **Resource sharing** | Each thread has its own resources | Multiple threads can share same Runnable |
| **Flexibility** | Less flexible | More flexible, preferred approach |
| **Use case** | Simple scenarios | Production code, thread pools |

**Best Practice:** Always prefer `Runnable` or `Callable` for better OOP design.

### Q2: What is a race condition?

**Answer:**
A race condition occurs when the outcome of a program depends on the timing of concurrent operations. It happens when:
1. Multiple threads access shared data
2. At least one thread modifies the data
3. No synchronization is applied

**Example:** Two threads incrementing a counter simultaneously may lose one increment.

### Q3: What is the difference between sleep() and wait()?

**Answer:**
| Aspect | sleep() | wait() |
|--------|---------|--------|
| **Lock** | Does NOT release lock | Releases lock |
| **Timing** | Wakes after timeout | Wakes via notify() |
| **Belongs to** | Thread class | Object class |
| **Use case** | Pausing execution | Inter-thread communication |

### Q4: How would you stop a thread in Java?

**Answer:**
There are several approaches:

1. **Using a flag (Recommended):**
```java
classstoppableTask implements Runnable {
    private volatile boolean running = true;
    
    public void stop() {
        running = false;
    }
    
    @Override
    public void run() {
        while (running) {
            // Do work
        }
    }
}
```

2. **Using InterruptedException:**
```java
public void run() {
    try {
        while (!Thread.interrupted()) {
            // Do work
        }
    } catch (InterruptedException e) {
        // Handle interrupt
    }
}
```

**Never use Thread.stop()** - it's deprecated and unsafe!

### Q5: What is thread starvation?

**Answer:**
Thread starvation occurs when a thread cannot get regular CPU time or access to resources because other threads are constantly busy. This can happen with:
- Low-priority threads
- Synchronized blocks held too long
- unfair lock implementations

**Solution:** Use `ReentrantLock` with fairness policy, minimize synchronized blocks.

---

## 🔧 Code Examples

### Example 1: Basic Thread Creation

```java
public class ThreadCreationDemo {
    public static void main(String[] args) {
        System.out.println("=== Thread Creation Demo ===\n");
        
        // Method 1: Extending Thread
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread 1 running");
            }
        };
        
        // Method 2: Implementing Runnable
        Runnable task = () -> System.out.println("Runnable task running");
        Thread thread2 = new Thread(task);
        
        // Method 3: Using ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> System.out.println("Executor task running"));
        
        thread1.start();
        thread2.start();
        
        executor.shutdown();
        System.out.println("Main thread continues...");
    }
}
```

### Example 2: Thread-Safe Counter

```java
class ThreadSafeCounter {
    private int count = 0;
    private final Object lock = new Object();
    
    public void increment() {
        synchronized (lock) {
            count++;
        }
    }
    
    public int getCount() {
        synchronized (lock) {
            return count;
        }
    }
}
```

---

## 📋 Summary

| Concept | Key Takeaway |
|---------|-------------|
| Threads | Lightweight processes for concurrent execution |
| Runnable vs Thread | Prefer Runnable for flexibility |
| Synchronization | Prevents race conditions |
| volatile | Ensures visibility across threads |
| wait/notify | Inter-thread communication |
| Thread lifecycle | NEW → RUNNABLE → RUNNING → BLOCKED → TERMINATED |

---

## 🎯 Today's Exercise
Implement a producer-consumer pattern with proper synchronization.

**Expected Duration:** 45 minutes
**Difficulty:** ⭐⭐⭐☆☆

---

## 🔗 Additional Resources
- [Java Concurrency Tutorial](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- [Understanding Thread Synchronization](https://www.baeldung.com/java-synchronized)

---
**"Concurrency is not about doing more things at once, it's about managing access to shared resources"** 🔄

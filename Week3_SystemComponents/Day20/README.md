# Day 20: Thread Synchronization & Locks 🔒

## 🎯 Learning Objectives
- Master synchronization primitives in Java
- Understand ReentrantLock and ReadWriteLock
- Learn deadlock prevention strategies
- Handle concurrent access to shared resources
- Build thread-safe applications

---

## 📚 Theory: Deep Dive

### 1. Why Synchronization?

When multiple threads access shared data simultaneously, we need to ensure data consistency and prevent race conditions.

```
┌─────────────────────────────────────────────────────────────────┐
│              SYNCHRONIZATION PROBLEM                            │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│   Thread A (Balance: $1000)          Thread B                   │
│   ───────────────────────           ───────────                  │
│   read balance: $1000                                          │
│   withdraw: $500                                                │
│                        read balance: $1000                      │
│                        withdraw: $300                           │
│   write balance: $500                                            │
│                        write balance: $700                      │
│                                                                 │
│   Expected: $200        Actual: $700   ← DATA INCONSISTENCY!  │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### 2. Synchronized Keyword

The `synchronized` keyword provides intrinsic locking:

```java
// Synchronized method - locks on 'this'
public synchronized void withdraw(double amount) {
    if (balance >= amount) {
        balance -= amount;
    }
}

// Synchronized block - locks on specified object
public void withdraw(double amount) {
    synchronized (this) {
        if (balance >= amount) {
            balance -= amount;
        }
    }
}

// Static synchronized - locks on Class object
public static synchronized void staticMethod() {
    // ...
}
```

### 3. ReentrantLock

`ReentrantLock` provides more flexibility than synchronized:

```java
class Counter {
    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();
    
    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();  // Always unlock in finally!
        }
    }
    
    public int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
    
    // Try lock with timeout
    public boolean tryIncrement() {
        if (lock.tryLock()) {
            try {
                count++;
                return true;
            } finally {
                lock.unlock();
            }
        }
        return false;
    }
}
```

### 4. ReadWriteLock

Optimized for read-heavy workloads:

```java
class Cache<K, V> {
    private final Map<K, V> cache = new HashMap<>();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();
    
    // Multiple readers can access simultaneously
    public V get(K key) {
        readLock.lock();
        try {
            return cache.get(key);
        } finally {
            readLock.unlock();
        }
    }
    
    // Only one writer at a time
    public void put(K key, V value) {
        writeLock.lock();
        try {
            cache.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }
}
```

### 5. Deadlock Prevention

A deadlock occurs when two or more threads are waiting for each other to release resources.

```
┌─────────────────────────────────────────────────────────────────┐
│                     DEADLOCK SCENARIO                           │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│   Thread A                    Thread B                           │
│   ─────────                   ─────────                          │
│   lock(resource1)                                               │
│                        lock(resource2)                          │
│   waiting for resource2...   waiting for resource1...          │
│                                                                 │
│   DEADLOCK! Both threads are stuck forever                     │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

#### Conditions for Deadlock (Coffman Conditions):
1. **Mutual Exclusion**: Only one thread can use a resource
2. **Hold and Wait**: Thread holds resources while waiting for others
3. **No Preemption**: Can't forcibly take resources
4. **Circular Wait**: Circular chain of waiting

#### Prevention Strategies:

```java
// Strategy 1: Lock Ordering (always acquire in same order)
class FixedOrderTransfer {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    
    public void transfer1() {
        synchronized (lock1) {
            synchronized (lock2) {
                // Do transfer
            }
        }
    }
    
    public void transfer2() {
        synchronized (lock1) {  // Same order as above!
            synchronized (lock2) {
                // Do transfer
            }
        }
    }
}

// Strategy 2: TryLock with timeout
class TryLockTransfer {
    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();
    
    public void transfer() {
        try {
            if (lock1.tryLock(1000, TimeUnit.MILLISECONDS)) {
                try {
                    if (lock2.tryLock(1000, TimeUnit.MILLISECONDS)) {
                        try {
                            // Do transfer
                        } finally {
                            lock2.unlock();
                        }
                    }
                } finally {
                    lock1.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

### 6. Condition Variables

Used for complex thread coordination:

```java
class BoundedBuffer<T> {
    private final Object[] items;
    private int count, in, out;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    
    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();  // Wait until not full
            }
            items[in] = item;
            in = (in + 1) % items.length;
            count++;
            notEmpty.signal();  // Notify consumers
        } finally {
            lock.unlock();
        }
    }
    
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();  // Wait until not empty
            }
            T item = items[out];
            out = (out + 1) % items.length;
            count--;
            notFull.signal();  // Notify producers
            return item;
        } finally {
            lock.unlock();
        }
    }
}
```

---

## 💡 Interview Questions & Answers

### Q1: What is the difference between synchronized and ReentrantLock?

**Answer:**
| Aspect | synchronized | ReentrantLock |
|--------|-------------|----------------|
| **Blocking** | Blocks until lock acquired | Can tryLock() with timeout |
| **Fairness** | No guaranteed fairness | Can set fair policy |
| **Multiple conditions** | Not supported | Multiple Condition variables |
| **Interruptibility** | Not interruptible | Can be interrupted |
| **Performance** | Optimized by JVM | Slightly overhead |

### Q2: What is a deadlock? How do you prevent it?

**Answer:**
A deadlock occurs when threads are blocked waiting for each other. Prevention strategies:
1. **Lock Ordering**: Always acquire locks in consistent order
2. **TryLock**: Use tryLock() with timeout instead of blocking
3. **Resource Hierarchy**: Assign hierarchy numbers to resources
4. **Deadlock Detection**: Use tools to detect and break deadlocks

### Q3: What is a race condition?

**Answer:**
A race condition occurs when the program's behavior depends on the timing of concurrent operations. It happens when multiple threads access and modify shared data without synchronization.

### Q4: What is the difference between wait() and sleep()?

**Answer:**
- `wait()`: Releases the lock, used for inter-thread communication
- `sleep()`: Does NOT release the lock, used for pausing execution

### Q5: What is a ThreadLocal variable?

**Answer:**
ThreadLocal provides thread-local variables - each thread gets its own copy:

```java
ThreadLocal<Date> dateThreadLocal = ThreadLocal.withInitial(Date::new);
Date date = dateThreadLocal.get();  // Each thread has its own Date
dateThreadLocal.remove();  // Clean up
```

---

## 📋 Summary

| Concept | Key Takeaway |
|---------|-------------|
| synchronized | Basic intrinsic locking |
| ReentrantLock | Flexible locking with tryLock |
| ReadWriteLock | Read-heavy optimization |
| Deadlock | Circular wait prevention |
| Condition | Complex thread coordination |
| ThreadLocal | Thread-specific storage |

---

## 🎯 Today's Exercise
Implement deadlock prevention techniques and thread-safe data structures.

**Expected Duration:** 45 minutes
**Difficulty:** ⭐⭐⭐⭐☆

---

## 🔗 Additional Resources
- [Java Concurrency in Practice](https://www.amazon.com/Java-Concurrency-Practice-Brian-Goetz/dp/0321349601)
- [ReentrantLock Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/ReentrantLock.html)

---
**"Synchronization is about coordinating actions; locks are about coordinating access"** 🔐

/**
 * Day 22 - Solution: Design a Distributed Counter System
 * 
 * This solution demonstrates a thread-safe distributed counter implementation
 * using various concurrency mechanisms in Java.
 */

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class Exercise1_Solution {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Distributed Counter Solution ===\n");
        
        // Test Solution 1: Using AtomicLong
        System.out.println("--- Solution 1: AtomicLong ---");
        testAtomicCounter();
        
        // Test Solution 2: Using ReentrantLock
        System.out.println("\n--- Solution 2: ReentrantLock ---");
        testLockCounter();
        
        // Test Solution 3: Synchronized method
        System.out.println("\n--- Solution 3: Synchronized ---");
        testSynchronizedCounter();
    }
    
    private static void testAtomicCounter() throws InterruptedException {
        AtomicCounter counter = new AtomicCounter("page-views");
        int numThreads = 100;
        int incrementsPerThread = 10;
        
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                }
            });
        }
        
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        
        long expected = (long) numThreads * incrementsPerThread;
        System.out.println("Expected: " + expected + ", Actual: " + counter.getValue());
        System.out.println("Test passed: " + (counter.getValue() == expected));
    }
    
    private static void testLockCounter() throws InterruptedException {
        LockCounter counter = new LockCounter("downloads");
        int numThreads = 50;
        int incrementsPerThread = 20;
        
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                }
            });
        }
        
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        
        long expected = (long) numThreads * incrementsPerThread;
        System.out.println("Expected: " + expected + ", Actual: " + counter.getValue());
        System.out.println("Test passed: " + (counter.getValue() == expected));
    }
    
    private static void testSynchronizedCounter() throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter("users-online");
        int numThreads = 50;
        int incrementsPerThread = 20;
        
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                }
            });
        }
        
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        
        long expected = (long) numThreads * incrementsPerThread;
        System.out.println("Expected: " + expected + ", Actual: " + counter.getValue());
        System.out.println("Test passed: " + (counter.getValue() == expected));
    }
}

// ============================================================
// SOLUTION 1: Using AtomicLong (Best for simple counters)
// ============================================================

class AtomicCounter {
    private final String name;
    private final AtomicLong count = new AtomicLong(0);
    
    public AtomicCounter(String name) {
        this.name = name;
    }
    
    public void increment() {
        count.incrementAndGet();
    }
    
    public void decrement() {
        count.decrementAndGet();
    }
    
    public long getValue() {
        return count.get();
    }
    
    public long getAndIncrement() {
        return count.getAndIncrement();
    }
    
    public long incrementAndGet() {
        return count.incrementAndGet();
    }
}

// ============================================================
// SOLUTION 2: Using ReentrantLock (Best for complex operations)
// ============================================================

class LockCounter {
    private final String name;
    private long count = 0;
    private final ReentrantLock lock = new ReentrantLock();
    
    public LockCounter(String name) {
        this.name = name;
    }
    
    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }
    
    public void decrement() {
        lock.lock();
        try {
            count--;
        } finally {
            lock.unlock();
        }
    }
    
    public long getValue() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}

// ============================================================
// SOLUTION 3: Using Synchronized (Simple and effective)
// ============================================================

class SynchronizedCounter {
    private final String name;
    private long count = 0;
    
    public SynchronizedCounter(String name) {
        this.name = name;
    }
    
    public synchronized void increment() {
        count++;
    }
    
    public synchronized void decrement() {
        count--;
    }
    
    public synchronized long getValue() {
        return count;
    }
}

// ============================================================
// BONUS: Distributed Counter Interface (for multi-server scenarios)
// ============================================================

/**
 * Interface for distributed counters that could work across multiple servers
 * using a shared data store like Redis or a database.
 */
interface DistributedCounterInterface {
    void increment();
    void decrement();
    long getValue();
    void reset();
}

/**
 * Example implementation that could be backed by Redis or database
 * In a real distributed system, this would use network calls to a 
 * centralized counter service.
 */
class DistributedCounterImpl implements DistributedCounterInterface {
    private final String counterName;
    private final String storageAddress;
    
    public DistributedCounterImpl(String name, String storageAddress) {
        this.counterName = name;
        this.storageAddress = storageAddress;
    }
    
    @Override
    public void increment() {
        // In real implementation: Redis INCR command
        // redis.incr(counterName);
        System.out.println("Distributed increment on: " + storageAddress);
    }
    
    @Override
    public void decrement() {
        // In real implementation: Redis DECR command
        // redis.decr(counterName);
        System.out.println("Distributed decrement on: " + storageAddress);
    }
    
    @Override
    public long getValue() {
        // In real implementation: Redis GET command
        // return redis.get(counterName);
        return 0;
    }
    
    @Override
    public void reset() {
        // In real implementation: Redis DEL command
        // redis.del(counterName);
        System.out.println("Distributed reset on: " + storageAddress);
    }
}

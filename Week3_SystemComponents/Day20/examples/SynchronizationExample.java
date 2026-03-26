/**
 * EXAMPLE: Thread Synchronization Mechanisms
 */

public class SynchronizationExample {
    
    /*
     * SYNCHRONIZATION PRIMITIVES IN JAVA
     * ==================================
     * 
     * 1. synchronized keyword
     *    - Intrinsic locks on objects
     *    - Can synchronize methods or blocks
     * 
     * 2. Lock Interface (java.util.concurrent.locks)
     *    - ReentrantLock
     *Lock
     *    - ReadWrite    - StampedLock
     * 
     * 3. volatile keyword
     *    - Ensures visibility across threads
     *    - No atomicity guarantees
     * 
     * 4. Atomic classes
     *    - AtomicInteger, AtomicLong, AtomicReference
     *    - Compare-and-swap operations
     */
    
    // Example 1: synchronized method
    static class Counter1 {
        private int count = 0;
        
        public synchronized void increment() {
            count++;
        }
        
        public synchronized int getCount() {
            return count;
        }
    }
    
    // Example 2: synchronized block
    static class Counter2 {
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
    
    // Example 3: ReentrantLock
    static class Counter3 {
        private int count = 0;
        private java.util.concurrent.locks.ReentrantLock lock = 
            new java.util.concurrent.locks.ReentrantLock();
        
        public void increment() {
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
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
    }
    
    // Example 4: ReadWriteLock
    static class Counter4 {
        private int count = 0;
        private java.util.concurrent.locks.ReadWriteLock rwLock = 
            new java.util.concurrent.locks.ReentrantReadWriteLock();
        
        public void increment() {
            rwLock.writeLock().lock();
            try {
                count++;
            } finally {
                rwLock.writeLock().unlock();
            }
        }
        
        public int getCount() {
            rwLock.readLock().lock();
            try {
                return count;
            } finally {
                rwLock.readLock().unlock();
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Thread Synchronization Examples ===");
        System.out.println("\n1. synchronized method");
        System.out.println("2. synchronized block");
        System.out.println("3. ReentrantLock");
        System.out.println("4. ReadWriteLock");
        System.out.println("\n✅ Synchronization concepts demonstrated!");
    }
}

/**
 * SOLUTION: Deadlock Prevention
 */

public class Exercise1_Solution {
    
    /*
     * SOLUTION 1: Lock Ordering (prevent circular wait)
     * Always acquire locks in a fixed order
     */
    static class FixedOrderSolution {
        private final Object lock1 = new Object();
        private final Object lock2 = new Object();
        
        public void methodA() {
            synchronized (lock1) {
                System.out.println("Thread A: Holding lock1...");
                try { Thread.sleep(100); } catch (Exception e) {}
                synchronized (lock2) {  // lock1 → lock2 (consistent order)
                    System.out.println("Thread A: Acquired lock2");
                }
            }
        }
        
        public void methodB() {
            synchronized (lock1) {  // Changed from lock2 to lock1
                System.out.println("Thread B: Holding lock1...");
                try { Thread.sleep(100); } catch (Exception e) {}
                synchronized (lock2) {  // lock1 → lock2 (consistent order)
                    System.out.println("Thread B: Acquired lock2");
                }
            }
        }
    }
    
    /*
     * SOLUTION 2: tryLock with Timeout
     * Try to acquire lock with timeout, rollback if failed
     */
    static class TryLockSolution {
        private java.util.concurrent.locks.ReentrantLock lock1 = new java.util.concurrent.locks.ReentrantLock();
        private java.util.concurrent.locks.ReentrantLock lock2 = new java.util.concurrent.locks.ReentrantLock();
        
        public void methodWithTryLock() {
            boolean lock1Acquired = false;
            boolean lock2Acquired = false;
            
            try {
                lock1Acquired = lock1.tryLock(1000, java.util.concurrent.TimeUnit.MILLISECONDS);
                if (lock1Acquired) {
                    System.out.println("Thread: Acquired lock1");
                    Thread.sleep(100);
                    
                    lock2Acquired = lock2.tryLock(1000, java.util.concurrent.TimeUnit.MILLISECONDS);
                    if (lock2Acquired) {
                        System.out.println("Thread: Acquired lock2");
                    } else {
                        System.out.println("Thread: Failed to acquire lock2, rolling back");
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if (lock2Acquired) lock2.unlock();
                if (lock1Acquired) lock1.unlock();
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("📝 Deadlock Prevention - SOLUTION\n");
        
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║              DEADLOCK PREVENTION STRATEGIES                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("DEADLOCK CONDITIONS (all must be present):");
        System.out.println("1. Mutual Exclusion - Only one thread can hold a resource");
        System.out.println("2. Hold and Wait - Thread holds resources while waiting for others");
        System.out.println("3. No Preemption - Can't forcibly take resources");
        System.out.println("4. Circular Wait - Thread A waits for B, B waits for A\n");
        
        System.out.println("PREVENTION STRATEGIES:");
        System.out.println("----------------------");
        System.out.println("1. Lock Ordering: Always acquire locks in same order");
        System.out.println("2. tryLock with timeout: Don't wait indefinitely");
        System.out.println("3. Single lock: Use one lock instead of multiple");
        System.out.println("4. Lock hierarchy: Enforce lock acquisition order\n");
        
        // Test fixed order solution
        System.out.println("Testing Fixed Order Solution:");
        FixedOrderSolution fixed = new FixedOrderSolution();
        
        Thread t1 = new Thread(() -> fixed.methodA());
        Thread t2 = new Thread(() -> fixed.methodB());
        
        t1.start();
        t2.start();
        
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n✅ Deadlock prevented!");
    }
}

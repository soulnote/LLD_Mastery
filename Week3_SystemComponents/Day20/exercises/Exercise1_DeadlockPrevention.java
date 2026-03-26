/**
 * EXERCISE 1: Deadlock Prevention
 * 
 * TASK: Identify and fix deadlock scenarios in code
 * 
 * Requirements:
 * 1. Understand deadlock conditions (mutual exclusion, hold and wait, no preemption, circular wait)
 * 2. Identify deadlock-prone code patterns
 * 3. Implement deadlock prevention strategies
 * 4. Use tryLock() with timeout
 * 
 * DIFFICULTY: ⭐⭐⭐⭐☆ (SDE3-Level)
 * TIME: 45 minutes
 */

public class Exercise1_DeadlockPrevention {
    
    /*
     * DEADLOCK SCENARIO - These two threads cause deadlock
     */
    static class DeadlockExample {
        private final Object lock1 = new Object();
        private final Object lock2 = new Object();
        
        public void methodA() {
            synchronized (lock1) {
                System.out.println("Thread A: Holding lock1...");
                try { Thread.sleep(100); } catch (Exception e) {}
                synchronized (lock2) {
                    System.out.println("Thread A: Acquired lock2");
                }
            }
        }
        
        public void methodB() {
            synchronized (lock2) {
                System.out.println("Thread B: Holding lock2...");
                try { Thread.sleep(100); } catch (Exception e) {}
                synchronized (lock1) {
                    System.out.println("Thread B: Acquired lock1");
                }
            }
        }
    }
    
    // TODO: Fix the deadlock by reordering locks or using tryLock
    
    public static void main(String[] args) {
        System.out.println("📝 Exercise 1: Deadlock Prevention\n");
        
        DeadlockExample example = new DeadlockExample();
        
        // Thread 1
        new Thread(() -> example.methodA()).start();
        
        // Thread 2
        new Thread(() -> example.methodB()).start();
        
        System.out.println("\n⚠️ This will cause deadlock!");
        System.out.println("Fix it using:");
        System.out.println("1. Lock ordering (always acquire lock1 before lock2)");
        System.out.println("2. tryLock() with timeout");
        System.out.println("3. Single lock instead of multiple locks");
    }
}

/**
 * Day 22 - Exercise: Design a Distributed Counter System
 * 
 * Design a distributed counter that can handle high concurrency across multiple servers.
 * The counter should be able to:
 * 1. Increment and decrement atomically
 * 2. Handle concurrent requests from multiple threads
 * 3. Be scalable across multiple servers
 * 4. Provide accurate count values
 * 
 * Expected Duration: 45 minutes
 * Difficulty: ⭐⭐⭐☆☆
 */

public class Exercise1_DistributedCounter {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Distributed Counter Exercise ===");
        
        // TODO: Implement your solution here
        // Create a distributed counter that handles concurrent increments
        
        System.out.println("\nExercise: Design a distributed counter system");
        System.out.println("Requirements:");
        System.out.println("1. Implement atomic increment/decrement operations");
        System.out.println("2. Handle concurrent requests from multiple threads");
        System.out.println("3. Support horizontal scaling (multiple counter instances)");
        System.out.println("4. Provide thread-safe operations");
        System.out.println("5. Handle potential race conditions");
        
        // Example test case structure:
        // DistributedCounter counter = new DistributedCounter("page-views");
        // 
        // // Concurrent increment test
        // for (int i = 0; i < 100; i++) {
        //     new Thread(() -> counter.increment()).start();
        // }
        // 
        // // Should print approximately 100
        // System.out.println("Final count: " + counter.getValue());
    }
}

// TODO: Implement the DistributedCounter class with the following features:
// 1. Thread-safe increment() method
// 2. Thread-safe decrement() method  
// 3. Thread-safe getValue() method
// 4. Support for named counters
// 5. Optional: Implement distributed locking mechanism

class DistributedCounter {
    private String name;
    // TODO: Add your implementation here
    
    public DistributedCounter(String name) {
        this.name = name;
    }
    
    public void increment() {
        // TODO: Implement atomic increment
    }
    
    public void decrement() {
        // TODO: Implement atomic decrement
    }
    
    public long getValue() {
        // TODO: Return current counter value
        return 0;
    }
}

/**
 * EXERCISE 1: Producer-Consumer Problem
 * 
 * TASK: Implement a thread-safe producer-consumer pattern
 * 
 * Requirements:
 * 1. Create a shared buffer with limited capacity
 * 2. Implement producer threads that add items to buffer
 * 3. Implement consumer threads that remove items
 * 4. Use proper synchronization (wait/notify or BlockingQueue)
 * 5. Handle edge cases (buffer full, buffer empty)
 * 
 * DIFFICULTY: ⭐⭐⭐☆☆ (SDE2-Level)
 * TIME: 40 minutes
 */

public class Exercise1_ProducerConsumer {
    
    // Shared buffer - implement this
    static class SharedBuffer {
        // TODO: Implement thread-safe buffer
        // - Use synchronized methods or locks
        // - Handle wait/notify for blocking behavior
    }
    
    // Producer thread
    static class Producer extends Thread {
        private SharedBuffer buffer;
        private int itemsToProduce;
        
        public Producer(SharedBuffer buffer, int itemsToProduce) {
            this.buffer = buffer;
            this.itemsToProduce = itemsToProduce;
        }
        
        public void run() {
            // TODO: Produce items and add to buffer
        }
    }
    
    // Consumer thread
    static class Consumer extends Thread {
        private SharedBuffer buffer;
        private int itemsToConsume;
        
        public Consumer(SharedBuffer buffer, int itemsToConsume) {
            this.buffer = buffer;
            this.itemsToConsume = itemsToConsume;
        }
        
        public void run() {
            // TODO: Consume items from buffer
        }
    }
    
    public static void main(String[] args) {
        System.out.println("📝 Exercise 1: Producer-Consumer Problem\n");
        
        SharedBuffer buffer = new SharedBuffer();
        
        // TODO: Create and start producer and consumer threads
        // Producer produces 10 items
        // Consumer consumes 10 items
        
        System.out.println("✅ Implement the producer-consumer pattern!");
    }
}

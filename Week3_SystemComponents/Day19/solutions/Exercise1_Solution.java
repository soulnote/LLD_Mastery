/**
 * SOLUTION: Producer-Consumer Problem
 * 
 * This solution demonstrates a production-ready implementation of the 
 * producer-consumer pattern using wait/notify synchronization.
 * 
 * @author LLD Mastery
 * @version 1.0
 */

public class Exercise1_Solution {
    
    // ============================================================
    // Thread-Safe Shared Buffer using wait/notify
    // ============================================================
    static class SharedBuffer {
        private final int capacity;
        private final java.util.Queue<Integer> buffer;
        
        public SharedBuffer(int capacity) {
            this.capacity = capacity;
            this.buffer = new java.util.LinkedList<>();
        }
        
        /**
         * Producer adds item to buffer
         * Blocks if buffer is full
         */
        public synchronized void produce(int item) throws InterruptedException {
            // Wait while buffer is full
            while (buffer.size() >= capacity) {
                System.out.println("Buffer full! Producer waiting...");
                wait();
            }
            
            // Add item to buffer
            buffer.add(item);
            System.out.println("Producer produced: " + item + 
                " | Buffer size: " + buffer.size());
            
            // Notify consumer that item is available
            notifyAll();
        }
        
        /**
         * Consumer removes item from buffer
         * Blocks if buffer is empty
         */
        public synchronized int consume() throws InterruptedException {
            // Wait while buffer is empty
            while (buffer.isEmpty()) {
                System.out.println("Buffer empty! Consumer waiting...");
                wait();
            }
            
            // Remove item from buffer
            int item = buffer.poll();
            System.out.println("Consumer consumed: " + item + 
                " | Buffer size: " + buffer.size());
            
            // Notify producer that space is available
            notifyAll();
            
            return item;
        }
        
        /**
         * Check current buffer size
         */
        public synchronized int size() {
            return buffer.size();
        }
    }
    
    // ============================================================
    // Producer Thread
    // ============================================================
    static class Producer extends Thread {
        private final SharedBuffer buffer;
        private final int itemsToProduce;
        private final int productionDelay;
        
        public Producer(SharedBuffer buffer, int itemsToProduce, int productionDelay) {
            super("Producer-" + itemsToProduce);
            this.buffer = buffer;
            this.itemsToProduce = itemsToProduce;
            this.productionDelay = productionDelay;
        }
        
        @Override
        public void run() {
            try {
                for (int i = 1; i <= itemsToProduce; i++) {
                    buffer.produce(i);
                    
                    // Simulate production time
                    if (productionDelay > 0) {
                        Thread.sleep(productionDelay);
                    }
                }
                System.out.println(getName() + " finished producing all items!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(getName() + " was interrupted!");
            }
        }
    }
    
    // ============================================================
    // Consumer Thread
    // ============================================================
    static class Consumer extends Thread {
        private final SharedBuffer buffer;
        private final int itemsToConsume;
        private final int consumptionDelay;
        
        public Consumer(SharedBuffer buffer, int itemsToConsume, int consumptionDelay) {
            super("Consumer-" + itemsToConsume);
            this.buffer = buffer;
            this.itemsToConsume = itemsToConsume;
            this.consumptionDelay = consumptionDelay;
        }
        
        @Override
        public void run() {
            try {
                for (int i = 0; i < itemsToConsume; i++) {
                    buffer.consume();
                    
                    // Simulate consumption time
                    if (consumptionDelay > 0) {
                        Thread.sleep(consumptionDelay);
                    }
                }
                System.out.println(getName() + " finished consuming all items!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(getName() + " was interrupted!");
            }
        }
    }
    
    // ============================================================
    // Main Method
    // ============================================================
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║          PRODUCER-CONSUMER SOLUTION DEMO                           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝\n");
        
        // Configuration
        final int BUFFER_CAPACITY = 5;
        final int TOTAL_ITEMS = 10;
        final int PRODUCER_DELAY = 100;  // ms
        final int CONSUMER_DELAY = 150;   // ms
        
        // Create shared buffer
        SharedBuffer buffer = new SharedBuffer(BUFFER_CAPACITY);
        
        System.out.println("Configuration:");
        System.out.println("  - Buffer Capacity: " + BUFFER_CAPACITY);
        System.out.println("  - Total Items: " + TOTAL_ITEMS);
        System.out.println("  - Producer Delay: " + PRODUCER_DELAY + "ms");
        System.out.println("  - Consumer Delay: " + CONSUMER_DELAY + "ms\n");
        
        // Create producer and consumer threads
        Producer producer = new Producer(buffer, TOTAL_ITEMS, PRODUCER_DELAY);
        Consumer consumer = new Consumer(buffer, TOTAL_ITEMS, CONSUMER_DELAY);
        
        // Track execution time
        long startTime = System.currentTimeMillis();
        
        // Start threads
        System.out.println("Starting Producer and Consumer...\n");
        producer.start();
        consumer.start();
        
        // Wait for both threads to complete
        producer.join();
        consumer.join();
        
        long endTime = System.currentTimeMillis();
        
        // Results
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                          RESULTS                                  ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");
        
        System.out.println("Execution completed successfully!");
        System.out.println("Total time: " + (endTime - startTime) + "ms");
        
        // Verify final state
        System.out.println("\nFinal buffer size: " + buffer.size());
        
        System.out.println("\n✅ Producer-Consumer pattern demonstrated with proper synchronization!");
    }
}

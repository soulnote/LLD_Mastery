/**
 * EXERCISE 1: Design Async Order Processing Service
 * 
 * TASK: Implement an asynchronous order processing system using ExecutorService
 * 
 * Requirements:
 * 1. Use ExecutorService for async task execution
 * 2. Process order validation, payment, and shipping asynchronously
 * 3. Use CompletableFuture for chaining operations
 * 4. Handle exceptions gracefully
 * 5. Implement proper thread pool management
 * 
 * DIFFICULTY: ⭐⭐⭐⭐⭐ (SDE3-Level)
 * TIME: 50 minutes
 */

public class Exercise1_AsyncService {
    
    // TODO: Implement async order processing service
    // Steps:
    // 1. Validate order
    // 2. Process payment
    // 3. Update inventory
    // 4. Schedule shipping
    // 5. Send notification
    
    static class Order {
        String orderId;
        String customerId;
        double amount;
        String status;
    }
    
    public static void main(String[] args) {
        System.out.println("📝 Exercise 1: Async Order Processing\n");
        
        // TODO: Implement the async order processing
        // Use ExecutorService and CompletableFuture
        
        System.out.println("Implement async order processing with:");
        System.out.println("1. ExecutorService thread pool");
        System.out.println("2. CompletableFuture for chaining");
        System.out.println("3. Error handling");
        System.out.println("4. Callback handling");
        
        System.out.println("\n✅ Complete the implementation!");
    }
}

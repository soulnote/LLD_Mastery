/**
 * EXERCISE 2: Order Service with Dependency Injection
 * 
 * TASK: Apply DIP with dependency injection
 * 
 * Requirements:
 * 1. Create PaymentProcessor interface
 * 2. Create NotificationService interface  
 * 3. Create InventoryService interface
 * 4. Implement concrete classes (CreditCardPayment, EmailNotification, etc.)
 * 5. Create OrderService that uses dependency injection
 * 6. Demonstrate switching implementations without modifying OrderService
 * 
 * DIFFICULTY: ⭐⭐⭐☆☆
 * TIME: 30 minutes
 */

public class Exercise2_OrderService {
    
    // TODO: Create interfaces
    // - PaymentProcessor: processPayment(amount)
    // - NotificationService: sendNotification(message)
    // - InventoryService: checkStock(item), reduceStock(item, quantity)
    
    // TODO: Implement concrete classes
    // - CreditCardPayment implements PaymentProcessor
    // - PayPalPayment implements PaymentProcessor
    // - EmailNotification implements NotificationService
    // - SMSNotification implements NotificationService
    // - InMemoryInventory implements InventoryService
    
    // TODO: Create OrderService with constructor injection
    // - Should accept PaymentProcessor, NotificationService, InventoryService
    // - processOrder method should use all three
    
    public static void main(String[] args) {
        System.out.println("📦 Exercise 2: Order Service with DI\n");
        
        // TODO: Test your implementation
        // 1. Create different implementations
        // 2. Inject them into OrderService
        // 3. Process orders
        // 4. Try switching implementations
        
        System.out.println("✅ Complete the implementation!");
    }
}

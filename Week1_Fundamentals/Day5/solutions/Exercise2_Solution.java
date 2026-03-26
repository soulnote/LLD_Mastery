/**
 * SOLUTION: Order Service with Dependency Injection
 * Demonstrates Dependency Inversion Principle (DIP)
 */

// ✅ DIP: Abstractions (interfaces)

interface PaymentProcessor {
    void processPayment(double amount);
    String getPaymentType();
}

interface NotificationService {
    void sendNotification(String message);
    String getNotificationType();
}

interface InventoryService {
    boolean checkStock(String item);
    void reduceStock(String item, int quantity);
}

// Payment implementations
class CreditCardPayment implements PaymentProcessor {
    private String cardNumber;
    
    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    @Override
    public void processPayment(double amount) {
        System.out.println("💳 [Credit Card] Processing: $" + amount);
        System.out.println("   Card: ****" + cardNumber.substring(cardNumber.length() - 4));
    }
    
    @Override
    public String getPaymentType() {
        return "Credit Card";
    }
}

class PayPalPayment implements PaymentProcessor {
    private String email;
    
    public PayPalPayment(String email) {
        this.email = email;
    }
    
    @Override
    public void processPayment(double amount) {
        System.out.println("🅿️ [PayPal] Processing: $" + amount);
        System.out.println("   Account: " + email);
    }
    
    @Override
    public String getPaymentType() {
        return "PayPal";
    }
}

// Notification implementations
class EmailNotification implements NotificationService {
    private String email;
    
    public EmailNotification(String email) {
        this.email = email;
    }
    
    @Override
    public void sendNotification(String message) {
        System.out.println("📧 [Email] Sending to: " + email);
        System.out.println("   Message: " + message);
    }
    
    @Override
    public String getNotificationType() {
        return "Email";
    }
}

class SMSNotification implements NotificationService {
    private String phoneNumber;
    
    public SMSNotification(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public void sendNotification(String message) {
        System.out.println("📱 [SMS] Sending to: " + phoneNumber);
        System.out.println("   Message: " + message);
    }
    
    @Override
    public String getNotificationType() {
        return "SMS";
    }
}

// Inventory implementation
class InMemoryInventory implements InventoryService {
    private java.util.Map<String, Integer> stock = new java.util.HashMap<>();
    
    public InMemoryInventory() {
        stock.put("Laptop", 10);
        stock.put("Mouse", 50);
        stock.put("Keyboard", 30);
    }
    
    @Override
    public boolean checkStock(String item) {
        return stock.getOrDefault(item, 0) > 0;
    }
    
    @Override
    public void reduceStock(String item, int quantity) {
        int current = stock.getOrDefault(item, 0);
        stock.put(item, current - quantity);
        System.out.println("📦 Stock reduced: " + item + " -> " + (current - quantity));
    }
    
    public void addStock(String item, int quantity) {
        stock.put(item, stock.getOrDefault(item, 0) + quantity);
    }
}

// ✅ DIP: High-level module depends on abstractions
class OrderService {
    private PaymentProcessor paymentProcessor;
    private NotificationService notificationService;
    private InventoryService inventoryService;
    
    // ✅ Constructor Injection - Dependency Inversion!
    public OrderService(PaymentProcessor paymentProcessor,
                       NotificationService notificationService,
                       InventoryService inventoryService) {
        this.paymentProcessor = paymentProcessor;
        this.notificationService = notificationService;
        this.inventoryService = inventoryService;
    }
    
    public void processOrder(String item, int quantity, double price) {
        System.out.println("\n📦 Processing order for: " + quantity + "x " + item);
        
        // Check inventory
        if (!inventoryService.checkStock(item)) {
            System.out.println("❌ Item out of stock!");
            return;
        }
        
        // Process payment
        double total = price * quantity;
        paymentProcessor.processPayment(total);
        
        // Reduce inventory
        inventoryService.reduceStock(item, quantity);
        
        // Send notification
        notificationService.sendNotification("Order processed: " + item + " x" + quantity);
        
        System.out.println("✅ Order complete!");
    }
    
    // Allow switching implementations at runtime
    public void setPaymentProcessor(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }
    
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}

public class Exercise2_Solution {
    public static void main(String[] args) {
        System.out.println("📦 Order Service with DI - SOLUTION\n");
        
        // Create dependencies
        InventoryService inventory = new InMemoryInventory();
        
        // Use Credit Card + Email
        System.out.println("=== Configuration 1: Credit Card + Email ===");
        PaymentProcessor creditCard = new CreditCardPayment("4111111111111111");
        NotificationService email = new EmailNotification("customer@example.com");
        
        OrderService orderService = new OrderService(creditCard, email, inventory);
        orderService.processOrder("Laptop", 2, 999.99);
        
        // Switch to PayPal + SMS - NO CHANGE to OrderService!
        System.out.println("\n=== Configuration 2: PayPal + SMS ===");
        PaymentProcessor paypal = new PayPalPayment("customer@paypal.com");
        NotificationService sms = new SMSNotification("+1-555-1234");
        
        OrderService orderService2 = new OrderService(paypal, sms, inventory);
        orderService2.processOrder("Mouse", 5, 29.99);
        
        // Demonstrate runtime switching
        System.out.println("\n=== Runtime Switching ===");
        OrderService orderService3 = new OrderService(creditCard, email, inventory);
        orderService3.processOrder("Keyboard", 1, 79.99);
        
        // Switch payment at runtime
        orderService3.setPaymentProcessor(paypal);
        orderService3.processOrder("Mouse", 3, 29.99);
        
        System.out.println("\n=== DIP Benefits ===");
        System.out.println("✅ OrderService depends on abstractions (interfaces)");
        System.out.println("✅ Dependencies are injected from outside");
        System.out.println("✅ Easy to swap implementations");
        System.out.println("✅ Easy to test with mocks");
        System.out.println("✅ No modification needed to OrderService when adding new payment types!");
        
        System.out.println("\n🎉 DIP achieved! High-level module doesn't depend on low-level modules!");
    }
}

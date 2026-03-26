/**
 * EXAMPLE: Open/Closed Principle Violation
 * 
 * This code VIOLATES OCP because adding new payment methods
 * requires modifying the PaymentProcessor class
 */

public class OCPViolation {
    
    // ❌ VIOLATION: This class must be modified for each new payment type
    static class PaymentProcessor {
        
        public void processPayment(String paymentType, double amount) {
            // Every time we add a new payment type, we must modify this method!
            if (paymentType.equals("CREDIT_CARD")) {
                System.out.println("Processing credit card payment: $" + amount);
                // Credit card processing logic
            } else if (paymentType.equals("PAYPAL")) {
                System.out.println("Processing PayPal payment: $" + amount);
                // PayPal processing logic
            } else if (paymentType.equals("BANK_TRANSFER")) {
                System.out.println("Processing bank transfer: $" + amount);
                // Bank transfer logic
            }
            // TODO: Add more payment types here... BAD!
            else {
                throw new IllegalArgumentException("Unknown payment type: " + paymentType);
            }
        }
    }
    
    // Test the violation
    public static void main(String[] args) {
        System.out.println("❌ OCP Violation Demo\n");
        
        PaymentProcessor processor = new PaymentProcessor();
        
        // Existing payment types work
        processor.processPayment("CREDIT_CARD", 100.0);
        processor.processPayment("PAYPAL", 50.0);
        
        // To add CRYPTOCURRENCY, we must modify PaymentProcessor!
        // This violates OCP - code should be closed for modification
        System.out.println("\n⚠️ Adding new payment type requires modifying PaymentProcessor!");
        
        // What if we want to add crypto? We'd need to add another if-else!
        // processor.processPayment("CRYPTOCURRENCY", 0.5);
    }
}

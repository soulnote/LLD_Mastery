/**
 * EXERCISE 1: Extensible Payment System
 * 
 * TASK: Create a payment system that follows OCP
 * 
 * Requirements:
 * 1. Create a PaymentMethod interface
 * 2. Implement CreditCard, PayPal, BankTransfer classes
 * 3. Create a PaymentProcessor that can process any payment method
 * 4. Add a new CryptoPayment class WITHOUT modifying PaymentProcessor
 * 5. Demonstrate polymorphism with different payment types
 * 
 * DIFFICULTY: ⭐⭐☆☆☆
 * TIME: 25 minutes
 */

public class Exercise1_PaymentSystem {
    
    // TODO: Create PaymentMethod interface
    // Should have:
    // - processPayment(double amount)
    // - getPaymentType()
    
    // TODO: Implement CreditCardPayment
    // Should have card number, expiry, CVV
    
    // TODO: Implement PayPalPayment
    // Should have email
    
    // TODO: Implement BankTransferPayment
    // Should have account number, routing number
    
    // TODO: Implement CryptoPayment (NEW - without modifying existing code!)
    // Should have wallet address, crypto type (BTC, ETH, etc.)
    
    // TODO: Create PaymentProcessor
    // Should have processPayment(PaymentMethod, double)
    // Should have processBatch(PaymentMethod[], double)
    
    public static void main(String[] args) {
        System.out.println("💳 Exercise 1: Extensible Payment System\n");
        
        // TODO: Test your implementation
        // 1. Create different payment methods
        // 2. Process payments using PaymentProcessor
        // 3. Add CryptoPayment and process - no modification needed!
        
        System.out.println("✅ Complete the implementation!");
    }
}

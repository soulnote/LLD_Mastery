/**
 * SOLUTION: Extensible Payment System
 * Demonstrates Open/Closed Principle (OCP)
 */

// ✅ OCP: Interface defines contract, implementation can be added
interface PaymentMethod {
    void processPayment(double amount);
    String getPaymentType();
}

// Credit Card implementation
class CreditCardPayment implements PaymentMethod {
    private String cardNumber;
    private String expiry;
    private String cvv;
    
    public CreditCardPayment(String cardNumber, String expiry, String cvv) {
        this.cardNumber = cardNumber;
        this.expiry = expiry;
        this.cvv = cvv;
    }
    
    @Override
    public void processPayment(double amount) {
        System.out.println("💳 [Credit Card] Processing: $" + amount);
        System.out.println("   Card: ****" + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("   ✅ Payment approved!");
    }
    
    @Override
    public String getPaymentType() {
        return "Credit Card";
    }
}

// PayPal implementation
class PayPalPayment implements PaymentMethod {
    private String email;
    
    public PayPalPayment(String email) {
        this.email = email;
    }
    
    @Override
    public void processPayment(double amount) {
        System.out.println("🅿️ [PayPal] Processing: $" + amount);
        System.out.println("   Account: " + email);
        System.out.println("   ✅ Payment approved!");
    }
    
    @Override
    public String getPaymentType() {
        return "PayPal";
    }
}

// Bank Transfer implementation
class BankTransferPayment implements PaymentMethod {
    private String accountNumber;
    private String routingNumber;
    
    public BankTransferPayment(String accountNumber, String routingNumber) {
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
    }
    
    @Override
    public void processPayment(double amount) {
        System.out.println("🏦 [Bank Transfer] Processing: $" + amount);
        System.out.println("   Account: ****" + accountNumber.substring(accountNumber.length() - 4));
        System.out.println("   ✅ Transfer initiated!");
    }
    
    @Override
    public String getPaymentType() {
        return "Bank Transfer";
    }
}

// ✅ NEW: Crypto implementation - ADDED WITHOUT MODIFYING EXISTING CODE!
class CryptoPayment implements PaymentMethod {
    private String walletAddress;
    private String cryptoType;
    
    public CryptoPayment(String walletAddress, String cryptoType) {
        this.walletAddress = walletAddress;
        this.cryptoType = cryptoType;
    }
    
    @Override
    public void processPayment(double amount) {
        System.out.println("🪙 [Crypto] Processing: $" + amount);
        System.out.println("   Type: " + cryptoType);
        System.out.println("   Wallet: " + walletAddress.substring(0, 8) + "...");
        System.out.println("   ✅ Transaction confirmed!");
    }
    
    @Override
    public String getPaymentType() {
        return "Cryptocurrency";
    }
}

// ✅ OCP: PaymentProcessor is CLOSED for modification
class PaymentProcessor {
    
    // Works with ANY PaymentMethod - no need to modify for new types!
    public void processPayment(PaymentMethod payment, double amount) {
        System.out.println("\n💰 Processing payment...");
        payment.processPayment(amount);
    }
    
    // Process multiple payments
    public void processBatch(PaymentMethod[] payments, double amount) {
        System.out.println("\n📦 Processing batch payments...");
        for (PaymentMethod payment : payments) {
            payment.processPayment(amount);
        }
    }
}

public class Exercise1_Solution {
    public static void main(String[] args) {
        System.out.println("💳 Extensible Payment System - SOLUTION\n");
        
        PaymentProcessor processor = new PaymentProcessor();
        
        // Existing payment methods
        PaymentMethod creditCard = new CreditCardPayment("4111111111111111", "12/25", "123");
        PaymentMethod paypal = new PayPalPayment("user@email.com");
        
        processor.processPayment(creditCard, 100.0);
        processor.processPayment(paypal, 50.0);
        
        // ✅ NEW: Add Crypto - NO modification to PaymentProcessor!
        PaymentMethod crypto = new CryptoPayment("0x1234567890abcdef1234567890abcdef", "ETH");
        processor.processPayment(crypto, 0.5);
        
        // Batch processing with all types
        System.out.println("\n=== Batch Processing ===");
        PaymentMethod[] allPayments = {
            creditCard,
            paypal,
            crypto,
            new BankTransferPayment("123456789", "021000021")
        };
        processor.processBatch(allPayments, 75.0);
        
        System.out.println("\n🎉 OCP achieved! New payment types can be added without modifying existing code!");
    }
}

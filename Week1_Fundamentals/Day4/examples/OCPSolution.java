/**
 * EXAMPLE: Open/Closed Principle Solution
 * 
 * This code FOLLOWS OCP by using interfaces and polymorphism.
 * New payment types can be added without modifying existing code!
 */

// ✅ SOLUTION: Define a contract for all payment methods
interface PaymentMethod {
    void pay(double amount);
    String getName();
}

// Credit Card payment - can be added without modifying PaymentProcessor
class CreditCardPayment implements PaymentMethod {
    private String cardNumber;
    private String cvv;
    
    public CreditCardPayment(String cardNumber, String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("💳 [CreditCard] Processing payment: $" + amount);
        System.out.println("   Card: ****" + cardNumber.substring(cardNumber.length() - 4));
    }
    
    @Override
    public String getName() {
        return "Credit Card";
    }
}

// PayPal payment - new payment type, no modification needed!
class PayPalPayment implements PaymentMethod {
    private String email;
    
    public PayPalPayment(String email) {
        this.email = email;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("🅿️ [PayPal] Processing payment: $" + amount);
        System.out.println("   Account: " + email);
    }
    
    @Override
    public String getName() {
        return "PayPal";
    }
}

// Bank Transfer payment - another new type!
class BankTransferPayment implements PaymentMethod {
    private String accountNumber;
    private String routingNumber;
    
    public BankTransferPayment(String accountNumber, String routingNumber) {
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("🏦 [Bank Transfer] Processing payment: $" + amount);
        System.out.println("   Account: ****" + accountNumber.substring(accountNumber.length() - 4));
    }
    
    @Override
    public String getName() {
        return "Bank Transfer";
    }
}

// Crypto payment - EASY TO ADD! Just create a new class!
class CryptoPayment implements PaymentMethod {
    private String walletAddress;
    private String cryptoType;
    
    public CryptoPayment(String walletAddress, String cryptoType) {
        this.walletAddress = walletAddress;
        this.cryptoType = cryptoType;
    }
    
    @Override
    public void pay(double amount) {
        System.out.println("🪙 [Crypto] Processing payment: $" + amount);
        System.out.println("   Type: " + cryptoType);
        System.out.println("   Wallet: " + walletAddress.substring(0, 6) + "...");
    }
    
    @Override
    public String getName() {
        return "Cryptocurrency";
    }
}

// ✅ PaymentProcessor is CLOSED for modification, OPEN for extension!
class PaymentProcessor {
    
    // This method works with ANY payment method - no if-else needed!
    public void processPayment(PaymentMethod payment, double amount) {
        System.out.println("\n💰 Processing payment with " + payment.getName());
        payment.pay(amount);
        System.out.println("✅ Payment processed successfully!");
    }
    
    // Process multiple payments
    public void processBatch(PaymentMethod[] payments, double amount) {
        for (PaymentMethod payment : payments) {
            processPayment(payment, amount);
        }
    }
}

// Test the solution
public class OCPSolution {
    public static void main(String[] args) {
        System.out.println("✅ OCP Solution Demo\n");
        
        PaymentProcessor processor = new PaymentProcessor();
        
        // Existing payment types
        PaymentMethod creditCard = new CreditCardPayment("4111111111111111", "123");
        PaymentMethod paypal = new PayPalPayment("user@example.com");
        
        // Process existing payments
        processor.processPayment(creditCard, 100.0);
        processor.processPayment(paypal, 50.0);
        
        // Add new payment type - NO MODIFICATION to PaymentProcessor!
        PaymentMethod crypto = new CryptoPayment("0x1234567890abcdef", "ETH");
        processor.processPayment(crypto, 0.5);
        
        // Process batch with multiple types
        System.out.println("\n=== Batch Processing ===");
        PaymentMethod[] payments = {
            creditCard,
            paypal,
            new BankTransferPayment("123456789", "021000021")
        };
        processor.processBatch(payments, 75.0);
        
        System.out.println("\n🎉 OCP achieved! New payment types can be added without modifying existing code!");
    }
}

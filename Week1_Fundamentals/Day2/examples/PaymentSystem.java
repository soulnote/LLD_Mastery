/**
 * Polymorphism Example: Payment Processing System
 * Demonstrates runtime polymorphism and interface-based design
 */

// Payment interface - defines contract
interface PaymentProcessor {
    boolean processPayment(double amount);
    String getPaymentMethod();
    double getTransactionFee(double amount);
}

// Abstract base class for common functionality
abstract class BasePayment implements PaymentProcessor {
    protected String merchantId;
    protected boolean isActive;
    
    public BasePayment(String merchantId) {
        this.merchantId = merchantId;
        this.isActive = true;
    }
    
    // Common validation logic
    protected boolean validateAmount(double amount) {
        return amount > 0 && amount <= 10000; // Max transaction limit
    }
    
    // Template method pattern
    public final boolean processPayment(double amount) {
        if (!isActive) {
            System.out.println("❌ Payment processor is inactive");
            return false;
        }
        
        if (!validateAmount(amount)) {
            System.out.println("❌ Invalid amount: $" + amount);
            return false;
        }
        
        return executePayment(amount);
    }
    
    // Abstract method for specific implementation
    protected abstract boolean executePayment(double amount);
}

// Credit Card Payment Implementation
class CreditCardPayment extends BasePayment {
    private String cardNumber;
    private String cardHolder;
    
    public CreditCardPayment(String merchantId, String cardNumber, String cardHolder) {
        super(merchantId);
        this.cardNumber = maskCardNumber(cardNumber);
        this.cardHolder = cardHolder;
    }
    
    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() < 4) return cardNumber;
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
    
    @Override
    protected boolean executePayment(double amount) {
        System.out.println("💳 Processing Credit Card Payment...");
        System.out.println("   Card: " + cardNumber);
        System.out.println("   Holder: " + cardHolder);
        System.out.println("   Amount: $" + amount);
        System.out.println("   Fee: $" + getTransactionFee(amount));
        System.out.println("✅ Credit Card payment successful!");
        return true;
    }
    
    @Override
    public String getPaymentMethod() {
        return "Credit Card";
    }
    
    @Override
    public double getTransactionFee(double amount) {
        return amount * 0.029; // 2.9% fee
    }
}

// PayPal Payment Implementation
class PayPalPayment extends BasePayment {
    private String email;
    
    public PayPalPayment(String merchantId, String email) {
        super(merchantId);
        this.email = email;
    }
    
    @Override
    protected boolean executePayment(double amount) {
        System.out.println("🅿️ Processing PayPal Payment...");
        System.out.println("   Email: " + email);
        System.out.println("   Amount: $" + amount);
        System.out.println("   Fee: $" + getTransactionFee(amount));
        System.out.println("✅ PayPal payment successful!");
        return true;
    }
    
    @Override
    public String getPaymentMethod() {
        return "PayPal";
    }
    
    @Override
    public double getTransactionFee(double amount) {
        return amount * 0.034 + 0.30; // 3.4% + $0.30 fee
    }
}

// Cryptocurrency Payment Implementation
class CryptoPayment extends BasePayment {
    private String walletAddress;
    private String cryptoType;
    
    public CryptoPayment(String merchantId, String walletAddress, String cryptoType) {
        super(merchantId);
        this.walletAddress = walletAddress;
        this.cryptoType = cryptoType;
    }
    
    @Override
    protected boolean executePayment(double amount) {
        System.out.println("₿ Processing Cryptocurrency Payment...");
        System.out.println("   Type: " + cryptoType);
        System.out.println("   Wallet: " + walletAddress.substring(0, 8) + "...");
        System.out.println("   Amount: $" + amount);
        System.out.println("   Fee: $" + getTransactionFee(amount));
        System.out.println("✅ Crypto payment successful!");
        return true;
    }
    
    @Override
    public String getPaymentMethod() {
        return cryptoType + " Cryptocurrency";
    }
    
    @Override
    public double getTransactionFee(double amount) {
        return 2.50; // Flat fee for crypto
    }
}

// Payment Gateway - demonstrates polymorphism
class PaymentGateway {
    public void processTransaction(PaymentProcessor processor, double amount) {
        System.out.println("\n🔄 Starting transaction with " + processor.getPaymentMethod());
        
        if (processor.processPayment(amount)) {
            double fee = processor.getTransactionFee(amount);
            double total = amount + fee;
            System.out.println("💰 Transaction Summary:");
            System.out.println("   Amount: $" + amount);
            System.out.println("   Fee: $" + String.format("%.2f", fee));
            System.out.println("   Total: $" + String.format("%.2f", total));
        }
        
        System.out.println("-".repeat(50));
    }
}
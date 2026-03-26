/**
 * EXAMPLE: Strategy Pattern
 */
public class StrategyPattern {
    
    interface PaymentStrategy {
        void pay(double amount);
    }
    
    static class CreditCardPayment implements PaymentStrategy {
        public void pay(double amount) {
            System.out.println("Paid $" + amount + " with Credit Card");
        }
    }
    
    static class PayPalPayment implements PaymentStrategy {
        public void pay(double amount) {
            System.out.println("Paid $" + amount + " with PayPal");
        }
    }
    
    static class ShoppingCart {
        private PaymentStrategy strategy;
        public void setPayment(PaymentStrategy s) { this.strategy = s; }
        public void checkout(double amount) { strategy.pay(amount); }
    }
    
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.setPayment(new CreditCardPayment());
        cart.checkout(100);
    }
}

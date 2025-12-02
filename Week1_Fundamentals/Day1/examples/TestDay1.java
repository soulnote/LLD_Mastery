/**
 * Test class to demonstrate Day 1 concepts
 * Run this to see Encapsulation and Inheritance in action!
 */
public class TestDay1 {
    public static void main(String[] args) {
        System.out.println("🚀 Day 1: OOP Fundamentals Demo\n");
        
        // ===== ENCAPSULATION DEMO =====
        System.out.println("=== ENCAPSULATION DEMO ===");
        BankAccount account = new BankAccount("ACC001", "John Doe", 1000.0);
        
        System.out.println("Account Holder: " + account.getAccountHolder());
        System.out.println("Initial Balance: $" + account.getBalance());
        
        // Valid operations
        account.deposit(500.0);
        account.withdraw(200.0);
        
        // Invalid operations
        account.withdraw(2000.0); // Insufficient funds
        account.deposit(-100.0);  // Invalid amount
        
        System.out.println("Final Balance: $" + account.getBalance());
        
        // Deactivate account
        account.deactivateAccount();
        account.deposit(100.0); // Should fail
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // ===== INHERITANCE DEMO =====
        System.out.println("=== INHERITANCE DEMO ===");
        
        // Create a Car
        Car car = new Car("Toyota", "Camry", 2023, 25000.0, 4, "Gasoline");
        car.start(); // Inherited method
        car.displaySpecs(); // Overridden method
        car.honk(); // Car-specific method
        car.stop(); // Inherited method
        
        System.out.println();
        
        // Create a Motorcycle
        Motorcycle bike = new Motorcycle("Harley-Davidson", "Street 750", 2023, 8000.0, 750, false);
        bike.start(); // Inherited method
        bike.displaySpecs(); // Overridden method
        bike.wheelie(); // Motorcycle-specific method
        bike.stop(); // Inherited method
        
        System.out.println("\n🎉 Day 1 concepts demonstrated successfully!");
        System.out.println("💡 Key Learning: Encapsulation protects data, Inheritance promotes reusability!");
    }
}
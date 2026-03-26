/**
 * EXERCISE 1: Draw UML Diagram
 * 
 * TASK: Create a UML class diagram for a Banking System
 * 
 * Requirements:
 * 1. Design the following classes with fields and methods:
 *    - Account (base class)
 *    - SavingsAccount (extends Account)
 *    - CheckingAccount (extends Account)
 *    - Customer
 *    - Bank
 * 
 Define relationships:
 *    - Customer HAS-A Account (1..*)
 * 2. *    - Bank HAS-A Customer (1..*)
 *    - SavingsAccount IS-A Account
 *    - CheckingAccount IS-A Account
 * 
 * 3. Draw the UML diagram on paper or in a tool
 * 
 * DIFFICULTY: ⭐⭐☆☆☆
 * TIME: 20 minutes
 */

public class Exercise1_DrawUML {
    
    /*
     * TODO: Draw the UML diagram based on these classes:
     * 
     * Here's the class structure to help you:
     * 
     * Account (abstract):
     * - accountNumber: String
     * - balance: double
     * - accountHolder: Customer
     * + deposit(amount: double)
     * + withdraw(amount: double): boolean
     * + getBalance(): double
     * 
     * SavingsAccount extends Account:
     * - interestRate: double
     * + calculateInterest(): double
     * 
     * CheckingAccount extends Account:
     * - overdraftLimit: double
     * + withdraw(amount: double): boolean (with overdraft)
     * 
     * Customer:
     * - customerId: String
     * - name: String
     * - accounts: List<Account>
     * + addAccount(account: Account)
     * + getAccounts(): List<Account>
     * 
     * Bank:
     * - bankName: String
     * - customers: List<Customer>
     * + addCustomer(customer: Customer)
     * + findCustomer(id: String): Customer
     */
    
    public static void main(String[] args) {
        System.out.println("📊 Exercise 1: Draw UML for Banking System\n");
        
        System.out.println("=== Classes to Draw ===");
        System.out.println("1. Account (abstract)");
        System.out.println("2. SavingsAccount");
        System.out.println("3. CheckingAccount");
        System.out.println("4. Customer");
        System.out.println("5. Bank");
        
        System.out.println("\n=== Relationships ===");
        System.out.println("- SavingsAccount △→ Account");
        System.out.println("- CheckingAccount △→ Account");
        System.out.println("- Customer ◇── Account (1..*)");
        System.out.println("- Bank ◇── Customer (1..*)");
        
        System.out.println("\n📝 Draw this UML diagram on paper or in a tool like:");
        System.out.println("   - Lucidchart");
        System.out.println("   - Draw.io");
        System.out.println("   - PlantUML");
        
        System.out.println("\n✅ Draw your UML diagram!");
    }
}

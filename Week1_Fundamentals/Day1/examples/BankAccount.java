/**
 * Encapsulation Example: Bank Account System
 * Demonstrates data hiding and controlled access
 */
public class BankAccount {
    // Private fields - encapsulated data
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private boolean isActive;
    
    // Constructor
    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance >= 0 ? initialBalance : 0;
        this.isActive = true;
    }
    
    // Controlled access methods
    public boolean deposit(double amount) {
        if (amount > 0 && isActive) {
            balance += amount;
            System.out.println("Deposited: $" + amount + ". New balance: $" + balance);
            return true;
        }
        System.out.println("Invalid deposit amount or account inactive");
        return false;
    }
    
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance && isActive) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount + ". New balance: $" + balance);
            return true;
        }
        System.out.println("Invalid withdrawal: insufficient funds or account inactive");
        return false;
    }
    
    // Getters - controlled read access
    public double getBalance() {
        return isActive ? balance : 0;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountHolder() {
        return accountHolder;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    // Account management
    public void deactivateAccount() {
        this.isActive = false;
        System.out.println("Account " + accountNumber + " has been deactivated");
    }
}
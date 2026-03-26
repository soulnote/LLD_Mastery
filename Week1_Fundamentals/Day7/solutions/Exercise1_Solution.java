/**
 * SOLUTION: Banking System Implementation
 * 
 * This implements the UML diagram created in Exercise1
 * 
 * UML DIAGRAM IMPLEMENTED:
 * ======================
 * 
 *         ┌─────────────┐
 *         │    Bank     │  ◇────────┐
 *         └──────┬──────┘           │
 *                │ 1..*             │
 *                ▼                  │
 *         ┌─────────────┐           │
 *         │  Customer   │ ◇────────┤
 *         └──────┬──────┘           │
 *                │ 1..*             │
 *                ▼                  │
 *    ┌──────────┴──────────┐       │
 *    │     <<interface>>   │       │
 *    │      Account        │       │
 *    └──────────┬──────────┘       │
 *               △ 1                 │
 *       ┌───────┴───────┐           │
 *       │               │           │
 * ┌─────┴─────┐ ┌─────┴─────┐     │
 * │ Savings   │ │ Checking  │     │
 * │ Account   │ │ Account   │     │
 * └───────────┘ └───────────┘     │
 */

// Abstract Account class
abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected Customer accountHolder;
    
    public Account(String accountNumber, Customer accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = 0.0;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + " to account " + accountNumber);
        }
    }
    
    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrew $" + amount + " from account " + accountNumber);
            return true;
        }
        return false;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public Customer getAccountHolder() {
        return accountHolder;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract String getAccountType();
}

// SavingsAccount extends Account
class SavingsAccount extends Account {
    private double interestRate;
    
    public SavingsAccount(String accountNumber, Customer accountHolder, double interestRate) {
        super(accountNumber, accountHolder);
        this.interestRate = interestRate;
    }
    
    public double calculateInterest() {
        return balance * interestRate;
    }
    
    @Override
    public String getAccountType() {
        return "Savings";
    }
}

// CheckingAccount extends Account
class CheckingAccount extends Account {
    private double overdraftLimit;
    
    public CheckingAccount(String accountNumber, Customer accountHolder, double overdraftLimit) {
        super(accountNumber, accountHolder);
        this.overdraftLimit = overdraftLimit;
    }
    
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && (balance + overdraftLimit) >= amount) {
            balance -= amount;
            System.out.println("Withdrew $" + amount + " from account " + accountNumber);
            return true;
        }
        return false;
    }
    
    @Override
    public String getAccountType() {
        return "Checking";
    }
    
    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}

// Customer class
class Customer {
    private String customerId;
    private String name;
    private java.util.List<Account> accounts;
    
    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
        this.accounts = new java.util.ArrayList<>();
    }
    
    public void addAccount(Account account) {
        accounts.add(account);
    }
    
    public java.util.List<Account> getAccounts() {
        return accounts;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public String getName() {
        return name;
    }
}

// Bank class
class Bank {
    private String bankName;
    private java.util.List<Customer> customers;
    
    public Bank(String bankName) {
        this.bankName = bankName;
        this.customers = new java.util.ArrayList<>();
    }
    
    public void addCustomer(Customer customer) {
        customers.add(customer);
        System.out.println("Added customer: " + customer.getName());
    }
    
    public Customer findCustomer(String id) {
        for (Customer c : customers) {
            if (c.getCustomerId().equals(id)) {
                return c;
            }
        }
        return null;
    }
    
    public String getBankName() {
        return bankName;
    }
    
    public int getCustomerCount() {
        return customers.size();
    }
}

public class Exercise1_Solution {
    public static void main(String[] args) {
        System.out.println("🏦 Banking System - SOLUTION\n");
        
        // Create bank
        Bank bank = new Bank("World Bank");
        
        // Create customers
        Customer john = new Customer("C001", "John Doe");
        Customer jane = new Customer("C002", "Jane Smith");
        
        // Add customers to bank
        bank.addCustomer(john);
        bank.addCustomer(jane);
        
        // Create accounts for John
        SavingsAccount savings = new SavingsAccount("S001", john, 0.05);
        john.addAccount(savings);
        
        CheckingAccount checking = new CheckingAccount("C001", john, 500.0);
        john.addAccount(checking);
        
        // Create account for Jane
        SavingsAccount janeSavings = new SavingsAccount("S002", jane, 0.04);
        jane.addAccount(janeSavings);
        
        // Perform operations
        System.out.println("\n=== John's Account Operations ===");
        savings.deposit(1000);
        savings.withdraw(200);
        System.out.println("Savings Balance: $" + savings.getBalance());
        System.out.println("Interest: $" + savings.calculateInterest());
        
        checking.deposit(500);
        checking.withdraw(800); // Uses overdraft
        System.out.println("Checking Balance: $" + checking.getBalance());
        
        System.out.println("\n=== Jane's Account Operations ===");
        janeSavings.deposit(2000);
        System.out.println("Savings Balance: $" + janeSavings.getBalance());
        
        // Find customer
        System.out.println("\n=== Customer Lookup ===");
        Customer found = bank.findCustomer("C001");
        if (found != null) {
            System.out.println("Found: " + found.getName());
            System.out.println("Accounts: " + found.getAccounts().size());
        }
        
        System.out.println("\n=== UML to Code ===");
        System.out.println("✅ Bank ◇── Customer (1..*)");
        System.out.println("✅ Customer ◇── Account (1..*)");
        System.out.println("✅ SavingsAccount △→ Account");
        System.out.println("✅ CheckingAccount △→ Account");
        
        System.out.println("\n✅ Banking system implemented from UML diagram!");
    }
}

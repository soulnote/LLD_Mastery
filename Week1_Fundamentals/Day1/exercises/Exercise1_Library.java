/**
 * EXERCISE 1: Library Management System
 * 
 * TASK: Implement a Library Management System demonstrating Encapsulation
 * 
 * Requirements:
 * 1. Create a Book class with private fields: isbn, title, author, isAvailable
 * 2. Implement proper encapsulation with getters and controlled setters
 * 3. Add methods: borrowBook(), returnBook(), getBookInfo()
 * 4. Ensure books can't be borrowed if already borrowed
 * 5. Add validation for ISBN format (must be 13 digits)
 * 
 * DIFFICULTY: ⭐⭐☆☆☆
 * TIME: 20 minutes
 */

public class Exercise1_Library {
    
    // TODO: Implement Book class here
    static class Book {
        // TODO: Add private fields
        
        // TODO: Add constructor with validation
        
        // TODO: Add borrowBook() method
        
        // TODO: Add returnBook() method
        
        // TODO: Add getters and appropriate setters
        
        // TODO: Add getBookInfo() method
    }
    
    // Test your implementation
    public static void main(String[] args) {
        System.out.println("📚 Exercise 1: Library Management System");
        
        // TODO: Create book instances and test all methods
        // Test cases:
        // 1. Create a valid book
        // 2. Try to borrow available book
        // 3. Try to borrow already borrowed book
        // 4. Return book and borrow again
        // 5. Test invalid ISBN
        
        System.out.println("✅ Complete the implementation and run tests!");
    }
}
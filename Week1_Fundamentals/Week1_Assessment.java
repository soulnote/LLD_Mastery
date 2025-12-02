/**
 * WEEK 1 ASSESSMENT QUIZ
 * Test your understanding of OOP Fundamentals and SOLID Principles
 * 
 * Instructions:
 * 1. Read each question carefully
 * 2. Implement the required code
 * 3. Run the tests to verify your solutions
 * 4. Score: 80%+ = Excellent, 60-79% = Good, <60% = Review needed
 */

import java.util.*;

public class Week1_Assessment {
    
    /**
     * QUESTION 1: Encapsulation (10 points)
     * Create a BankAccount class that properly encapsulates account data
     * Requirements:
     * - Private fields: accountNumber, balance, accountHolder
     * - Constructor with validation (balance >= 0)
     * - Methods: deposit(), withdraw(), getBalance()
     * - Withdraw should fail if insufficient funds
     */
    static class BankAccount {
        // TODO: Implement according to requirements
    }
    
    /**
     * QUESTION 2: Inheritance (10 points)
     * Create a Vehicle hierarchy with proper inheritance
     * Requirements:
     * - Abstract Vehicle class with: brand, model, start(), stop()
     * - Car class extends Vehicle, adds: doors, honk()
     * - Motorcycle class extends Vehicle, adds: engineCC, wheelie()
     */
    static abstract class Vehicle {
        // TODO: Implement base class
    }
    
    static class Car extends Vehicle {
        // TODO: Implement Car class
    }
    
    static class Motorcycle extends Vehicle {
        // TODO: Implement Motorcycle class
    }
    
    /**
     * QUESTION 3: Polymorphism (15 points)
     * Create a Shape calculator using polymorphism
     * Requirements:
     * - Shape interface with calculateArea() method
     * - Circle, Rectangle, Triangle implementations
     * - ShapeCalculator class that works with any Shape
     */
    interface Shape {
        // TODO: Define interface
    }
    
    static class Circle implements Shape {
        // TODO: Implement Circle
    }
    
    static class Rectangle implements Shape {
        // TODO: Implement Rectangle
    }
    
    static class ShapeCalculator {
        // TODO: Implement calculator that works with any Shape
    }
    
    /**
     * QUESTION 4: Abstraction (15 points)
     * Create a media player system using abstraction
     * Requirements:
     * - MediaPlayer interface with play(), pause(), stop()
     * - Abstract AudioPlayer with common audio functionality
     * - MP3Player and WAVPlayer concrete implementations
     */
    interface MediaPlayer {
        // TODO: Define interface
    }
    
    static abstract class AudioPlayer implements MediaPlayer {
        // TODO: Implement abstract class
    }
    
    static class MP3Player extends AudioPlayer {
        // TODO: Implement MP3Player
    }
    
    /**
     * QUESTION 5: Single Responsibility Principle (25 points)
     * Refactor this OrderProcessor class to follow SRP
     * Current violations: validation, calculation, email, persistence
     */
    
    // ❌ BAD: Violates SRP
    static class OrderProcessor {
        public boolean processOrder(Order order) {
            // Validation
            if (order.getItems().isEmpty()) return false;
            
            // Calculation
            double total = 0;
            for (OrderItem item : order.getItems()) {
                total += item.getPrice() * item.getQuantity();
            }
            order.setTotal(total);
            
            // Email notification
            System.out.println("Sending email to: " + order.getCustomerEmail());
            
            // Save to database
            System.out.println("Saving order to database");
            
            return true;
        }
    }
    
    // TODO: Refactor into separate classes following SRP
    static class OrderValidator {
        // TODO: Extract validation logic
    }
    
    static class OrderCalculator {
        // TODO: Extract calculation logic
    }
    
    static class OrderEmailService {
        // TODO: Extract email logic
    }
    
    static class OrderRepository {
        // TODO: Extract persistence logic
    }
    
    /**
     * QUESTION 6: Method Overloading (10 points)
     * Create a Calculator class with overloaded methods
     */
    static class Calculator {
        // TODO: Implement overloaded add methods for:
        // - add(int, int)
        // - add(double, double)
        // - add(int, int, int)
        // - add(double[])
    }
    
    /**
     * QUESTION 7: Interface vs Abstract Class (15 points)
     * Explain when to use interface vs abstract class and implement both
     */
    
    // TODO: Create an interface for Drawable objects
    interface Drawable {
        // TODO: Define interface methods
    }
    
    // TODO: Create an abstract class for GraphicObject
    static abstract class GraphicObject implements Drawable {
        // TODO: Define abstract class with common functionality
    }
    
    // Supporting classes for the assessment
    static class Order {
        private List<OrderItem> items = new ArrayList<>();
        private String customerEmail;
        private double total;
        
        public Order(String customerEmail) {
            this.customerEmail = customerEmail;
        }
        
        public void addItem(OrderItem item) {
            items.add(item);
        }
        
        // Getters and setters
        public List<OrderItem> getItems() { return items; }
        public String getCustomerEmail() { return customerEmail; }
        public double getTotal() { return total; }
        public void setTotal(double total) { this.total = total; }
    }
    
    static class OrderItem {
        private String name;
        private double price;
        private int quantity;
        
        public OrderItem(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
        
        public String getName() { return name; }
        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
    }
    
    // Test runner
    public static void main(String[] args) {
        System.out.println("📝 WEEK 1 ASSESSMENT QUIZ");
        System.out.println("Complete all questions and run tests to check your score!\n");
        
        int totalScore = 0;
        int maxScore = 100;
        
        // Test implementations here
        System.out.println("🎯 Assessment Areas:");
        System.out.println("1. Encapsulation (10 points)");
        System.out.println("2. Inheritance (10 points)");
        System.out.println("3. Polymorphism (15 points)");
        System.out.println("4. Abstraction (15 points)");
        System.out.println("5. Single Responsibility Principle (25 points)");
        System.out.println("6. Method Overloading (10 points)");
        System.out.println("7. Interface vs Abstract Class (15 points)");
        
        System.out.println("\n✅ Complete the implementations above!");
        System.out.println("💡 Tip: Focus on clean, well-structured code that follows OOP principles");
        
        // TODO: Add test cases for each question
        // calculateScore(totalScore, maxScore);
    }
    
    private static void calculateScore(int score, int maxScore) {
        double percentage = (score * 100.0) / maxScore;
        
        System.out.println("\n🎯 ASSESSMENT RESULTS");
        System.out.println("Score: " + score + "/" + maxScore + " (" + String.format("%.1f", percentage) + "%)");
        
        if (percentage >= 80) {
            System.out.println("🏆 EXCELLENT! You've mastered Week 1 concepts!");
        } else if (percentage >= 60) {
            System.out.println("👍 GOOD! Review areas where you lost points.");
        } else {
            System.out.println("📚 REVIEW NEEDED. Go back and study the concepts again.");
        }
    }
}
/**
 * EXERCISE 1: Advanced Calculator System
 * 
 * TASK: Create a calculator system demonstrating method overloading and polymorphism
 * 
 * Requirements:
 * 1. Create Calculator interface with calculate() method
 * 2. Implement BasicCalculator: +, -, *, / operations
 * 3. Implement ScientificCalculator: extends basic + sin, cos, log, power
 * 4. Implement ProgrammerCalculator: extends basic + AND, OR, XOR, shift operations
 * 5. Use method overloading for different parameter types
 * 6. Create CalculatorFactory to demonstrate polymorphism
 * 
 * DIFFICULTY: ⭐⭐⭐☆☆
 * TIME: 35 minutes
 */

public class Exercise1_Calculator {
    
    // TODO: Create Calculator interface
    interface Calculator {
        // TODO: Add calculate methods for different operations
    }
    
    // TODO: Implement BasicCalculator
    static class BasicCalculator implements Calculator {
        // TODO: Implement basic arithmetic operations
        // TODO: Add method overloading for int, double, float parameters
        
        // Method overloading examples:
        // public double add(double a, double b)
        // public int add(int a, int b)
        // public double add(double a, double b, double c)
    }
    
    // TODO: Implement ScientificCalculator
    static class ScientificCalculator extends BasicCalculator {
        // TODO: Add scientific operations: sin, cos, tan, log, power, sqrt
        // TODO: Override calculate method for scientific operations
    }
    
    // TODO: Implement ProgrammerCalculator
    static class ProgrammerCalculator extends BasicCalculator {
        // TODO: Add bitwise operations: AND, OR, XOR, NOT, left shift, right shift
        // TODO: Add number base conversions: binary, octal, hexadecimal
    }
    
    // TODO: Create CalculatorFactory
    static class CalculatorFactory {
        // TODO: Create method to return appropriate calculator based on type
        // public static Calculator createCalculator(String type)
    }
    
    // Test your implementation
    public static void main(String[] args) {
        System.out.println("🧮 Exercise 1: Advanced Calculator System");
        
        // TODO: Test all calculator types
        // Test cases:
        // 1. Create BasicCalculator and test overloaded methods
        // 2. Create ScientificCalculator and test scientific operations
        // 3. Create ProgrammerCalculator and test bitwise operations
        // 4. Use CalculatorFactory to create calculators polymorphically
        // 5. Demonstrate runtime polymorphism with Calculator array
        
        System.out.println("✅ Complete the implementation and run tests!");
    }
}
/**
 * EXERCISE 2: Shape Hierarchy
 * 
 * TASK: Create a shape hierarchy demonstrating Inheritance
 * 
 * Requirements:
 * 1. Create abstract Shape class with: color, area(), perimeter(), display()
 * 2. Implement Circle class: radius field, area and perimeter calculations
 * 3. Implement Rectangle class: length, width fields, area and perimeter calculations
 * 4. Implement Triangle class: three sides, area using Heron's formula
 * 5. Each shape should display its specific properties
 * 
 * DIFFICULTY: ⭐⭐⭐☆☆
 * TIME: 30 minutes
 */

public class Exercise2_Shapes {
    
    // TODO: Implement abstract Shape class
    static abstract class Shape {
        // TODO: Add protected color field
        
        // TODO: Add constructor
        
        // TODO: Add abstract methods: area(), perimeter()
        
        // TODO: Add concrete display() method
    }
    
    // TODO: Implement Circle class
    static class Circle extends Shape {
        // TODO: Add radius field
        
        // TODO: Implement constructor
        
        // TODO: Implement area() and perimeter() methods
        
        // TODO: Override display() method
    }
    
    // TODO: Implement Rectangle class
    static class Rectangle extends Shape {
        // TODO: Add length and width fields
        
        // TODO: Implement constructor with validation
        
        // TODO: Implement area() and perimeter() methods
        
        // TODO: Override display() method
    }
    
    // TODO: Implement Triangle class
    static class Triangle extends Shape {
        // TODO: Add three sides
        
        // TODO: Implement constructor with triangle validity check
        
        // TODO: Implement area() using Heron's formula
        
        // TODO: Implement perimeter() method
        
        // TODO: Override display() method
    }
    
    // Test your implementation
    public static void main(String[] args) {
        System.out.println("🔺 Exercise 2: Shape Hierarchy");
        
        // TODO: Create instances of each shape and test
        // Test cases:
        // 1. Create Circle with radius 5
        // 2. Create Rectangle with length 4, width 6
        // 3. Create Triangle with sides 3, 4, 5
        // 4. Display all shapes and their properties
        // 5. Test invalid triangle (sides that can't form triangle)
        
        System.out.println("✅ Complete the implementation and run tests!");
    }
}
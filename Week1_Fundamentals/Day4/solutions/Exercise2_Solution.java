/**
 * SOLUTION: Shape Hierarchy - Following LSP
 * Demonstrates Liskov Substitution Principle
 */

// ✅ LSP: Interface defines contract
interface Shape {
    double area();
}

// ✅ LSP: Rectangle properly implements Shape
class Rectangle implements Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    public void setWidth(double width) { this.width = width; }
    public void setHeight(double height) { this.height = height; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    
    @Override
    public double area() {
        return width * height;
    }
}

// ✅ LSP: Square implements Shape directly - NOT extending Rectangle!
// This avoids the LSP violation
class Square implements Shape {
    private double side;
    
    public Square(double side) {
        this.side = side;
    }
    
    public void setSide(double side) {
        this.side = side;
    }
    
    public double getSide() {
        return side;
    }
    
    @Override
    public double area() {
        return side * side;
    }
}

// ✅ LSP: Circle also implements Shape
class Circle implements Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    public void setRadius(double radius) {
        this.radius = radius;
    }
    
    public double getRadius() {
        return radius;
    }
    
    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

// Triangle
class Triangle implements Shape {
    private double base;
    private double height;
    
    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }
    
    @Override
    public double area() {
        return 0.5 * base * height;
    }
}

// ✅ LSP: Works with ANY Shape - polymorphism!
class AreaCalculator {
    
    public static double calculate(Shape shape) {
        return shape.area();
    }
    
    public static double totalArea(Shape[] shapes) {
        double total = 0;
        for (Shape shape : shapes) {
            total += shape.area();
        }
        return total;
    }
    
    public static void printShapeInfo(Shape shape) {
        String type = shape.getClass().getSimpleName();
        System.out.println(type + " area: " + String.format("%.2f", shape.area()));
    }
}

public class Exercise2_Solution {
    public static void main(String[] args) {
        System.out.println("📐 Shape Hierarchy - SOLUTION\n");
        
        // All shapes implement Shape - proper LSP!
        Shape rectangle = new Rectangle(5, 10);
        Shape square = new Square(5);
        Shape circle = new Circle(3);
        Shape triangle = new Triangle(6, 4);
        
        // Print individual areas
        System.out.println("=== Individual Areas ===");
        AreaCalculator.printShapeInfo(rectangle);
        AreaCalculator.printShapeInfo(square);
        AreaCalculator.printShapeInfo(circle);
        AreaCalculator.printShapeInfo(triangle);
        
        // Polymorphism - any Shape can be used interchangeably
        System.out.println("\n=== Polymorphism Demo ===");
        Shape[] shapes = {rectangle, square, circle, triangle};
        
        for (Shape shape : shapes) {
            System.out.println("Shape: " + shape.getClass().getSimpleName() + 
                             " -> Area: " + String.format("%.2f", shape.area()));
        }
        
        // Calculate total area
        double total = AreaCalculator.totalArea(shapes);
        System.out.println("\nTotal area: " + String.format("%.2f", total));
        
        // Substitute any shape anywhere
        System.out.println("\n=== Substitution Demo ===");
        printArea(rectangle);
        printArea(square);
        printArea(circle);
        
        System.out.println("\n🎉 LSP achieved! All shapes are properly substitutable!");
    }
    
    // This method works with ANY Shape - no matter which implementation
    static void printArea(Shape shape) {
        System.out.println("Area of " + shape.getClass().getSimpleName() + 
                         ": " + String.format("%.2f", shape.area()));
    }
}

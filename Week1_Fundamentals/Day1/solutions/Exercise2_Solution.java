/**
 * SOLUTION: Exercise 2 - Shape Hierarchy
 * Demonstrates inheritance with abstract classes and method overriding
 */

public class Exercise2_Solution {
    
    static abstract class Shape {
        protected String color;
        
        public Shape(String color) {
            this.color = color != null ? color : "Unknown";
        }
        
        public abstract double area();
        public abstract double perimeter();
        
        public void display() {
            System.out.printf("🎨 %s %s - Area: %.2f, Perimeter: %.2f%n", 
                color, this.getClass().getSimpleName(), area(), perimeter());
        }
        
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
    }
    
    static class Circle extends Shape {
        private double radius;
        
        public Circle(String color, double radius) {
            super(color);
            if (radius <= 0) {
                throw new IllegalArgumentException("Radius must be positive");
            }
            this.radius = radius;
        }
        
        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
        
        @Override
        public double perimeter() {
            return 2 * Math.PI * radius;
        }
        
        @Override
        public void display() {
            System.out.printf("⭕ %s Circle (radius: %.1f) - Area: %.2f, Perimeter: %.2f%n", 
                color, radius, area(), perimeter());
        }
        
        public double getRadius() { return radius; }
    }
    
    static class Rectangle extends Shape {
        private double length;
        private double width;
        
        public Rectangle(String color, double length, double width) {
            super(color);
            if (length <= 0 || width <= 0) {
                throw new IllegalArgumentException("Length and width must be positive");
            }
            this.length = length;
            this.width = width;
        }
        
        @Override
        public double area() {
            return length * width;
        }
        
        @Override
        public double perimeter() {
            return 2 * (length + width);
        }
        
        @Override
        public void display() {
            System.out.printf("▭ %s Rectangle (%.1f x %.1f) - Area: %.2f, Perimeter: %.2f%n", 
                color, length, width, area(), perimeter());
        }
        
        public double getLength() { return length; }
        public double getWidth() { return width; }
    }
    
    static class Triangle extends Shape {
        private double side1, side2, side3;
        
        public Triangle(String color, double side1, double side2, double side3) {
            super(color);
            if (!isValidTriangle(side1, side2, side3)) {
                throw new IllegalArgumentException("Invalid triangle: sides don't satisfy triangle inequality");
            }
            this.side1 = side1;
            this.side2 = side2;
            this.side3 = side3;
        }
        
        private boolean isValidTriangle(double a, double b, double c) {
            return a > 0 && b > 0 && c > 0 && 
                   a + b > c && b + c > a && a + c > b;
        }
        
        @Override
        public double area() {
            // Heron's formula
            double s = perimeter() / 2;
            return Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
        }
        
        @Override
        public double perimeter() {
            return side1 + side2 + side3;
        }
        
        @Override
        public void display() {
            System.out.printf("🔺 %s Triangle (%.1f, %.1f, %.1f) - Area: %.2f, Perimeter: %.2f%n", 
                color, side1, side2, side3, area(), perimeter());
        }
        
        public double getSide1() { return side1; }
        public double getSide2() { return side2; }
        public double getSide3() { return side3; }
    }
    
    public static void main(String[] args) {
        System.out.println("🔺 Solution: Shape Hierarchy\n");
        
        try {
            // Create different shapes
            Circle circle = new Circle("Red", 5.0);
            Rectangle rectangle = new Rectangle("Blue", 4.0, 6.0);
            Triangle triangle = new Triangle("Green", 3.0, 4.0, 5.0);
            
            // Display all shapes
            circle.display();
            rectangle.display();
            triangle.display();
            
            System.out.println();
            
            // Test polymorphism
            Shape[] shapes = {circle, rectangle, triangle};
            System.out.println("📊 Polymorphic behavior:");
            for (Shape shape : shapes) {
                shape.display();
            }
            
            System.out.println();
            
            // Test invalid triangle
            try {
                Triangle invalidTriangle = new Triangle("Yellow", 1.0, 2.0, 5.0);
            } catch (IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n🎯 Key Learning: Inheritance enables code reuse and polymorphic behavior!");
    }
}
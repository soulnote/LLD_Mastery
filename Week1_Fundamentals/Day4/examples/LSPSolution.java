/**
 * EXAMPLE: Liskov Substitution Principle Solution
 * 
 * This code FOLLOWS LSP - using composition and proper abstractions
 */

public class LSPSolution {
    
    // ✅ SOLUTION 1: Use interfaces instead of inheritance
    // Both Rectangle and Square implement Shape2D
    interface Shape2D {
        double area();
    }
    
    // Rectangle implements the interface properly
    static class Rectangle implements Shape2D {
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
    
    // Square implements the interface properly - no inheritance issues!
    static class Square implements Shape2D {
        private double side;
        
        public Square(double side) {
            this.side = side;
        }
        
        public void setSide(double side) { this.side = side; }
        public double getSide() { return side; }
        
        @Override
        public double area() {
            return side * side;
        }
    }
    
    // ✅ SOLUTION 2: Use composition instead of inheritance
    // A Square WRAPS a Rectangle rather than extending it
    static class SquareUsingComposition {
        private double side;
        
        public SquareUsingComposition(double side) {
            this.side = side;
        }
        
        public void setSide(double side) {
            this.side = side;
        }
        
        public double getSide() {
            return side;
        }
        
        // Delegate to Rectangle but only use one dimension
        public Rectangle toRectangle() {
            return new Rectangle(side, side);
        }
        
        public double area() {
            return side * side;
        }
    }
    
    // ✅ Works with any Shape2D - polymorphism!
    static class AreaCalculator {
        public static double calculate(Shape2D shape) {
            return shape.area();
        }
        
        public static void printArea(Shape2D shape, String name) {
            System.out.println(name + " area: " + shape.area());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("✅ LSP Solution Demo\n");
        
        // Use interfaces - both work correctly
        System.out.println("=== Using Interfaces ===");
        Shape2D rectangle = new Rectangle(5, 10);
        Shape2D square = new Square(5);
        
        AreaCalculator.printArea(rectangle, "Rectangle");
        AreaCalculator.printArea(square, "Square");
        
        // Test polymorphism - both can be used interchangeably
        System.out.println("\n=== Polymorphism Demo ===");
        Shape2D[] shapes = {
            new Rectangle(3, 4),
            new Square(5),
            new Rectangle(10, 2)
        };
        
        double totalArea = 0;
        for (Shape2D shape : shapes) {
            totalArea += AreaCalculator.calculate(shape);
        }
        System.out.println("Total area of all shapes: " + totalArea);
        
        // Use composition
        System.out.println("\n=== Using Composition ===");
        SquareUsingComposition composedSquare = new SquareUsingComposition(7);
        System.out.println("Square (composition) area: " + composedSquare.area());
        Rectangle rectFromSquare = composedSquare.toRectangle();
        System.out.println("Rectangle from square area: " + rectFromSquare.area());
        
        System.out.println("\n🎉 LSP achieved! Shapes are properly substitutable!");
    }
}

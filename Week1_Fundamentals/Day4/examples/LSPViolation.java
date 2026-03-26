/**
 * EXAMPLE: Liskov Substitution Principle Violation
 * 
 * This code VIOLATES LSP - Square cannot substitute for Rectangle
 * because it changes the expected behavior
 */

public class LSPViolation {
    
    // Base class - Rectangle
    static class Rectangle {
        protected double width;
        protected double height;
        
        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }
        
        public void setWidth(double width) {
            this.width = width;
        }
        
        public void setHeight(double height) {
            this.height = height;
        }
        
        public double getWidth() { return width; }
        public double getHeight() { return height; }
        
        // Area should be width * height
        public double area() {
            return width * height;
        }
    }
    
    // ❌ VIOLATION: Square IS-A Rectangle, but...
    static class Square extends Rectangle {
        
        public Square(double side) {
            super(side, side);
        }
        
        // LSP Violation: Setting width also changes height!
        // This breaks the substitutability of Square for Rectangle
        @Override
        public void setWidth(double width) {
            this.width = width;
            this.height = width; // Square forces height to equal width!
        }
        
        @Override
        public void setHeight(double height) {
            this.height = height;
            this.width = height; // Square forces width to equal height!
        }
    }
    
    // This method expects a Rectangle but may receive a Square
    // This breaks when a Square is passed!
    static class AreaCalculator {
        public static void doubleWidth(Rectangle r, double factor) {
            double originalArea = r.area();
            r.setWidth(r.getWidth() * factor);
            double newArea = r.area();
            
            // Expected: new area = original area * factor
            // But with Square: this calculation is WRONG!
            System.out.println("Original area: " + originalArea);
            System.out.println("New area: " + newArea);
            System.out.println("Expected: " + (originalArea * factor));
            System.out.println("Match: " + (newArea == originalArea * factor));
        }
    }
    
    public static void main(String[] args) {
        System.out.println("❌ LSP Violation Demo\n");
        
        // Test with Rectangle - works as expected
        Rectangle rect = new Rectangle(5, 10);
        System.out.println("=== Testing Rectangle ===");
        System.out.println("Initial: width=" + rect.getWidth() + ", height=" + rect.getHeight());
        System.out.println("Area: " + rect.area());
        
        AreaCalculator.doubleWidth(rect, 2);
        
        // Test with Square - BROKEN!
        Square square = new Square(5);
        System.out.println("\n=== Testing Square ===");
        System.out.println("Initial: width=" + square.getWidth() + ", height=" + square.getHeight());
        System.out.println("Area: " + square.area());
        
        // This will produce unexpected results!
        AreaCalculator.doubleWidth(square, 2);
        
        // Demonstrate the violation
        System.out.println("\n⚠️ PROBLEM: Square changed width to 10, but height also changed to 10!");
        System.out.println("   Expected: width=10, height=5 -> area=50");
        System.out.println("   Actual: width=10, height=10 -> area=100");
        System.out.println("   This breaks the Rectangle contract!");
    }
}

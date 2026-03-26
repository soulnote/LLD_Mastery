/**
 * SOLUTION: Shape Factory
 * Factory pattern implementation for shapes
 */

public class Exercise2_Solution {
    
    // Shape interface
    interface Shape {
        double area();
        String getType();
    }
    
    // Circle implementation
    static class Circle implements Shape {
        private double radius;
        
        public Circle(double radius) {
            this.radius = radius;
        }
        
        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
        
        @Override
        public String getType() {
            return "Circle";
        }
    }
    
    // Rectangle implementation
    static class Rectangle implements Shape {
        private double width;
        private double height;
        
        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }
        
        @Override
        public double area() {
            return width * height;
        }
        
        @Override
        public String getType() {
            return "Rectangle";
        }
    }
    
    // Triangle implementation
    static class Triangle implements Shape {
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
        
        @Override
        public String getType() {
            return "Triangle";
        }
    }
    
    // Square - NEW shape added without modifying factory!
    static class Square implements Shape {
        private double side;
        
        public Square(double side) {
            this.side = side;
        }
        
        @Override
        public double area() {
            return side * side;
        }
        
        @Override
        public String getType() {
            return "Square";
        }
    }
    
    // Factory
    static class ShapeFactory {
        public static Shape createShape(String type, double... params) {
            switch (type.toUpperCase()) {
                case "CIRCLE":
                    return new Circle(params[0]);
                case "RECTANGLE":
                    return new Rectangle(params[0], params[1]);
                case "TRIANGLE":
                    return new Triangle(params[0], params[1]);
                case "SQUARE":
                    return new Square(params[0]);
                default:
                    throw new IllegalArgumentException("Unknown shape: " + type);
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("🔺 Shape Factory - SOLUTION\n");
        
        // Create shapes using factory
        Shape circle = ShapeFactory.createShape("CIRCLE", 5);
        Shape rectangle = ShapeFactory.createShape("RECTANGLE", 4, 6);
        Shape triangle = ShapeFactory.createShape("TRIANGLE", 3, 4);
        Shape square = ShapeFactory.createShape("SQUARE", 5);
        
        // Calculate areas
        System.out.println("Circle area: " + String.format("%.2f", circle.area()));
        System.out.println("Rectangle area: " + String.format("%.2f", rectangle.area()));
        System.out.println("Triangle area: " + String.format("%.2f", triangle.area()));
        System.out.println("Square area: " + String.format("%.2f", square.area()));
        
        System.out.println("\n✅ Factory pattern demonstrated!");
    }
}

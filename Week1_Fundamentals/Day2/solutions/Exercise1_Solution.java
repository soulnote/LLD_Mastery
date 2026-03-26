/**
 * SOLUTION: Advanced Calculator System
 * Demonstrates method overloading and polymorphism
 */

public class Exercise1_Solution {
    
    // Calculator interface - defines the contract
    interface Calculator {
        double calculate(String operation, double... operands);
        String getType();
    }
    
    // Basic Calculator - fundamental arithmetic operations
    static class BasicCalculator implements Calculator {
        private String type = "Basic";
        
        @Override
        public double calculate(String operation, double... operands) {
            switch (operation.toUpperCase()) {
                case "ADD":
                    return add(operands);
                case "SUBTRACT":
                    return subtract(operands);
                case "MULTIPLY":
                    return multiply(operands);
                case "DIVIDE":
                    if (operands.length >= 2 && operands[1] != 0) {
                        return operands[0] / operands[1];
                    }
                    throw new ArithmeticException("Cannot divide by zero");
                case "MODULUS":
                    return operands.length >= 2 ? operands[0] % operands[1] : 0;
                default:
                    throw new IllegalArgumentException("Unknown operation: " + operation);
            }
        }
        
        // Method overloading - different parameter types
        public double add(double a, double b) {
            return a + b;
        }
        
        public int add(int a, int b) {
            return a + b;
        }
        
        public double add(double a, double b, double c) {
            return a + b + c;
        }
        
        public double subtract(double a, double b) {
            return a - b;
        }
        
        public double multiply(double... values) {
            double result = 1;
            for (double val : values) {
                result *= val;
            }
            return result;
        }
        
        @Override
        public String getType() {
            return type;
        }
    }
    
    // Scientific Calculator - extends BasicCalculator with scientific operations
    static class ScientificCalculator extends BasicCalculator {
        private String type = "Scientific";
        
        @Override
        public double calculate(String operation, double... operands) {
            // First try basic operations
            try {
                return super.calculate(operation, operands);
            } catch (IllegalArgumentException e) {
                // Then try scientific operations
                return calculateScientific(operation, operands);
            }
        }
        
        private double calculateScientific(String operation, double... operands) {
            switch (operation.toUpperCase()) {
                case "SIN":
                    return Math.sin(Math.toRadians(operands[0]));
                case "COS":
                    return Math.cos(Math.toRadians(operands[0]));
                case "TAN":
                    return Math.tan(Math.toRadians(operands[0]));
                case "LOG":
                    return Math.log10(operands[0]);
                case "LN":
                    return Math.log(operands[0]);
                case "POWER":
                    return operands.length >= 2 ? 
                        Math.pow(operands[0], operands[1]) : 0;
                case "SQRT":
                    return Math.sqrt(operands[0]);
                case "CUBE_ROOT":
                    return Math.cbrt(operands[0]);
                case "ABS":
                    return Math.abs(operands[0]);
                default:
                    throw new IllegalArgumentException("Unknown scientific operation: " + operation);
            }
        }
        
        @Override
        public String getType() {
            return type;
        }
    }
    
    // Programmer Calculator - bitwise operations
    static class ProgrammerCalculator extends BasicCalculator {
        private String type = "Programmer";
        
        @Override
        public double calculate(String operation, double... operands) {
            // First try basic operations
            try {
                return super.calculate(operation, operands);
            } catch (IllegalArgumentException e) {
                // Then try bitwise operations
                return calculateBitwise(operation, operands);
            }
        }
        
        private double calculateBitwise(String operation, double... operands) {
            int a = (int) operands[0];
            int b = operands.length >= 2 ? (int) operands[1] : 0;
            
            switch (operation.toUpperCase()) {
                case "AND":
                    return a & b;
                case "OR":
                    return a | b;
                case "XOR":
                    return a ^ b;
                case "NOT":
                    return ~a;
                case "LEFT_SHIFT":
                    return a << b;
                case "RIGHT_SHIFT":
                    return a >> b;
                case "UNSIGNED_RIGHT_SHIFT":
                    return a >>> b;
                default:
                    throw new IllegalArgumentException("Unknown bitwise operation: " + operation);
            }
        }
        
        // Number base conversions
        public String toBinary(int value) {
            return Integer.toBinaryString(value);
        }
        
        public String toOctal(int value) {
            return Integer.toOctalString(value);
        }
        
        public String toHexadecimal(int value) {
            return Integer.toHexString(value);
        }
        
        public int fromBinary(String binary) {
            return Integer.parseInt(binary, 2);
        }
        
        @Override
        public String getType() {
            return type;
        }
    }
    
    // Factory for creating calculators - demonstrates polymorphism
    static class CalculatorFactory {
        public static Calculator createCalculator(String type) {
            switch (type.toLowerCase()) {
                case "basic":
                    return new BasicCalculator();
                case "scientific":
                    return new ScientificCalculator();
                case "programmer":
                    return new ProgrammerCalculator();
                default:
                    throw new IllegalArgumentException("Unknown calculator type: " + type);
            }
        }
    }
    
    // Main method - demonstrates polymorphism
    public static void main(String[] args) {
        System.out.println("🧮 Advanced Calculator System - SOLUTION\n");
        
        // Demonstrate method overloading
        BasicCalculator basic = new BasicCalculator();
        System.out.println("=== Method Overloading Demo ===");
        System.out.println("add(5, 3) = " + basic.add(5, 3));
        System.out.println("add(5.5, 3.5) = " + basic.add(5.5, 3.5));
        System.out.println("add(1, 2, 3) = " + basic.add(1.0, 2.0, 3.0));
        
        // Demonstrate inheritance + polymorphism
        System.out.println("\n=== Inheritance Demo ===");
        ScientificCalculator scientific = new ScientificCalculator();
        System.out.println("Basic operations work in Scientific: " + scientific.add(10, 5));
        System.out.println("Scientific operations: sin(90) = " + scientific.calculate("SIN", 90));
        System.out.println("sqrt(144) = " + scientific.calculate("SQRT", 144));
        
        // Demonstrate polymorphism through factory
        System.out.println("\n=== Polymorphism with Factory ===");
        Calculator[] calculators = {
            CalculatorFactory.createCalculator("basic"),
            CalculatorFactory.createCalculator("scientific"),
            CalculatorFactory.createCalculator("programmer")
        };
        
        for (Calculator calc : calculators) {
            System.out.println("Calculator Type: " + calc.getType());
            System.out.println("  ADD: " + calc.calculate("ADD", 10, 5));
        }
        
        // Demonstrate programmer calculator
        System.out.println("\n=== Programmer Calculator Demo ===");
        ProgrammerCalculator programmer = new ProgrammerCalculator();
        System.out.println("AND (5 & 3): " + programmer.calculate("AND", 5, 3));
        System.out.println("OR (5 | 3): " + programmer.calculate("OR", 5, 3));
        System.out.println("XOR (5 ^ 3): " + programmer.calculate("XOR", 5, 3));
        System.out.println("Binary of 42: " + programmer.toBinary(42));
        System.out.println("Hex of 255: " + programmer.toHexadecimal(255));
        
        System.out.println("\n✅ All calculator demonstrations complete!");
    }
}

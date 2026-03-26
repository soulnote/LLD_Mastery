/**
 * EXAMPLE: Decorator Pattern
 */
public class DecoratorPattern {
    
    interface Coffee {
        String getDescription();
        double getCost();
    }
    
    static class SimpleCoffee implements Coffee {
        @Override
        public String getDescription() { return "Simple Coffee"; }
        @Override
        public double getCost() { return 2.0; }
    }
    
    static abstract class CoffeeDecorator implements Coffee {
        protected Coffee coffee;
        public CoffeeDecorator(Coffee coffee) { this.coffee = coffee; }
    }
    
    static class MilkDecorator extends CoffeeDecorator {
        public MilkDecorator(Coffee c) { super(c); }
        @Override public String getDescription() { return coffee.getDescription() + ", Milk"; }
        @Override public double getCost() { return coffee.getCost() + 0.5; }
    }
    
    static class SugarDecorator extends CoffeeDecorator {
        public SugarDecorator(Coffee c) { super(c); }
        @Override public String getDescription() { return coffee.getDescription() + ", Sugar"; }
        @Override public double getCost() { return coffee.getCost() + 0.2; }
    }
    
    public static void main(String[] args) {
        Coffee coffee = new SimpleCoffee();
        coffee = new MilkDecorator(coffee);
        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + ": $" + coffee.getCost());
    }
}

/**
 * Inheritance Example: Vehicle Hierarchy
 * Demonstrates IS-A relationship and code reusability
 */
public abstract class Vehicle {
    protected String brand;
    protected String model;
    protected int year;
    protected double price;
    
    public Vehicle(String brand, String model, int year, double price) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
    }
    
    // Common behavior for all vehicles
    public void start() {
        System.out.println(brand + " " + model + " is starting...");
    }
    
    public void stop() {
        System.out.println(brand + " " + model + " has stopped.");
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract void displaySpecs();
    
    // Getters
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
}

/**
 * Car class - inherits from Vehicle
 */
class Car extends Vehicle {
    private int doors;
    private String fuelType;
    
    public Car(String brand, String model, int year, double price, int doors, String fuelType) {
        super(brand, model, year, price); // Call parent constructor
        this.doors = doors;
        this.fuelType = fuelType;
    }
    
    @Override
    public void displaySpecs() {
        System.out.println("=== Car Specifications ===");
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Price: $" + price);
        System.out.println("Doors: " + doors);
        System.out.println("Fuel Type: " + fuelType);
    }
    
    // Car-specific method
    public void honk() {
        System.out.println(brand + " " + model + " is honking: Beep! Beep!");
    }
}

/**
 * Motorcycle class - inherits from Vehicle
 */
class Motorcycle extends Vehicle {
    private int engineCC;
    private boolean hasSidecar;
    
    public Motorcycle(String brand, String model, int year, double price, int engineCC, boolean hasSidecar) {
        super(brand, model, year, price);
        this.engineCC = engineCC;
        this.hasSidecar = hasSidecar;
    }
    
    @Override
    public void displaySpecs() {
        System.out.println("=== Motorcycle Specifications ===");
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Price: $" + price);
        System.out.println("Engine CC: " + engineCC);
        System.out.println("Has Sidecar: " + (hasSidecar ? "Yes" : "No"));
    }
    
    // Motorcycle-specific method
    public void wheelie() {
        System.out.println(brand + " " + model + " is doing a wheelie!");
    }
}
/**
 * SOLUTION: Device Control System
 * Demonstrates Interface Segregation Principle (ISP)
 */

// ✅ ISP: Focused interfaces

interface Printable {
    void print(String document);
    void scan(String document);
}

interface Connectable {
    void connect();
    void disconnect();
}

interface Faxable {
    void sendFax(String number, String message);
    String receiveFax();
}

// Printer: only print and connect capabilities
class Printer implements Printable, Connectable {
    private String model;
    private boolean connected;
    
    public Printer(String model) {
        this.model = model;
        this.connected = false;
    }
    
    @Override
    public void print(String document) {
        System.out.println("🖨️ [" + model + "] Printing: " + document);
    }
    
    @Override
    public void scan(String document) {
        System.out.println("📷 [" + model + "] Scanning: " + document);
    }
    
    @Override
    public void connect() {
        connected = true;
        System.out.println("🔌 [" + model + "] Printer connected");
    }
    
    @Override
    public void disconnect() {
        connected = false;
        System.out.println("🔌 [" + model + "] Printer disconnected");
    }
}

// FaxMachine: all capabilities
class FaxMachine implements Printable, Connectable, Faxable {
    private String number;
    private boolean connected;
    
    public FaxMachine(String number) {
        this.number = number;
        this.connected = false;
    }
    
    @Override
    public void print(String document) {
        System.out.println("🖨️ [Fax-" + number + "] Printing: " + document);
    }
    
    @Override
    public void scan(String document) {
        System.out.println("📷 [Fax-" + number + "] Scanning: " + document);
    }
    
    @Override
    public void connect() {
        connected = true;
        System.out.println("🔌 [Fax-" + number + "] Fax connected");
    }
    
    @Override
    public void disconnect() {
        connected = false;
        System.out.println("🔌 [Fax-" + number + "] Fax disconnected");
    }
    
    @Override
    public void sendFax(String number, String message) {
        System.out.println("📠 Sending fax to: " + number);
        System.out.println("   Message: " + message);
        System.out.println("   ✅ Fax sent!");
    }
    
    @Override
    public String receiveFax() {
        System.out.println("📠 Receiving fax...");
        return "Fax content from " + number;
    }
}

// Scanner: only scan and connect capabilities
class Scanner implements Printable, Connectable {
    private String model;
    private boolean connected;
    
    public Scanner(String model) {
        this.model = model;
        this.connected = false;
    }
    
    @Override
    public void print(String document) {
        // Not supported
        System.out.println("❌ [" + model + "] Printing not supported");
    }
    
    @Override
    public void scan(String document) {
        System.out.println("📷 [" + model + "] Scanning: " + document);
    }
    
    @Override
    public void connect() {
        connected = true;
        System.out.println("🔌 [" + model + "] Scanner connected");
    }
    
    @Override
    public void disconnect() {
        connected = false;
        System.out.println("🔌 [" + model + "] Scanner disconnected");
    }
}

// DeviceController works with different interfaces
class DeviceController {
    
    public static void doPrint(Printable device, String doc) {
        device.print(doc);
    }
    
    public static void doScan(Printable device, String doc) {
        device.scan(doc);
    }
    
    public static void doConnect(Connectable device) {
        device.connect();
    }
    
    public static void doDisconnect(Connectable device) {
        device.disconnect();
    }
    
    public static void doFax(Faxable device, String number, String message) {
        device.sendFax(number, message);
    }
}

public class Exercise1_Solution {
    public static void main(String[] args) {
        System.out.println("🖨️ Device Control System - SOLUTION\n");
        
        // Create devices
        Printer printer = new Printer("HP LaserJet");
        FaxMachine fax = new FaxMachine("555-1234");
        Scanner scanner = new Scanner("Canon");
        
        // Work with printer
        System.out.println("=== Printer ===");
        DeviceController.doConnect(printer);
        DeviceController.doPrint(printer, "Report.pdf");
        DeviceController.doScan(printer, "Photo.jpg");
        DeviceController.doDisconnect(printer);
        
        // Work with fax machine
        System.out.println("\n=== Fax Machine ===");
        DeviceController.doConnect(fax);
        DeviceController.doPrint(fax, "Contract.pdf");
        DeviceController.doFax(fax, "555-5678", "Hello!");
        DeviceController.doDisconnect(fax);
        
        // Work with scanner
        System.out.println("\n=== Scanner ===");
        DeviceController.doConnect(scanner);
        DeviceController.doScan(scanner, "Document.jpg");
        DeviceController.doDisconnect(scanner);
        
        // Demonstrate ISP benefit - different devices implement different interfaces
        System.out.println("\n=== ISP Benefits ===");
        System.out.println("✅ Printer implements Printable + Connectable");
        System.out.println("✅ FaxMachine implements Printable + Connectable + Faxable");
        System.out.println("✅ Scanner implements Printable + Connectable");
        System.out.println("✅ Each device only implements what it needs!");
        
        System.out.println("\n🎉 ISP achieved! Focused interfaces prevent unnecessary dependencies!");
    }
}

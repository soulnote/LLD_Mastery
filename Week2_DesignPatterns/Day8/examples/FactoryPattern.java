/**
 * EXAMPLE: Factory Method Pattern
 * Demonstrates how to use factory to create objects
 */

public class FactoryPattern {
    
    // Product interface
    interface Notification {
        void send(String message);
        String getType();
    }
    
    // Concrete products
    static class EmailNotification implements Notification {
        @Override
        public void send(String message) {
            System.out.println("📧 Email: " + message);
        }
        
        @Override
        public String getType() {
            return "Email";
        }
    }
    
    static class SMSNotification implements Notification {
        @Override
        public void send(String message) {
            System.out.println("📱 SMS: " + message);
        }
        
        @Override
        public String getType() {
            return "SMS";
        }
    }
    
    static class PushNotification implements Notification {
        @Override
        public void send(String message) {
            System.out.println("🔔 Push: " + message);
        }
        
        @Override
        public String getType() {
            return "Push";
        }
    }
    
    // Simple Factory
    static class NotificationFactory {
        public static Notification createNotification(String type) {
            switch (type.toUpperCase()) {
                case "EMAIL":
                    return new EmailNotification();
                case "SMS":
                    return new SMSNotification();
                case "PUSH":
                    return new PushNotification();
                default:
                    throw new IllegalArgumentException("Unknown type: " + type);
            }
        }
    }
    
    // Factory Method Pattern - subclasses decide what to create
    abstract static class Dialog {
        abstract Notification createNotification();
        
        public void notifyUser(String message) {
            Notification notification = createNotification();
            notification.send(message);
        }
    }
    
    static class EmailDialog extends Dialog {
        @Override
        Notification createNotification() {
            return new EmailNotification();
        }
    }
    
    static class SMSDialog extends Dialog {
        @Override
        Notification createNotification() {
            return new SMSNotification();
        }
    }
    
    static class PushDialog extends Dialog {
        @Override
        Notification createNotification() {
            return new PushNotification();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Factory Pattern Demo ===\n");
        
        // Simple Factory
        System.out.println("1. Simple Factory:");
        Notification email = NotificationFactory.createNotification("EMAIL");
        email.send("Hello via Email!");
        
        Notification sms = NotificationFactory.createNotification("SMS");
        sms.send("Hello via SMS!");
        
        // Factory Method
        System.out.println("\n2. Factory Method:");
        Dialog emailDialog = new EmailDialog();
        emailDialog.notifyUser("Hello!");
        
        Dialog smsDialog = new SMSDialog();
        smsDialog.notifyUser("Hello!");
        
        // More flexible - can pass different dialogs
        System.out.println("\n3. Flexible Factory Method:");
        notifyUser(emailDialog, "Welcome!");
        notifyUser(smsDialog, "Welcome!");
        
        System.out.println("\n✅ Factory pattern demonstrated!");
    }
    
    static void notifyUser(Dialog dialog, String message) {
        dialog.notifyUser(message);
    }
}

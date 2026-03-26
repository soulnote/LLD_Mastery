/**
 * SOLUTION: Notification System
 * Demonstrates abstraction with interfaces and polymorphism
 */

// Base notification interface
interface Notification {
    void send(String recipient, String message);
    String getType();
}

// Email notification implementation
class EmailNotification implements Notification {
    private String smtpServer;
    private int port;
    
    public EmailNotification(String smtpServer, int port) {
        this.smtpServer = smtpServer;
        this.port = port;
    }
    
    @Override
    public void send(String recipient, String message) {
        System.out.println("📧 [EMAIL] Sending to: " + recipient);
        System.out.println("   From: mail.example.com");
        System.out.println("   Subject: Notification");
        System.out.println("   Body: " + message);
        System.out.println("   ✅ Email sent successfully!");
    }
    
    @Override
    public String getType() {
        return "Email";
    }
}

// SMS notification implementation
class SMSNotification implements Notification {
    private String gateway;
    
    public SMSNotification(String gateway) {
        this.gateway = gateway;
    }
    
    @Override
    public void send(String recipient, String message) {
        System.out.println("📱 [SMS] Sending to: " + recipient);
        System.out.println("   Gateway: " + gateway);
        System.out.println("   Message: " + message);
        System.out.println("   ✅ SMS sent successfully!");
    }
    
    @Override
    public String getType() {
        return "SMS";
    }
}

// Push notification implementation
class PushNotification implements Notification {
    private String appId;
    
    public PushNotification(String appId) {
        this.appId = appId;
    }
    
    @Override
    public void send(String recipient, String message) {
        System.out.println("🔔 [PUSH] Sending to: " + recipient);
        System.out.println("   App ID: " + appId);
        System.out.println("   Title: Alert");
        System.out.println("   Body: " + message);
        System.out.println("   ✅ Push notification sent!");
    }
    
    @Override
    public String getType() {
        return "Push";
    }
}

// Slack notification implementation
class SlackNotification implements Notification {
    private String webhookUrl;
    private String channel;
    
    public SlackNotification(String webhookUrl, String channel) {
        this.webhookUrl = webhookUrl;
        this.channel = channel;
    }
    
    @Override
    public void send(String recipient, String message) {
        System.out.println("💬 [SLACK] Sending to: #" + channel);
        System.out.println("   Webhook: " + webhookUrl);
        System.out.println("   Message: " + message);
        System.out.println("   ✅ Slack message sent!");
    }
    
    @Override
    public String getType() {
        return "Slack";
    }
}

// Notification sender - demonstrates polymorphism
class NotificationSender {
    private Notification[] notifications;
    
    public NotificationSender(Notification[] notifications) {
        this.notifications = notifications;
    }
    
    // Polymorphic method - works with any Notification type
    public void sendAll(String message) {
        for (Notification notification : notifications) {
            System.out.println("\n--- Sending via " + notification.getType() + " ---");
            notification.send("user@example.com", message);
        }
    }
    
    // Send to specific type
    public void sendToType(String type, String recipient, String message) {
        for (Notification notification : notifications) {
            if (notification.getType().equalsIgnoreCase(type)) {
                notification.send(recipient, message);
                return;
            }
        }
        System.out.println("❌ Notification type not found: " + type);
    }
}

// Factory for creating notifications
class NotificationFactory {
    public static Notification createNotification(String type) {
        return createNotification(type, "");
    }
    
    public static Notification createNotification(String type, String config) {
        switch (type.toLowerCase()) {
            case "email":
                return new EmailNotification("smtp.gmail.com", 587);
            case "sms":
                return new SMSNotification("twilio gateway");
            case "push":
                return new PushNotification("firebase-app-123");
            case "slack":
                return new SlackNotification("https://hooks.slack.com/...", "alerts");
            default:
                throw new IllegalArgumentException("Unknown notification type: " + type);
        }
    }
}

public class Exercise2_Solution {
    public static void main(String[] args) {
        System.out.println("📢 Notification System - SOLUTION\n");
        
        // Create different notification types - demonstrates abstraction
        Notification email = new EmailNotification("smtp.gmail.com", 587);
        Notification sms = new SMSNotification("twilio");
        Notification push = new PushNotification("firebase-123");
        Notification slack = new SlackNotification("webhook-url", "team-alerts");
        
        // Demonstrate polymorphism - one interface, multiple implementations
        System.out.println("=== Polymorphism Demo ===");
        Notification[] allNotifications = {email, sms, push, slack};
        
        for (Notification n : allNotifications) {
            System.out.println("Notification Type: " + n.getType());
            n.send("user@example.com", "Hello from " + n.getType() + "!");
        }
        
        // Use factory to create notifications
        System.out.println("\n=== Factory Pattern Demo ===");
        Notification email2 = NotificationFactory.createNotification("email");
        Notification sms2 = NotificationFactory.createNotification("sms");
        
        email2.send("test@example.com", "Test message");
        
        // Use NotificationSender for bulk sending
        System.out.println("\n=== Bulk Notification Sending ===");
        NotificationSender sender = new NotificationSender(allNotifications);
        sender.sendAll("System maintenance scheduled for tonight!");
        
        System.out.println("\n✅ Notification system demonstration complete!");
    }
}

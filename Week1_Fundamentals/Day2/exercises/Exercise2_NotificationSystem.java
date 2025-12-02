/**
 * EXERCISE 2: Notification System
 * 
 * TASK: Design a flexible notification system using abstraction and polymorphism
 * 
 * Requirements:
 * 1. Create NotificationService interface with send() method
 * 2. Create abstract Notification class with common properties
 * 3. Implement EmailNotification, SMSNotification, PushNotification
 * 4. Each notification type has different formatting and delivery logic
 * 5. Create NotificationManager to handle multiple notification types
 * 6. Add priority levels and retry mechanisms
 * 7. Demonstrate method overloading for different send scenarios
 * 
 * DIFFICULTY: ⭐⭐⭐⭐☆
 * TIME: 45 minutes
 */

public class Exercise2_NotificationSystem {
    
    // TODO: Create NotificationService interface
    interface NotificationService {
        // TODO: Add send methods with different parameters
    }
    
    // TODO: Create Priority enum
    enum Priority {
        // TODO: Add LOW, MEDIUM, HIGH, URGENT
    }
    
    // TODO: Create abstract Notification class
    static abstract class Notification implements NotificationService {
        // TODO: Add protected fields: recipient, message, priority, timestamp
        
        // TODO: Add constructor
        
        // TODO: Add abstract method: formatMessage()
        
        // TODO: Add concrete method: logNotification()
        
        // TODO: Add getters and setters
    }
    
    // TODO: Implement EmailNotification
    static class EmailNotification extends Notification {
        // TODO: Add email-specific fields: subject, attachments
        
        // TODO: Implement constructor
        
        // TODO: Implement formatMessage() - HTML format
        
        // TODO: Implement send() method with email-specific logic
        
        // TODO: Add method overloading for different send scenarios
        // send(String recipient, String message)
        // send(String recipient, String message, String subject)
        // send(String recipient, String message, String subject, List<String> attachments)
    }
    
    // TODO: Implement SMSNotification
    static class SMSNotification extends Notification {
        // TODO: Add SMS-specific fields: phoneNumber, characterLimit
        
        // TODO: Implement constructor
        
        // TODO: Implement formatMessage() - plain text, character limit
        
        // TODO: Implement send() method with SMS-specific logic
        
        // TODO: Add validation for phone number format
    }
    
    // TODO: Implement PushNotification
    static class PushNotification extends Notification {
        // TODO: Add push-specific fields: deviceId, appId, badge count
        
        // TODO: Implement constructor
        
        // TODO: Implement formatMessage() - JSON format
        
        // TODO: Implement send() method with push-specific logic
        
        // TODO: Add device registration validation
    }
    
    // TODO: Create NotificationManager
    static class NotificationManager {
        // TODO: Add list of notification services
        
        // TODO: Add method to register notification service
        
        // TODO: Add method to send notification to all services
        
        // TODO: Add method to send based on priority
        
        // TODO: Add retry mechanism for failed notifications
    }
    
    // Test your implementation
    public static void main(String[] args) {
        System.out.println("📱 Exercise 2: Notification System");
        
        // TODO: Test all notification types
        // Test cases:
        // 1. Create different notification types
        // 2. Test method overloading for each type
        // 3. Create NotificationManager and register services
        // 4. Send notifications with different priorities
        // 5. Test retry mechanism
        // 6. Demonstrate polymorphism with notification array
        
        System.out.println("✅ Complete the implementation and run tests!");
    }
}
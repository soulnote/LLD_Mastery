/**
 * Day 25 - Example: Notification Service Implementation
 * Demonstrates a multi-channel notification service with queuing.
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

// ============================================================
// 1. NOTIFICATION TYPES
// ============================================================

enum NotificationType {
    EMAIL, PUSH, SMS, IN_APP
}

enum NotificationPriority {
    HIGH, NORMAL, LOW
}

enum DeliveryStatus {
    PENDING, SENT, DELIVERED, FAILED
}

// ============================================================
// 2. NOTIFICATION DATA MODEL
// ============================================================

class Notification {
    private final String id;
    private final String userId;
    private final NotificationType type;
    private final String title;
    private final String body;
    private final NotificationPriority priority;
    private final long createdAt;
    private DeliveryStatus status;
    
    public Notification(String userId, NotificationType type, String title, String body) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.type = type;
        this.title = title;
        this.body = body;
        this.priority = NotificationPriority.NORMAL;
        this.createdAt = System.currentTimeMillis();
        this.status = DeliveryStatus.PENDING;
    }
    
    // Getters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public NotificationType getType() { return type; }
    public String getTitle() { return title; }
    public String getBody() { return body; }
    public NotificationPriority getPriority() { return priority; }
    public long getCreatedAt() { return createdAt; }
    public DeliveryStatus getStatus() { return status; }
    public void setStatus(DeliveryStatus status) { this.status = status; }
}

// ============================================================
// 3. NOTIFICATION SERVICE
// ============================================================

class NotificationService {
    private final Map<String, BlockingQueue<Notification>> queues = new ConcurrentHashMap<>();
    private final Map<String, Notification> notifications = new ConcurrentHashMap<>();
    private final ExecutorService executor;
    
    public NotificationService() {
        // Create queues for each notification type
        for (NotificationType type : NotificationType.values()) {
            queues.put(type.name(), new LinkedBlockingQueue<>(10000));
        }
        
        executor = Executors.newFixedThreadPool(4);
        
        // Start workers for each queue
        startWorkers();
    }
    
    private void startWorkers() {
        for (NotificationType type : NotificationType.values()) {
            final NotificationType notificationType = type;
            executor.submit(() -> processQueue(notificationType));
        }
    }
    
    private void processQueue(NotificationType type) {
        BlockingQueue<Notification> queue = queues.get(type.name());
        
        while (true) {
            try {
                Notification notification = queue.take();
                deliverNotification(notification);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    public String sendNotification(String userId, NotificationType type, String title, String body) {
        Notification notification = new Notification(userId, type, title, body);
        notifications.put(notification.getId(), notification);
        
        try {
            queues.get(type.name()).put(notification);
            return notification.getId();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
    
    private void deliverNotification(Notification notification) {
        // Simulate delivery
        System.out.println("Delivering " + notification.getType() + " to user " + notification.getUserId());
        System.out.println("Title: " + notification.getTitle());
        System.out.println("Body: " + notification.getBody());
        
        // Simulate random success/failure
        boolean success = Math.random() > 0.1;
        notification.setStatus(success ? DeliveryStatus.DELIVERED : DeliveryStatus.FAILED);
        
        System.out.println("Status: " + notification.getStatus() + "\n");
    }
    
    public Notification getNotification(String id) {
        return notifications.get(id);
    }
    
    public void shutdown() {
        executor.shutdown();
    }
}

// ============================================================
// DEMO
// ============================================================

public class NotificationServiceExample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Notification Service Demo ===\n");
        
        NotificationService service = new NotificationService();
        
        // Send various notifications
        System.out.println("--- Sending Notifications ---\n");
        
        service.sendNotification("user1", NotificationType.EMAIL, "Welcome!", "Welcome to our platform");
        service.sendNotification("user2", NotificationType.PUSH, "New Message", "You have a new message");
        service.sendNotification("user3", NotificationType.SMS, "OTP", "Your OTP is 123456");
        service.sendNotification("user4", NotificationType.IN_APP, "Alert", "Please update your profile");
        
        // High priority notification
        service.sendNotification("user1", NotificationType.SMS, "URGENT", "Critical security alert");
        
        // Wait for processing
        Thread.sleep(2000);
        
        // Check notification status
        System.out.println("--- Checking Status ---");
        System.out.println("Notification sent successfully!");
        
        service.shutdown();
    }
}

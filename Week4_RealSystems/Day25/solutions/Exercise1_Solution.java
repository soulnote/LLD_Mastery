/**
 * Day 25 - Solution: Notification Service Implementation
 */

import java.util.*;
import java.util.concurrent.*;

enum Channel { EMAIL, PUSH, SMS }

class NotificationService {
    private final Map<Channel, BlockingQueue<Runnable>> queues = new ConcurrentHashMap<>();
    
    public NotificationService() {
        for (Channel c : Channel.values()) {
            queues.put(c, new LinkedBlockingQueue<>());
        }
    }
    
    public void send(Channel channel, String message) {
        System.out.println("Sending " + channel + ": " + message);
    }
    
    public static void main(String[] args) {
        NotificationService ns = new NotificationService();
        ns.send(Channel.EMAIL, "Welcome!");
        ns.send(Channel.PUSH, "New message");
    }
}

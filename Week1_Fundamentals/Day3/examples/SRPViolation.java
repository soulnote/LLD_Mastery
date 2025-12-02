/**
 * SRP VIOLATION Example: User Management System
 * This class violates SRP by handling multiple responsibilities
 */

import java.util.*;
import java.util.regex.Pattern;

// ❌ BAD: This class has multiple responsibilities
class UserManager {
    private List<User> users = new ArrayList<>();
    
    // Responsibility 1: User data management
    public void addUser(User user) {
        users.add(user);
    }
    
    public User findUserById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    // Responsibility 2: User validation
    public boolean validateUser(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return false;
        }
        
        if (user.getEmail() == null || !isValidEmail(user.getEmail())) {
            return false;
        }
        
        if (user.getPassword() == null || user.getPassword().length() < 8) {
            return false;
        }
        
        return true;
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    
    // Responsibility 3: Password management
    public String hashPassword(String password) {
        // Simplified hashing (in real world, use proper hashing)
        return "hashed_" + password + "_salt";
    }
    
    public boolean verifyPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
    
    // Responsibility 4: Email notifications
    public void sendWelcomeEmail(User user) {
        System.out.println("📧 Sending welcome email to: " + user.getEmail());
        System.out.println("Subject: Welcome to our platform!");
        System.out.println("Dear " + user.getName() + ", welcome aboard!");
    }
    
    public void sendPasswordResetEmail(User user, String resetToken) {
        System.out.println("📧 Sending password reset email to: " + user.getEmail());
        System.out.println("Subject: Password Reset Request");
        System.out.println("Reset token: " + resetToken);
    }
    
    // Responsibility 5: User statistics and reporting
    public void generateUserReport() {
        System.out.println("📊 USER STATISTICS REPORT");
        System.out.println("Total Users: " + users.size());
        
        long activeUsers = users.stream()
                .filter(User::isActive)
                .count();
        System.out.println("Active Users: " + activeUsers);
        
        Map<String, Long> usersByDomain = users.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    user -> user.getEmail().split("@")[1],
                    java.util.stream.Collectors.counting()
                ));
        
        System.out.println("Users by Domain:");
        usersByDomain.forEach((domain, count) -> 
            System.out.println("  " + domain + ": " + count));
    }
    
    // Responsibility 6: Data persistence
    public void saveUsersToFile(String filename) {
        System.out.println("💾 Saving " + users.size() + " users to file: " + filename);
        // Simulate file saving
        for (User user : users) {
            System.out.println("Saving: " + user.getId() + " - " + user.getName());
        }
    }
    
    public void loadUsersFromFile(String filename) {
        System.out.println("📂 Loading users from file: " + filename);
        // Simulate file loading
        System.out.println("Users loaded successfully");
    }
}

// User entity class
class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private boolean active;
    private Date createdAt;
    
    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = true;
        this.createdAt = new Date();
    }
    
    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public boolean isActive() { return active; }
    public Date getCreatedAt() { return createdAt; }
    
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setActive(boolean active) { this.active = active; }
}

// Test class to demonstrate the violation
public class SRPViolation {
    public static void main(String[] args) {
        System.out.println("❌ SRP VIOLATION EXAMPLE\n");
        
        UserManager userManager = new UserManager();
        
        // Create users
        User user1 = new User("1", "John Doe", "john@example.com", "password123");
        User user2 = new User("2", "Jane Smith", "jane@gmail.com", "securepass");
        
        // The UserManager class is doing too many things!
        System.out.println("🔍 Problems with this design:");
        System.out.println("1. UserManager has 6 different responsibilities");
        System.out.println("2. Changes in email logic affect the entire class");
        System.out.println("3. Hard to test individual components");
        System.out.println("4. Violates Single Responsibility Principle");
        System.out.println("5. Low cohesion, high coupling");
        
        System.out.println("\n📝 What this class is responsible for:");
        System.out.println("• User data management");
        System.out.println("• User validation");
        System.out.println("• Password hashing");
        System.out.println("• Email notifications");
        System.out.println("• Report generation");
        System.out.println("• Data persistence");
        
        System.out.println("\n💡 Solution: Break this into separate classes!");
        System.out.println("Check SRPSolution.java for the refactored version.");
    }
}
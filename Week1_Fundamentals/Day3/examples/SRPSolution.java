/**
 * SRP SOLUTION: Refactored User Management System
 * Each class now has a single responsibility
 */

import java.util.*;
import java.util.regex.Pattern;

// ✅ GOOD: Single responsibility - User data management
class UserRepository {
    private List<User> users = new ArrayList<>();
    
    public void addUser(User user) {
        users.add(user);
        System.out.println("✅ User added: " + user.getName());
    }
    
    public User findUserById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    public boolean removeUser(String id) {
        return users.removeIf(user -> user.getId().equals(id));
    }
    
    public void updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                break;
            }
        }
    }
}

// ✅ GOOD: Single responsibility - User validation
class UserValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    public ValidationResult validateUser(User user) {
        ValidationResult result = new ValidationResult();
        
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            result.addError("Name cannot be empty");
        }
        
        if (user.getEmail() == null || !isValidEmail(user.getEmail())) {
            result.addError("Invalid email format");
        }
        
        if (user.getPassword() == null || user.getPassword().length() < 8) {
            result.addError("Password must be at least 8 characters");
        }
        
        return result;
    }
    
    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    public boolean isValidPassword(String password) {
        return password != null && 
               password.length() >= 8 && 
               password.matches(".*[A-Z].*") && 
               password.matches(".*[a-z].*") && 
               password.matches(".*\\d.*");
    }
}

// ✅ GOOD: Single responsibility - Password management
class PasswordManager {
    private static final String SALT = "myapp_salt_2023";
    
    public String hashPassword(String password) {
        // Simplified hashing (use BCrypt in production)
        return "hashed_" + password + "_" + SALT;
    }
    
    public boolean verifyPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
    
    public String generateResetToken() {
        return "reset_" + UUID.randomUUID().toString();
    }
    
    public boolean isTokenValid(String token) {
        // Simplified token validation
        return token != null && token.startsWith("reset_");
    }
}

// ✅ GOOD: Single responsibility - Email notifications
class EmailService {
    public void sendWelcomeEmail(User user) {
        System.out.println("📧 WELCOME EMAIL");
        System.out.println("To: " + user.getEmail());
        System.out.println("Subject: Welcome to our platform!");
        System.out.println("Dear " + user.getName() + ",");
        System.out.println("Welcome aboard! We're excited to have you.");
        System.out.println("Email sent successfully ✅");
    }
    
    public void sendPasswordResetEmail(User user, String resetToken) {
        System.out.println("📧 PASSWORD RESET EMAIL");
        System.out.println("To: " + user.getEmail());
        System.out.println("Subject: Password Reset Request");
        System.out.println("Dear " + user.getName() + ",");
        System.out.println("Your reset token: " + resetToken);
        System.out.println("Email sent successfully ✅");
    }
    
    public void sendAccountActivationEmail(User user, String activationLink) {
        System.out.println("📧 ACTIVATION EMAIL");
        System.out.println("To: " + user.getEmail());
        System.out.println("Subject: Activate Your Account");
        System.out.println("Activation link: " + activationLink);
        System.out.println("Email sent successfully ✅");
    }
}

// ✅ GOOD: Single responsibility - User statistics and reporting
class UserReportService {
    private UserRepository userRepository;
    
    public UserReportService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public UserStatistics generateUserStatistics() {
        List<User> users = userRepository.getAllUsers();
        
        long totalUsers = users.size();
        long activeUsers = users.stream().filter(User::isActive).count();
        
        Map<String, Long> usersByDomain = users.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    user -> user.getEmail().split("@")[1],
                    java.util.stream.Collectors.counting()
                ));
        
        return new UserStatistics(totalUsers, activeUsers, usersByDomain);
    }
    
    public void printUserReport() {
        UserStatistics stats = generateUserStatistics();
        
        System.out.println("📊 USER STATISTICS REPORT");
        System.out.println("Total Users: " + stats.getTotalUsers());
        System.out.println("Active Users: " + stats.getActiveUsers());
        System.out.println("Users by Domain:");
        stats.getUsersByDomain().forEach((domain, count) -> 
            System.out.println("  " + domain + ": " + count));
    }
}

// ✅ GOOD: Single responsibility - Data persistence
class UserPersistenceService {
    public void saveUsersToFile(List<User> users, String filename) {
        System.out.println("💾 SAVING TO FILE: " + filename);
        System.out.println("Saving " + users.size() + " users...");
        
        for (User user : users) {
            System.out.println("Saved: " + user.getId() + " - " + user.getName());
        }
        System.out.println("File saved successfully ✅");
    }
    
    public List<User> loadUsersFromFile(String filename) {
        System.out.println("📂 LOADING FROM FILE: " + filename);
        
        // Simulate loading users
        List<User> users = Arrays.asList(
            new User("loaded1", "Loaded User 1", "loaded1@example.com", "password"),
            new User("loaded2", "Loaded User 2", "loaded2@example.com", "password")
        );
        
        System.out.println("Loaded " + users.size() + " users successfully ✅");
        return users;
    }
}

// Supporting classes
class ValidationResult {
    private List<String> errors = new ArrayList<>();
    
    public void addError(String error) {
        errors.add(error);
    }
    
    public boolean isValid() {
        return errors.isEmpty();
    }
    
    public List<String> getErrors() {
        return errors;
    }
}

class UserStatistics {
    private long totalUsers;
    private long activeUsers;
    private Map<String, Long> usersByDomain;
    
    public UserStatistics(long totalUsers, long activeUsers, Map<String, Long> usersByDomain) {
        this.totalUsers = totalUsers;
        this.activeUsers = activeUsers;
        this.usersByDomain = usersByDomain;
    }
    
    public long getTotalUsers() { return totalUsers; }
    public long getActiveUsers() { return activeUsers; }
    public Map<String, Long> getUsersByDomain() { return usersByDomain; }
}

// ✅ GOOD: Orchestrator class that coordinates different services
class UserService {
    private UserRepository userRepository;
    private UserValidator userValidator;
    private PasswordManager passwordManager;
    private EmailService emailService;
    
    public UserService(UserRepository userRepository, UserValidator userValidator,
                      PasswordManager passwordManager, EmailService emailService) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.passwordManager = passwordManager;
        this.emailService = emailService;
    }
    
    public boolean registerUser(User user) {
        // Validate user
        ValidationResult validation = userValidator.validateUser(user);
        if (!validation.isValid()) {
            System.out.println("❌ Validation failed:");
            validation.getErrors().forEach(error -> System.out.println("  • " + error));
            return false;
        }
        
        // Hash password
        String hashedPassword = passwordManager.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        
        // Save user
        userRepository.addUser(user);
        
        // Send welcome email
        emailService.sendWelcomeEmail(user);
        
        return true;
    }
}

// Test class demonstrating the SRP solution
public class SRPSolution {
    public static void main(String[] args) {
        System.out.println("✅ SRP SOLUTION EXAMPLE\n");
        
        // Create all services (dependency injection in real apps)
        UserRepository userRepository = new UserRepository();
        UserValidator userValidator = new UserValidator();
        PasswordManager passwordManager = new PasswordManager();
        EmailService emailService = new EmailService();
        UserReportService reportService = new UserReportService(userRepository);
        UserPersistenceService persistenceService = new UserPersistenceService();
        
        // Create orchestrator service
        UserService userService = new UserService(userRepository, userValidator, 
                                                 passwordManager, emailService);
        
        System.out.println("🎯 Benefits of SRP refactoring:");
        System.out.println("1. Each class has a single responsibility");
        System.out.println("2. Easy to test individual components");
        System.out.println("3. Changes in one area don't affect others");
        System.out.println("4. High cohesion, low coupling");
        System.out.println("5. Better code organization and maintainability\n");
        
        // Test the refactored system
        User user1 = new User("1", "John Doe", "john@example.com", "SecurePass123");
        User user2 = new User("2", "Jane Smith", "jane@gmail.com", "weak"); // Invalid password
        
        System.out.println("=== TESTING REFACTORED SYSTEM ===\n");
        
        // Test user registration
        System.out.println("1. Registering valid user:");
        userService.registerUser(user1);
        
        System.out.println("\n2. Registering invalid user:");
        userService.registerUser(user2);
        
        // Test individual services
        System.out.println("\n3. Generating user report:");
        reportService.printUserReport();
        
        System.out.println("\n4. Testing persistence:");
        persistenceService.saveUsersToFile(userRepository.getAllUsers(), "users.txt");
        
        System.out.println("\n🎉 SRP principles successfully demonstrated!");
        System.out.println("💡 Each class now has a single, well-defined responsibility!");
    }
}
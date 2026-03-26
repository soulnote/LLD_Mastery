/**
 * SOLUTION: User Service Refactoring
 * Demonstrates Single Responsibility Principle (SRP)
 */

// RESPONSIBILITY 1: Only validates user data
class UserValidator {
    public void validate(String name, String email, String password) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
    }
    
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}

// RESPONSIBILITY 2: Only handles password hashing
class PasswordHasher {
    public String hash(String password) {
        // Simplified - in production use BCrypt, Argon2, etc.
        int hash = password.hashCode();
        return "bcrypt_" + Math.abs(hash);
    }
}

// RESPONSIBILITY 3: Only handles database operations
class UserRepository {
    public void save(String name, String email, String hashedPassword) {
        System.out.println("💾 [Database] Saving user: " + name);
        System.out.println("   SQL: INSERT INTO users (name, email, password) VALUES (?, ?, ?)");
        System.out.println("   ✅ User saved successfully!");
    }
    
    public void findByEmail(String email) {
        System.out.println("💾 [Database] Finding user by email: " + email);
    }
    
    public void delete(String email) {
        System.out.println("💾 [Database] Deleting user: " + email);
    }
}

// RESPONSIBILITY 4: Only handles email sending
class EmailService {
    public void sendWelcomeEmail(String email, String name) {
        System.out.println("📧 [Email] Sending welcome email to: " + email);
        System.out.println("   Subject: Welcome to our platform, " + name + "!");
        System.out.println("   Body: Thank you for joining us. Enjoy your experience!");
        System.out.println("   ✅ Email sent!");
    }
    
    public void sendPasswordResetEmail(String email, String resetToken) {
        System.out.println("📧 [Email] Sending password reset to: " + email);
        System.out.println("   Token: " + resetToken);
    }
}

// RESPONSIBILITY 5: Only handles logging
class Logger {
    private static Logger instance;
    
    private Logger() {}
    
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    public void info(String message) {
        System.out.println("📝 [INFO] " + message);
    }
    
    public void error(String message, Exception e) {
        System.out.println("❌ [ERROR] " + message);
        if (e != null) {
            System.out.println("   Exception: " + e.getMessage());
        }
    }
    
    public void debug(String message) {
        System.out.println("🔍 [DEBUG] " + message);
    }
}

// REFACTORED: UserService now has only ONE responsibility - orchestrates user creation
class UserService {
    private UserValidator validator;
    private PasswordHasher passwordHasher;
    private UserRepository repository;
    private EmailService emailService;
    private Logger logger;
    
    // Dependency injection - follows DIP (Dependency Inversion Principle)
    public UserService(UserValidator validator, 
                       PasswordHasher passwordHasher,
                       UserRepository repository,
                       EmailService emailService) {
        this.validator = validator;
        this.passwordHasher = passwordHasher;
        this.repository = repository;
        this.emailService = emailService;
        this.logger = Logger.getInstance();
    }
    
    // Main business logic - coordinates other classes
    public void createUser(String name, String email, String password) {
        logger.info("Creating user: " + name);
        
        // Validation (UserValidator's responsibility)
        validator.validate(name, email, password);
        
        // Password hashing (PasswordHasher's responsibility)
        String hashedPassword = passwordHasher.hash(password);
        
        // Database save (UserRepository's responsibility)
        repository.save(name, email, hashedPassword);
        
        // Send email (EmailService's responsibility)
        emailService.sendWelcomeEmail(email, name);
        
        logger.info("User created successfully: " + name);
    }
}

public class Exercise1_Solution {
    public static void main(String[] args) {
        System.out.println("🔧 User Service Refactoring - SOLUTION\n");
        
        // Each class has ONE responsibility
        System.out.println("=== Demonstrating Single Responsibility ===\n");
        
        // Create dependencies
        UserValidator validator = new UserValidator();
        PasswordHasher hasher = new PasswordHasher();
        UserRepository repository = new UserRepository();
        EmailService emailService = new EmailService();
        
        // Inject dependencies into UserService
        UserService userService = new UserService(
            validator, hasher, repository, emailService
        );
        
        // Create user - each component does its own job
        System.out.println("--- Creating User ---");
        userService.createUser("John Doe", "john@example.com", "password123");
        
        System.out.println("\n=== Benefits of SRP ===");
        System.out.println("✅ UserValidator can change validation rules without touching other classes");
        System.out.println("✅ PasswordHasher can switch to a better algorithm independently");
        System.out.println("✅ UserRepository can change database without affecting email logic");
        System.out.println("✅ EmailService can switch email providers easily");
        System.out.println("✅ Each class is easier to test in isolation");
        
        System.out.println("\n✅ SRP Refactoring complete!");
    }
}

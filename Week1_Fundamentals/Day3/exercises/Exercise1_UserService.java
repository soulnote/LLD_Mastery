/**
 * EXERCISE 1: User Service Refactoring
 * 
 * TASK: Identify and fix SRP violations in the UserService class
 * 
 * Requirements:
 * 1. Analyze the UserService class below
 * 2. Identify all responsibilities (violations)
 * 3. Extract each responsibility into its own class
 * 4. Ensure each class has only ONE reason to change
 * 
 * Responsibilities to extract:
 * - User data management
 * - Email validation and sending
 * - Password hashing
 * - Database persistence
 * - Logging
 * 
 * DIFFICULTY: ⭐⭐☆☆☆
 * TIME: 25 minutes
 */

public class Exercise1_UserService {
    
    // ❌ VIOLATION: This class has too many responsibilities!
    // It handles user management, email, password, database, and logging
    static class UserService {
        private String name;
        private String email;
        private String password;
        
        public void createUser(String name, String email, String password) {
            // User data validation
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            if (!email.contains("@")) {
                throw new IllegalArgumentException("Invalid email format");
            }
            if (password.length() < 8) {
                throw new IllegalArgumentException("Password too short");
            }
            
            // Password hashing - should be its own class
            String hashedPassword = hashPassword(password);
            
            // Email validation - should be its own class
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Email not valid");
            }
            
            // Database save - should be its own class
            saveToDatabase(name, email, hashedPassword);
            
            // Send welcome email - should be its own class
            sendWelcomeEmail(email, name);
            
            // Logging - should be its own class
            System.out.println("User created: " + name + " at " + System.currentTimeMillis());
        }
        
        // ❌ RESPONSIBILITY: Password hashing
        private String hashPassword(String password) {
            // Simplified - in real code use BCrypt or similar
            int hash = password.hashCode();
            return "hashed_" + Math.abs(hash);
        }
        
        // ❌ RESPONSIBILITY: Email validation
        private boolean isValidEmail(String email) {
            return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
        }
        
        // ❌ RESPONSIBILITY: Database operations
        private void saveToDatabase(String name, String email, String password) {
            // Simulated database save
            System.out.println("INSERT INTO users VALUES ('" + name + "', '" + email + "', '" + password + "')");
        }
        
        // ❌ RESPONSIBILITY: Email sending
        private void sendWelcomeEmail(String email, String name) {
            System.out.println("Sending email to: " + email);
            System.out.println("Subject: Welcome " + name);
            System.out.println("Body: Welcome to our platform!");
        }
    }
    
    // TODO: Create separate classes for each responsibility
    
    // TODO: UserValidator - validate user data (name, email format)
    // TODO: PasswordHasher - handle password hashing
    // TODO: UserRepository - handle database operations
    // TODO: EmailService - handle email sending
    // TODO: Logger - handle logging
    
    // TODO: Create refactored UserService that uses these classes
    
    // Test your refactored solution
    public static void main(String[] args) {
        System.out.println("🔧 Exercise 1: User Service Refactoring\n");
        
        // Old way - multiple responsibilities in one class
        System.out.println("=== OLD: SRP Violation ===");
        UserService oldService = new UserService();
        oldService.createUser("John Doe", "john@example.com", "password123");
        
        // TODO: Test your refactored solution
        // Create instances of your new classes and test
        
        System.out.println("\n✅ Complete the refactoring!");
    }
}

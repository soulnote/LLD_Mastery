/**
 * EXAMPLE: Dependency Inversion Principle Violation
 * 
 * This code VIOLATES DIP - high-level module depends on low-level module
 */

public class DIPViolation {
    
    // ❌ VIOLATION: Low-level module (concrete class)
    class MySQLDatabase {
        public void connect() {
            System.out.println("🔌 Connecting to MySQL database...");
        }
        
        public void save(String data) {
            System.out.println("💾 Saving to MySQL: " + data);
        }
        
        public void query(String sql) {
            System.out.println("🔍 Querying MySQL: " + sql);
        }
    }
    
    // ❌ VIOLATION: High-level module depends on concrete low-level module
    class UserService {
        // Directly depends on MySQLDatabase - VIOLATES DIP!
        private MySQLDatabase database = new MySQLDatabase();
        
        public void createUser(String name) {
            database.connect();
            database.save("User: " + name);
            System.out.println("✅ User created: " + name);
        }
        
        public void getUser(int id) {
            database.connect();
            database.query("SELECT * FROM users WHERE id = " + id);
        }
    }
    
    // ❌ PROBLEM: To switch to PostgreSQL, we must modify UserService!
    // This tightly couples UserService to MySQLDatabase
    
    public static void main(String[] args) {
        System.out.println("❌ DIP Violation Demo\n");
        
        UserService userService = new UserService();
        
        userService.createUser("John");
        userService.getUser(1);
        
        System.out.println("\n⚠️ PROBLEM: UserService is tightly coupled to MySQLDatabase!");
        System.out.println("   To change database, we must modify UserService code!");
    }
}

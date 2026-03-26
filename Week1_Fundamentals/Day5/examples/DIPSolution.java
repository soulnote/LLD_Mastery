/**
 * EXAMPLE: Dependency Inversion Principle Solution
 * 
 * This code FOLLOWS DIP - depends on abstractions, not concretions
 */

public class DIPSolution {
    
    // ✅ SOLUTION: Abstract dependency (interface)
    interface Database {
        void connect();
        void save(String data);
        void query(String sql);
    }
    
    // Low-level module 1: MySQL
    class MySQLDatabase implements Database {
        @Override
        public void connect() {
            System.out.println("🔌 Connecting to MySQL...");
        }
        
        @Override
        public void save(String data) {
            System.out.println("💾 Saving to MySQL: " + data);
        }
        
        @Override
        public void query(String sql) {
            System.out.println("🔍 Querying MySQL: " + sql);
        }
    }
    
    // Low-level module 2: PostgreSQL - EASY TO ADD!
    class PostgreSQLDatabase implements Database {
        @Override
        public void connect() {
            System.out.println("🔌 Connecting to PostgreSQL...");
        }
        
        @Override
        public void save(String data) {
            System.out.println("💾 Saving to PostgreSQL: " + data);
        }
        
        @Override
        public void query(String sql) {
            System.out.println("🔍 Querying PostgreSQL: " + sql);
        }
    }
    
    // Low-level module 3: MongoDB - EASY TO ADD!
    class MongoDBDatabase implements Database {
        @Override
        public void connect() {
            System.out.println("🔌 Connecting to MongoDB...");
        }
        
        @Override
        public void save(String data) {
            System.out.println("💾 Saving to MongoDB: " + data);
        }
        
        @Override
        public void query(String sql) {
            System.out.println("🔍 Querying MongoDB: " + sql);
        }
    }
    
    // ✅ SOLUTION: High-level module depends on abstraction (interface)
    // Dependency is INJECTED - not created internally!
    class UserService {
        // Depends on abstraction, not concretion
        private Database database;
        
        // ✅ Constructor injection - Dependency Inversion!
        public UserService(Database database) {
            this.database = database;
        }
        
        public void createUser(String name) {
            database.connect();
            database.save("User: " + name);
            System.out.println("✅ User created: " + name);
        }
        
        public void getUser(int id) {
            database.connect();
            database.query("SELECT * FROM users WHERE id = " + id);
        }
        
        // Can change database at runtime!
        public void setDatabase(Database database) {
            this.database = database;
        }
    }
    
    // Test the solution
    public static void main(String[] args) {
        System.out.println("✅ DIP Solution Demo\n");
        
        // UserService doesn't know which database it uses!
        // The dependency is injected from outside
        
        // Use MySQL
        System.out.println("=== Using MySQL ===");
        Database mysql = new DIPSolution().new MySQLDatabase();
        UserService mysqlService = new DIPSolution().new UserService(mysql);
        mysqlService.createUser("John");
        
        // Switch to PostgreSQL - NO CHANGE to UserService!
        System.out.println("\n=== Using PostgreSQL ===");
        Database postgres = new DIPSolution().new PostgreSQLDatabase();
        UserService postgresService = new DIPSolution().new UserService(postgres);
        postgresService.createUser("Jane");
        
        // Switch to MongoDB - EASY!
        System.out.println("\n=== Using MongoDB ===");
        Database mongo = new DIPSolution().new MongoDBDatabase();
        UserService mongoService = new DIPSolution().new UserService(mongo);
        mongoService.createUser("Bob");
        
        // Or change database at runtime
        System.out.println("\n=== Runtime Database Switch ===");
        UserService service = new DIPSolution().new UserService(mysql);
        service.createUser("Alice");
        service.setDatabase(mongo); // Change at runtime!
        service.createUser("Charlie");
        
        System.out.println("\n🎉 DIP achieved! High-level module doesn't depend on low-level modules!");
        System.out.println("   Both depend on abstractions (Database interface)!");
    }
}

/**
 * EXAMPLE: Singleton Pattern
 * Demonstrates different ways to implement Singleton
 */

public class SingletonPattern {
    
    // ============================================
    // 1. Eager Initialization
    // ============================================
    /*
     * PROS: Simple, thread-safe
     * CONS: Instance created even if not used
     */
    static class EagerSingleton {
        // Instance created at class loading time
        private static final EagerSingleton instance = new EagerSingleton();
        
        private EagerSingleton() {
            // Private constructor prevents instantiation
            System.out.println("EagerSingleton created");
        }
        
        public static EagerSingleton getInstance() {
            return instance;
        }
        
        public void doSomething() {
            System.out.println("EagerSingleton doing something");
        }
    }
    
    // ============================================
    // 2. Lazy Initialization
    // ============================================
    /*
     * PROS: Created only when needed
     * CONS: Not thread-safe
     */
    static class LazySingleton {
        private static LazySingleton instance;
        
        private LazySingleton() {
            System.out.println("LazySingleton created");
        }
        
        public static LazySingleton getInstance() {
            if (instance == null) {
                instance = new LazySingleton();
            }
            return instance;
        }
    }
    
    // ============================================
    // 3. Thread-Safe (Synchronized)
    // ============================================
    /*
     * PROS: Thread-safe
     * CONS: Performance overhead due to synchronization
     */
    static class ThreadSafeSingleton {
        private static ThreadSafeSingleton instance;
        
        private ThreadSafeSingleton() {
            System.out.println("ThreadSafeSingleton created");
        }
        
        public static synchronized ThreadSafeSingleton getInstance() {
            if (instance == null) {
                instance = new ThreadSafeSingleton();
            }
            return instance;
        }
    }
    
    // ============================================
    // 4. Double-Checked Locking (Best Practice)
    // ============================================
    /*
     * PROS: Thread-safe, lazy, efficient
     * CONS: Slightly complex
     */
    static class DoubleCheckedSingleton {
        // volatile prevents instruction reordering
        private static volatile DoubleCheckedSingleton instance;
        
        private DoubleCheckedSingleton() {
            System.out.println("DoubleCheckedSingleton created");
        }
        
        public static DoubleCheckedSingleton getInstance() {
            if (instance == null) {
                synchronized (DoubleCheckedSingleton.class) {
                    if (instance == null) {
                        instance = new DoubleCheckedSingleton();
                    }
                }
            }
            return instance;
        }
    }
    
    // ============================================
    // 5. Bill Pugh - Inner Static Class
    // ============================================
    /*
     * PROS: Lazy, thread-safe, no synchronization
     * CONS: Uses nested class
     */
    static class BillPughSingleton {
        private BillPughSingleton() {
            System.out.println("BillPughSingleton created");
        }
        
        // Inner static class - loaded only when getInstance is called
        private static class SingletonHelper {
            private static final BillPughSingleton INSTANCE = new BillPughSingleton();
        }
        
        public static BillPughSingleton getInstance() {
            return SingletonHelper.INSTANCE;
        }
    }
    
    // ============================================
    // 6. Enum Singleton (Best for Java)
    // ============================================
    /*
     * PROS: Thread-safe, serialization guaranteed, reflection safe
     * CONS: Cannot extend other classes
     */
    enum EnumSingleton {
        INSTANCE;
        
        public void doSomething() {
            System.out.println("EnumSingleton doing something");
        }
    }
    
    // ============================================
    // Real-World Example: Database Connection
    // ============================================
    static class DatabaseConnection {
        private static DatabaseConnection instance;
        private String connectionString;
        
        private DatabaseConnection() {
            this.connectionString = "jdbc:mysql://localhost:3306/mydb";
            System.out.println("Database connection established");
        }
        
        public static synchronized DatabaseConnection getInstance() {
            if (instance == null) {
                instance = new DatabaseConnection();
            }
            return instance;
        }
        
        public void query(String sql) {
            System.out.println("Executing: " + sql);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Singleton Pattern Demo ===\n");
        
        // Eager Singleton
        System.out.println("1. Eager Initialization:");
        EagerSingleton eager1 = EagerSingleton.getInstance();
        EagerSingleton eager2 = EagerSingleton.getInstance();
        System.out.println("Same instance? " + (eager1 == eager2));
        
        // Lazy Singleton
        System.out.println("\n2. Lazy Initialization:");
        LazySingleton lazy1 = LazySingleton.getInstance();
        LazySingleton lazy2 = LazySingleton.getInstance();
        System.out.println("Same instance? " + (lazy1 == lazy2));
        
        // Double-Checked Locking
        System.out.println("\n3. Double-Checked Locking:");
        DoubleCheckedSingleton dcl1 = DoubleCheckedSingleton.getInstance();
        DoubleCheckedSingleton dcl2 = DoubleCheckedSingleton.getInstance();
        System.out.println("Same instance? " + (dcl1 == dcl2));
        
        // Bill Pugh
        System.out.println("\n4. Bill Pugh Singleton:");
        BillPughSingleton bp1 = BillPughSingleton.getInstance();
        BillPughSingleton bp2 = BillPughSingleton.getInstance();
        System.out.println("Same instance? " + (bp1 == bp2));
        
        // Enum
        System.out.println("\n5. Enum Singleton:");
        EnumSingleton enum1 = EnumSingleton.INSTANCE;
        EnumSingleton enum2 = EnumSingleton.INSTANCE;
        System.out.println("Same instance? " + (enum1 == enum2));
        
        // Real-world usage
        System.out.println("\n=== Real-World Example ===");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        System.out.println("Same instance? " + (db1 == db2));
        db1.query("SELECT * FROM users");
        
        System.out.println("\n✅ Singleton pattern demonstrated!");
    }
}

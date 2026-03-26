/**
 * SOLUTION: Logger Singleton
 * Thread-safe singleton logger implementation
 */

public class Exercise1_Solution {
    
    // Log levels
    enum LogLevel {
        INFO, DEBUG, ERROR
    }
    
    // Thread-safe Singleton Logger
    static class Logger {
        // Volatile ensures visibility across threads
        private static volatile Logger instance;
        private LogLevel minLevel;
        
        // Private constructor
        private Logger() {
            this.minLevel = LogLevel.DEBUG; // Default level
        }
        
        // Double-checked locking for thread safety
        public static Logger getInstance() {
            if (instance == null) {
                synchronized (Logger.class) {
                    if (instance == null) {
                        instance = new Logger();
                    }
                }
            }
            return instance;
        }
        
        public void setLevel(LogLevel level) {
            this.minLevel = level;
        }
        
        public void log(LogLevel level, String message) {
            if (level.ordinal() >= minLevel.ordinal()) {
                System.out.println("[" + level + "] " + java.time.LocalDateTime.now() + ": " + message);
            }
        }
        
        public void info(String message) {
            log(LogLevel.INFO, message);
        }
        
        public void debug(String message) {
            log(LogLevel.DEBUG, message);
        }
        
        public void error(String message) {
            log(LogLevel.ERROR, message);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("📝 Logger Singleton - SOLUTION\n");
        
        // Get logger instance
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        
        // Verify singleton
        System.out.println("Same instance? " + (logger1 == logger2));
        
        // Test logging
        System.out.println("\n=== Logging Demo ===");
        logger1.info("Application started");
        logger1.debug("Debugging information");
        logger1.error("Error occurred!");
        
        // Change level
        System.out.println("\n=== Changed to ERROR level ===");
        logger1.setLevel(LogLevel.ERROR);
        logger1.info("This won't show");
        logger1.error("This will show");
        
        System.out.println("\n✅ Singleton logger implemented!");
    }
}

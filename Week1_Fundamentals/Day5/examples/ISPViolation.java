/**
 * EXAMPLE: Interface Segregation Principle Violation
 * 
 * This code VIOLATES ISP - a "fat" interface forces 
 * clients to implement methods they don't need
 */

public class ISPViolation {
    
    // ❌ VIOLATION: Fat interface with too many methods
    interface Worker {
        void work();
        void eat();
        void sleep();
        void code();
        void debug();
        void attendMeeting();
    }
    
    // Robot must implement all methods, even ones it doesn't need!
    static class Robot implements Worker {
        private String name;
        
        public Robot(String name) {
            this.name = name;
        }
        
        @Override
        public void work() {
            System.out.println(name + " is working...");
        }
        
        // ❌ Robot doesn't eat - forced to implement!
        @Override
        public void eat() {
            // Robot doesn't eat - empty implementation
        }
        
        // ❌ Robot doesn't sleep - forced to implement!
        @Override
        public void sleep() {
            // Robot doesn't sleep - empty implementation
        }
        
        @Override
        public void code() {
            System.out.println(name + " is coding...");
        }
        
        @Override
        public void debug() {
            System.out.println(name + " is debugging...");
        }
        
        // ❌ Robot doesn't attend meetings - forced to implement!
        @Override
        public void attendMeeting() {
            // Useless for a robot!
        }
    }
    
    // Human must implement all methods too
    static class Human implements Worker {
        private String name;
        
        public Human(String name) {
            this.name = name;
        }
        
        @Override
        public void work() {
            System.out.println(name + " is working...");
        }
        
        @Override
        public void eat() {
            System.out.println(name + " is eating...");
        }
        
        @Override
        public void sleep() {
            System.out.println(name + " is sleeping...");
        }
        
        @Override
        public void code() {
            System.out.println(name + " is coding...");
        }
        
        @Override
        public void debug() {
            System.out.println(name + " is debugging...");
        }
        
        @Override
        public void attendMeeting() {
            System.out.println(name + " is attending a meeting...");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("❌ ISP Violation Demo\n");
        
        Robot robot = new Robot("R2D2");
        Human human = new Human("John");
        
        // Both implement the same interface
        workerTasks(robot);
        workerTasks(human);
        
        System.out.println("\n⚠️ PROBLEM: Robot forced to implement eat(), sleep(), attendMeeting()!");
    }
    
    static void workerTasks(Worker worker) {
        worker.work();
        worker.code();
        // We only need work and code, but have access to all methods
    }
}

/**
 * EXAMPLE: Interface Segregation Principle Solution
 * 
 * This code FOLLOWS ISP - small, focused interfaces
 */

public class ISPSolution {
    
    // ✅ SOLUTION: Small, focused interfaces
    
    // Work-related capabilities
    interface Workable {
        void work();
    }
    
    // Code-related capabilities  
    interface Coder {
        void code();
    }
    
    // Life-related capabilities
    interface Livable {
        void eat();
        void sleep();
    }
    
    // Meeting-related capabilities
    interface Meetingable {
        void attendMeeting();
    }
    
    // Human implements multiple focused interfaces
    static class Human implements Workable, Coder, Livable, Meetingable {
        private String name;
        
        public Human(String name) {
            this.name = name;
        }
        
        @Override
        public void work() {
            System.out.println(name + " is working...");
        }
        
        @Override
        public void code() {
            System.out.println(name + " is coding...");
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
        public void attendMeeting() {
            System.out.println(name + " is attending a meeting...");
        }
    }
    
    // Robot only implements what it needs
    static class Robot implements Workable, Coder {
        private String name;
        
        public Robot(String name) {
            this.name = name;
        }
        
        @Override
        public void work() {
            System.out.println(name + " is working...");
        }
        
        @Override
        public void code() {
            System.out.println(name + " is coding...");
        }
        
        // Robot doesn't implement eat, sleep, or attendMeeting
    }
    
    // Can work with any Workable
    static void doWork(Workable worker) {
        worker.work();
    }
    
    // Can work with any Coder
    static void doCode(Coder coder) {
        coder.code();
    }
    
    public static void main(String[] args) {
        System.out.println("✅ ISP Solution Demo\n");
        
        Human human = new Human("John");
        Robot robot = new Robot("R2D2");
        
        // Human can do everything
        System.out.println("=== Human ===");
        doWork(human);
        doCode(human);
        human.eat();
        human.sleep();
        human.attendMeeting();
        
        // Robot only does what it can
        System.out.println("\n=== Robot ===");
        doWork(robot);
        doCode(robot);
        // robot.eat() - not available!
        
        // Both can be used where Workable is needed
        System.out.println("\n=== Polymorphism ===");
        Workable[] workers = {human, robot};
        for (Workable w : workers) {
            w.work();
        }
        
        System.out.println("\n🎉 ISP achieved! Clients only depend on interfaces they use!");
    }
}

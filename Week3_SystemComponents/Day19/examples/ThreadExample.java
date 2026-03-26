/**
 * EXAMPLE: Thread Creation and Lifecycle
 * 
 * This example demonstrates different ways to create threads in Java,
 * thread lifecycle management, and basic thread operations.
 * 
 * @author LLD Mastery
 * @version 1.0
 */

public class ThreadExample {
    
    // ============================================================
    // Method 1: Extending Thread Class
    // ============================================================
    static class MyThread extends Thread {
        private String threadName;
        
        public MyThread(String name) {
            this.threadName = name;
        }
        
        @Override
        public void run() {
            System.out.println(threadName + " started...");
            for (int i = 1; i <= 5; i++) {
                System.out.println(threadName + " - Count: " + i);
                try {
                    // Sleep to simulate work
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println(threadName + " was interrupted");
                    return;
                }
            }
            System.out.println(threadName + " finished!");
        }
        
        @Override
        public void start() {
            System.out.println(threadName + " starting...");
            super.start();
        }
    }
    
    // ============================================================
    // Method 2: Implementing Runnable Interface
    // ============================================================
    static class MyRunnable implements Runnable {
        private String taskName;
        
        public MyRunnable(String name) {
            this.taskName = name;
        }
        
        @Override
        public void run() {
            System.out.println(taskName + " is running on thread: " + 
                Thread.currentThread().getName());
            
            try {
                // Simulate work
                for (int i = 1; i <= 3; i++) {
                    System.out.println(taskName + " - Step: " + i);
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                System.out.println(taskName + " interrupted!");
            }
            
            System.out.println(taskName + " completed!");
        }
    }
    
    // ============================================================
    // Method 3: Using Callable for Return Values
    // ============================================================
    static class SumCalculator implements java.util.concurrent.Callable<Integer> {
        private int start;
        private int end;
        
        public SumCalculator(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        @Override
        public Integer call() throws Exception {
            System.out.println("Calculating sum from " + start + " to " + end + 
                " on thread: " + Thread.currentThread().getName());
            
            int sum = 0;
            for (int i = start; i <= end; i++) {
                sum += i;
                // Simulate some processing time
                if (i % 1000 == 0) {
                    Thread.sleep(1);
                }
            }
            return sum;
        }
    }
    
    // ============================================================
    // Method 4: Lambda Expression (Java 8+)
    // ============================================================
    static class LambdaThreadDemo {
        public static void run() {
            // Using lambda for simple tasks
            Runnable task1 = () -> {
                System.out.println("Lambda task 1 running");
                for (int i = 0; i < 3; i++) {
                    System.out.println("Lambda 1: " + i);
                }
            };
            
            // Using lambda with more complex logic
            Runnable task2 = () -> {
                System.out.println("Lambda task 2 running");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Lambda task 2 done");
            };
            
            Thread t1 = new Thread(task1, "Lambda-Thread-1");
            Thread t2 = new Thread(task2, "Lambda-Thread-2");
            
            t1.start();
            t2.start();
            
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    // ============================================================
    // Thread Priority and Daemon Threads
    // ============================================================
    static class PriorityDemo implements Runnable {
        private String name;
        
        public PriorityDemo(String name) {
            this.name = name;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println(name + " priority " + 
                    Thread.currentThread().getPriority() + " : " + i);
            }
        }
    }
    
    // ============================================================
    // Main Method - Demonstrate All Thread Creation Methods
    // ============================================================
    public static void main(String[] args) throws Exception {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║          THREAD CREATION AND LIFECYCLE DEMO                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");
        
        // ============================================================
        // Method 1: Extending Thread
        // ============================================================
        System.out.println("┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│ Method 1: Extending Thread Class                             │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
        
        MyThread thread1 = new MyThread("Thread-A");
        MyThread thread2 = new MyThread("Thread-B");
        
        thread1.start();
        thread2.start();
        
        // Wait for threads to complete
        thread1.join();
        thread2.join();
        
        System.out.println("\n");
        
        // ============================================================
        // Method 2: Implementing Runnable
        // ============================================================
        System.out.println("┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│ Method 2: Implementing Runnable Interface                    │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
        
        Runnable runnable1 = new MyRunnable("Task-1");
        Runnable runnable2 = new MyRunnable("Task-2");
        
        Thread t1 = new Thread(runnable1, "Runnable-Thread-1");
        Thread t2 = new Thread(runnable2, "Runnable-Thread-2");
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        System.out.println("\n");
        
        // ============================================================
        // Method 3: Using Callable with ExecutorService
        // ============================================================
        System.out.println("┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│ Method 3: Callable with ExecutorService                      │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
        
        java.util.concurrent.ExecutorService executor = 
            java.util.concurrent.Executors.newFixedThreadPool(2);
        
        // Submit multiple Callable tasks
        java.util.concurrent.Future<Integer> future1 = executor.submit(
            new SumCalculator(1, 100));
        java.util.concurrent.Future<Integer> future2 = executor.submit(
            new SumCalculator(101, 200));
        java.util.concurrent.Future<Integer> future3 = executor.submit(
            new SumCalculator(201, 300));
        
        // Get results (blocks until available)
        System.out.println("Sum 1-100: " + future1.get());
        System.out.println("Sum 101-200: " + future2.get());
        System.out.println("Sum 201-300: " + future3.get());
        
        executor.shutdown();
        
        System.out.println("\n");
        
        // ============================================================
        // Method 4: Lambda Expressions
        // ============================================================
        System.out.println("┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│ Method 4: Lambda Expressions (Java 8+)                       │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
        
        LambdaThreadDemo.run();
        
        System.out.println("\n");
        
        // ============================================================
        // Thread Priority Demo
        // ============================================================
        System.out.println("┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│ Thread Priority Demonstration                                │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
        
        Thread minPriority = new Thread(new PriorityDemo("Min"), "MinPriority");
        Thread maxPriority = new Thread(new PriorityDemo("Max"), "MaxPriority");
        Thread normPriority = new Thread(new PriorityDemo("Norm"), "NormPriority");
        
        minPriority.setPriority(Thread.MIN_PRIORITY);
        maxPriority.setPriority(Thread.MAX_PRIORITY);
        normPriority.setPriority(Thread.NORM_PRIORITY);
        
        minPriority.start();
        normPriority.start();
        maxPriority.start();
        
        minPriority.join();
        normPriority.join();
        maxPriority.join();
        
        System.out.println("\n");
        
        // ============================================================
        // Daemon Thread Demo
        // ============================================================
        System.out.println("┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│ Daemon Thread Demonstration                                 │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
        
        Thread daemonThread = new Thread(() -> {
            System.out.println("Daemon thread starting...");
            try {
                // This will not complete if main thread ends first
                for (int i = 0; i < 10; i++) {
                    System.out.println("Daemon: " + i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                System.out.println("Daemon interrupted!");
            }
            System.out.println("Daemon thread finished!");
        }, "Daemon-Thread");
        
        daemonThread.setDaemon(true);
        daemonThread.start();
        
        // Main thread does some work
        Thread.sleep(250);
        System.out.println("Main thread done - JVM will exit if only daemon threads remain");
        
        // ============================================================
        // Thread Information
        // ============================================================
        System.out.println("\n┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│ Current Thread Information                                   │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
        
        Thread current = Thread.currentThread();
        System.out.println("Current Thread Name: " + current.getName());
        System.out.println("Current Thread ID: " + current.getId());
        System.out.println("Current Thread Priority: " + current.getPriority());
        System.out.println("Current Thread State: " + current.getState());
        System.out.println("Is Daemon: " + current.isDaemon());
        System.out.println("Is Alive: " + current.isAlive());
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║          ALL THREAD DEMONSTRATIONS COMPLETE                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
    }
}

/**
 * EXAMPLE: Executor Framework and CompletableFuture
 * 
 * This example demonstrates advanced concurrency patterns including:
 * - Thread pools and executors
 * - Future and Callable
 * - CompletableFuture for async operations
 * - Parallel execution
 * 
 * @author LLD Mastery
 * @version 1.0
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ExecutorExample {
    
    // ============================================================
    // Example 1: Thread Pool Types
    // ============================================================
    static void demonstrateThreadPools() {
        System.out.println("\n┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│ 1. Thread Pool Types                                        │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
        
        // Fixed Thread Pool - constant number of threads
        ExecutorService fixedPool = Executors.newFixedThreadPool(2);
        System.out.println("Created FixedThreadPool with 2 threads");
        
        // Submit tasks
        for (int i = 1; i <= 4; i++) {
            final int taskId = i;
            fixedPool.submit(() -> {
                System.out.println("FixedPool Task " + taskId + " running on " + 
                    Thread.currentThread().getName());
                try { Thread.sleep(200); } catch (InterruptedException e) {}
            });
        }
        fixedPool.shutdown();
        
        // Cached Thread Pool - expands as needed
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        System.out.println("Created CachedThreadPool");
        
        cachedPool.submit(() -> {
            System.out.println("CachedPool Task running on " + Thread.currentThread().getName());
        });
        cachedPool.shutdown();
        
        // Single Thread Executor
        ExecutorService singlePool = Executors.newSingleThreadExecutor();
        System.out.println("Created SingleThreadExecutor");
        
        singlePool.submit(() -> System.out.println("Single thread task"));
        singlePool.shutdown();
    }
    
    // ============================================================
    // Example 2: Future and Callable
    // ============================================================
    static void demonstrateFuture() throws Exception {
        System.out.println("\n┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│ 2. Future and Callable                                     │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        // Callable that returns a value
        Callable<Integer> sumTask = () -> {
            System.out.println("Calculating sum on " + Thread.currentThread().getName());
            int sum = 0;
            for (int i = 1; i <= 100; i++) {
                sum += i;
                if (i % 20 == 0) Thread.sleep(50);
            }
            return sum;
        };
        
        Callable<String> stringTask = () -> {
            Thread.sleep(500);
            return "Task completed!";
        };
        
        // Submit and get results
        Future<Integer> sumFuture = executor.submit(sumTask);
        Future<String> stringFuture = executor.submit(stringTask);
        
        System.out.println("Sum result: " + sumFuture.get());
        System.out.println("String result: " + stringFuture.get());
        
        executor.shutdown();
    }
    
    // ============================================================
    // Example 3: CompletableFuture - Basic
    // ============================================================
    static void demonstrateCompletableFuture() throws Exception {
        System.out.println("\n┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│ 3. CompletableFuture - Basic Operations                     │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
        
        // Simple async chain
        CompletableFuture<String> future = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("Step 1: Getting data on " + Thread.currentThread().getName());
                return "Hello";
            })
            .thenApply(s -> {
                System.out.println("Step 2: Processing on " + Thread.currentThread().getName());
                return s + " World";
            })
            .thenApply(s -> {
                System.out.println("Step 3: Finalizing on " + Thread.currentThread().getName());
                return s + "!";
            });
        
        System.out.println("Result: " + future.get());
        
        // With custom executor
        ExecutorService customExecutor = Executors.newFixedThreadPool(2);
        CompletableFuture<Integer> cf = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("Computing on custom executor");
                return 42;
            }, customExecutor)
            .thenApply(i -> i * 2);
        
        System.out.println("Custom executor result: " + cf.get());
        customExecutor.shutdown();
    }
    
    // ============================================================
    // Example 4: CompletableFuture - Combining
    // ============================================================
    static void demonstrateCombining() throws Exception {
        System.out.println("\n┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│ 4. CompletableFuture - Combining Futures                     │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
        
        // thenCombine - combine two futures
        CompletableFuture<Integer> f1 = CompletableFuture
            .supplyAsync(() -> {
                sleep(100);
                return 10;
            });
        
        CompletableFuture<Integer> f2 = CompletableFuture
            .supplyAsync(() -> {
                sleep(100);
                return 20;
            });
        
        CompletableFuture<Integer> combined = f1.thenCombine(f2, (a, b) -> a + b);
        System.out.println("Combined (10 + 20): " + combined.get());
        
        // allOf - wait for all futures
        CompletableFuture<Void> allFutures = CompletableFuture
            .allOf(
                CompletableFuture.supplyAsync(() -> { sleep(100); return 1; }),
                CompletableFuture.supplyAsync(() -> { sleep(50); return 2; }),
                CompletableFuture.supplyAsync(() -> { sleep(150); return 3; })
            );
        
        allFutures.join();
        System.out.println("All futures completed!");
        
        // anyOf - wait for first future (returns CompletableFuture<Object>)
        CompletableFuture<String> anyFuture1 = CompletableFuture.supplyAsync(() -> { sleep(200); return "slow"; });
        CompletableFuture<String> anyFuture2 = CompletableFuture.supplyAsync(() -> { sleep(50); return "fast"; });
        CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(anyFuture1, anyFuture2);
        
        System.out.println("First result: " + anyFuture.get());
    }
    
    // ============================================================
    // Example 5: Exception Handling
    // ============================================================
    static void demonstrateExceptionHandling() {
        System.out.println("\n┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│ 5. Exception Handling in CompletableFuture                   │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
        
        // handle - handle both success and exception
        CompletableFuture<Integer> handleFuture = CompletableFuture
            .supplyAsync(() -> {
                if (Math.random() > 0.5) {
                    throw new RuntimeException("Random error!");
                }
                return 42;
            })
            .handle((result, ex) -> {
                if (ex != null) {
                    System.out.println("Exception caught: " + ex.getMessage());
                    return -1;
                }
                return result;
            });
        
        System.out.println("Handle result: " + handleFuture.join());
        
        // exceptionally - handle exception
        CompletableFuture<String> exFuture = CompletableFuture
            .supplyAsync(() -> {
                throw new RuntimeException("Error!");
            })
            .exceptionally(ex -> {
                System.out.println("Exception: " + ex.getMessage());
                return "Recovered value";
            });
        
        System.out.println("Exceptionally result: " + exFuture.join());
    }
    
    // ============================================================
    // Example 6: Parallel Stream Processing
    // ============================================================
    static void demonstrateParallelProcessing() {
        System.out.println("\n┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│ 6. Parallel Stream Processing                                │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Sequential processing
        long startSeq = System.nanoTime();
        List<Integer> seqResult = numbers.stream()
            .map(i -> {
                sleep(10);
                return i * 2;
            })
            .collect(Collectors.toList());
        long timeSeq = System.nanoTime() - startSeq;
        
        // Parallel processing
        long startPar = System.nanoTime();
        List<Integer> parResult = numbers.parallelStream()
            .map(i -> {
                sleep(10);
                return i * 2;
            })
            .collect(Collectors.toList());
        long timePar = System.nanoTime() - startPar;
        
        System.out.println("Sequential time: " + timeSeq / 1_000_000 + "ms");
        System.out.println("Parallel time: " + timePar / 1_000_000 + "ms");
        System.out.println("Results: " + seqResult);
    }
    
    // ============================================================
    // Example 7: Thread-Safe Counter with Atomic
    // ============================================================
    static void demonstrateAtomicVariables() {
        System.out.println("\n┌───────────────────────────────────────────────────────────────┐");
        System.out.println("│ 7. Atomic Variables                                          │");
        System.out.println("└───────────────────────────────────────────────────────────────┘");
        
        AtomicInteger counter = new AtomicInteger(0);
        
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        // Increment counter from multiple threads
        for (int i = 0; i < 1000; i++) {
            executor.submit(counter::incrementAndGet);
        }
        
        executor.shutdown();
        try { executor.awaitTermination(1, TimeUnit.SECONDS); } 
        catch (InterruptedException e) {}
        
        System.out.println("AtomicInteger final value: " + counter.get());
        
        // AtomicReference
        AtomicReference<String> ref = new AtomicReference<>("initial");
        ref.compareAndSet("initial", "updated");
        System.out.println("AtomicReference value: " + ref.get());
    }
    
    // ============================================================
    // Helper Method
    // ============================================================
    private static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
    
    // ============================================================
    // Main Method
    // ============================================================
    public static void main(String[] args) throws Exception {
        System.out.println("╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║          EXECUTOR FRAMEWORK AND COMPLETABLEFUTURE DEMO           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");
        
        demonstrateThreadPools();
        demonstrateFuture();
        demonstrateCompletableFuture();
        demonstrateCombining();
        demonstrateExceptionHandling();
        demonstrateParallelProcessing();
        demonstrateAtomicVariables();
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║          ALL EXECUTOR DEMONSTRATIONS COMPLETE                     ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");
    }
}

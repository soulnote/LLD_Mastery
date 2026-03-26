/**
 * SOLUTION: Async Order Processing Service
 */

public class Exercise1_Solution {
    
    static class Order {
        String orderId;
        String customerId;
        double amount;
        String status;
        
        public Order(String orderId, String customerId, double amount) {
            this.orderId = orderId;
            this.customerId = customerId;
            this.amount = amount;
            this.status = "PENDING";
        }
        
        @Override
        public String toString() {
            return "Order{" + "orderId='" + orderId + '\'' + 
                   ", customerId='" + customerId + '\'' + 
                   ", amount=" + amount + 
                   ", status='" + status + '\'' + '}';
        }
    }
    
    // Async Order Service using CompletableFuture
    static class AsyncOrderService {
        private final java.util.concurrent.ExecutorService executor;
        
        public AsyncOrderService() {
            this.executor = java.util.concurrent.Executors.newFixedThreadPool(3);
        }
        
        // Step 1: Validate order
        public java.util.concurrent.CompletableFuture<Boolean> validateOrder(Order order) {
            return java.util.concurrent.CompletableFuture.supplyAsync(() -> {
                System.out.println("Validating order: " + order.orderId);
                try { Thread.sleep(500); } catch (Exception e) {}
                boolean valid = order.amount > 0 && order.customerId != null;
                System.out.println("Order " + order.orderId + " validation: " + (valid ? "PASSED" : "FAILED"));
                return valid;
            }, executor);
        }
        
        // Step 2: Process payment
        public java.util.concurrent.CompletableFuture<Boolean> processPayment(Order order) {
            return java.util.concurrent.CompletableFuture.supplyAsync(() -> {
                System.out.println("Processing payment for: " + order.orderId);
                try { Thread.sleep(800); } catch (Exception e) {}
                boolean success = order.amount < 10000; // Simulated check
                System.out.println("Payment for " + order.orderId + ": " + (success ? "SUCCESS" : "FAILED"));
                return success;
            }, executor);
        }
        
        // Step 3: Update inventory
        public java.util.concurrent.CompletableFuture<Boolean> updateInventory(Order order) {
            return java.util.concurrent.CompletableFuture.supplyAsync(() -> {
                System.out.println("Updating inventory for: " + order.orderId);
                try { Thread.sleep(300); } catch (Exception e) {}
                System.out.println("Inventory updated for: " + order.orderId);
                return true;
            }, executor);
        }
        
        // Step 4: Schedule shipping
        public java.util.concurrent.CompletableFuture<Boolean> scheduleShipping(Order order) {
            return java.util.concurrent.CompletableFuture.supplyAsync(() -> {
                System.out.println("Scheduling shipping for: " + order.orderId);
                try { Thread.sleep(400); } catch (Exception e) {}
                System.out.println("Shipping scheduled for: " + order.orderId);
                return true;
            }, executor);
        }
        
        // Main process: Chain all operations
        public java.util.concurrent.CompletableFuture<Order> processOrder(Order order) {
            return validateOrder(order)
                .thenCompose(valid -> {
                    if (!valid) {
                        order.status = "VALIDATION_FAILED";
                        return java.util.concurrent.CompletableFuture.completedFuture(order);
                    }
                    return processPayment(order).thenApply(p -> {
                        if (!p) order.status = "PAYMENT_FAILED";
                        return order;
                    });
                })
                .thenCompose(o -> {
                    if ("PAYMENT_FAILED".equals(o.status)) {
                        return java.util.concurrent.CompletableFuture.completedFuture(o);
                    }
                    return updateInventory(o).thenApply(i -> o);
                })
                .thenCompose(o -> {
                    return scheduleShipping(o).thenApply(s -> {
                        if (s) o.status = "COMPLETED";
                        else o.status = "SHIPPING_FAILED";
                        return o;
                    });
                })
                .exceptionally(ex -> {
                    order.status = "ERROR: " + ex.getMessage();
                    return order;
                });
        }
        
        public void shutdown() {
            executor.shutdown();
        }
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("📝 Async Order Processing - SOLUTION\n");
        
        AsyncOrderService service = new AsyncOrderService();
        
        // Create orders
        Order order1 = new Order("ORD-001", "CUST-A", 500.0);
        Order order2 = new Order("ORD-002", "CUST-B", 15000.0); // Will fail payment
        
        System.out.println("Processing orders asynchronously...\n");
        
        long startTime = System.currentTimeMillis();
        
        // Process orders concurrently
        java.util.concurrent.CompletableFuture.allOf(
            service.processOrder(order1),
            service.processOrder(order2)
        ).get();
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                       RESULTS                                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\nOrder 1: " + order1);
        System.out.println("Order 2: " + order2);
        
        System.out.println("\nTotal time: " + (endTime - startTime) + "ms (parallel execution)");
        
        service.shutdown();
        
        System.out.println("\n✅ Async order processing complete!");
    }
}

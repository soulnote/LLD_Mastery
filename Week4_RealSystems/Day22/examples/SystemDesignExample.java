/**
 * Day 22 - Example: System Design Fundamentals
 * Demonstrates key system design concepts including scalability,
 * load balancing, and distributed systems basics.
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

// ============================================================
// 1. HORIZONTAL VS VERTICAL SCALING
// ============================================================

/**
 * Vertical Scaling Example - Adding more resources to a single server
 */
class VerticalScalableServer {
    private int cpuCores;
    private long memoryGB;
    private long storageGB;
    
    public VerticalScalableServer(int cpuCores, long memoryGB, long storageGB) {
        this.cpuCores = cpuCores;
        this.memoryGB = memoryGB;
        this.storageGB = storageGB;
    }
    
    public void upgrade(int additionalCores, long additionalMemoryGB) {
        this.cpuCores += additionalCores;
        this.memoryGB += additionalMemoryGB;
        System.out.println("Upgraded server - CPU: " + cpuCores + ", Memory: " + memoryGB + "GB");
    }
    
    public void processRequest(String request) {
        System.out.println("Processing request: " + request + " on server with " 
            + cpuCores + " cores and " + memoryGB + "GB memory");
    }
}

/**
 * Horizontal Scaling Example - Adding more servers
 */
class HorizontalScalableServer {
    private final String serverId;
    private final AtomicInteger requestCount = new AtomicInteger(0);
    
    public HorizontalScalableServer(String serverId) {
        this.serverId = serverId;
    }
    
    public void processRequest(String request) {
        int count = requestCount.incrementAndGet();
        System.out.println("Server " + serverId + " processing request: " + request + " (total: " + count + ")");
    }
    
    public int getRequestCount() {
        return requestCount.get();
    }
}

// ============================================================
// 2. LOAD BALANCING IMPLEMENTATION
// ============================================================

/**
 * Simple Load Balancer with Round Robin algorithm
 */
class LoadBalancer {
    private final List<HorizontalScalableServer> servers = new ArrayList<>();
    private final AtomicInteger currentIndex = new AtomicInteger(0);
    private final Map<String, HorizontalScalableServer> sessionAffinity = new ConcurrentHashMap<>();
    
    public void addServer(HorizontalScalableServer server) {
        servers.add(server);
        System.out.println("Added server: " + server);
    }
    
    // Round Robin Load Balancing
    public HorizontalScalableServer getServerRoundRobin() {
        if (servers.isEmpty()) {
            throw new RuntimeException("No servers available");
        }
        int index = currentIndex.getAndUpdate(i -> (i + 1) % servers.size());
        return servers.get(index);
    }
    
    // Least Connections Load Balancing
    public HorizontalScalableServer getServerLeastConnections() {
        return servers.stream()
            .min(Comparator.comparingInt(HorizontalScalableServer::getRequestCount))
            .orElseThrow(() -> new RuntimeException("No servers available"));
    }
    
    // IP Hash Load Balancing (Session Persistence)
    public HorizontalScalableServer getServerByIP(String clientIP) {
        return sessionAffinity.computeIfAbsent(clientIP, ip -> {
            int hash = ip.hashCode();
            int index = Math.abs(hash) % servers.size();
            return servers.get(index);
        });
    }
    
    public void routeRequest(String request) {
        HorizontalScalableServer server = getServerRoundRobin();
        server.processRequest(request);
    }
}

// ============================================================
// 3. CACHING STRATEGIES
// ============================================================

/**
 * Cache-Aside Pattern Implementation
 */
class CacheAsideCache<K, V> {
    private final Map<K, V> cache = new ConcurrentHashMap<>();
    private final Map<K, Long> expiryMap = new ConcurrentHashMap<>();
    private final long defaultTTL; // Time to live in milliseconds
    
    public CacheAsideCache(long defaultTTL) {
        this.defaultTTL = defaultTTL;
    }
    
    public V get(K key) {
        // Check if expired
        if (isExpired(key)) {
            cache.remove(key);
            expiryMap.remove(key);
            return null;
        }
        return cache.get(key);
    }
    
    public void put(K key, V value) {
        cache.put(key, value);
        expiryMap.put(key, System.currentTimeMillis() + defaultTTL);
    }
    
    public V getOrLoad(K key, java.util.function.Supplier<V> loader) {
        V value = get(key);
        if (value == null) {
            value = loader.get();
            if (value != null) {
                put(key, value);
            }
        }
        return value;
    }
    
    private boolean isExpired(K key) {
        Long expiry = expiryMap.get(key);
        return expiry != null && System.currentTimeMillis() > expiry;
    }
    
    public void invalidate(K key) {
        cache.remove(key);
        expiryMap.remove(key);
    }
}

/**
 * Write-Through Cache Implementation
 */
class WriteThroughCache<K, V> {
    private final Map<K, V> cache = new ConcurrentHashMap<>();
    private final List<V> database = new ArrayList<>();
    
    public void write(K key, V value) {
        // Write to cache first
        cache.put(key, value);
        // Then write to database
        database.add(value);
        System.out.println("Write-through: Written to cache and database");
    }
    
    public V read(K key) {
        return cache.get(key);
    }
}

// ============================================================
// 4. CIRCUIT BREAKER PATTERN
// ============================================================

enum CircuitBreakerState {
    CLOSED,    // Normal operation
    OPEN,      // Failing, reject requests
    HALF_OPEN  // Testing if service recovered
}

class CircuitBreaker {
    private final String serviceName;
    private final int failureThreshold;
    private final long resetTimeout;
    
    private CircuitBreakerState state = CircuitBreakerState.CLOSED;
    private int failureCount = 0;
    private long lastFailureTime = 0;
    
    public CircuitBreaker(String serviceName, int failureThreshold, long resetTimeout) {
        this.serviceName = serviceName;
        this.failureThreshold = failureThreshold;
        this.resetTimeout = resetTimeout;
    }
    
    public boolean allowRequest() {
        if (state == CircuitBreakerState.CLOSED) {
            return true;
        }
        
        if (state == CircuitBreakerState.OPEN) {
            // Check if reset timeout has passed
            if (System.currentTimeMillis() - lastFailureTime > resetTimeout) {
                state = CircuitBreakerState.HALF_OPEN;
                System.out.println("Circuit breaker for " + serviceName + " moved to HALF_OPEN");
                return true;
            }
            return false;
        }
        
        // Half-open state - allow one request
        return true;
    }
    
    public void recordSuccess() {
        failureCount = 0;
        state = CircuitBreakerState.CLOSED;
        System.out.println("Circuit breaker for " + serviceName + " reset to CLOSED");
    }
    
    public void recordFailure() {
        failureCount++;
        lastFailureTime = System.currentTimeMillis();
        
        if (failureCount >= failureThreshold) {
            state = CircuitBreakerState.OPEN;
            System.out.println("Circuit breaker for " + serviceName + " opened due to failures");
        }
    }
    
    public CircuitBreakerState getState() {
        return state;
    }
}

// ============================================================
// 5. RATE LIMITER (Token Bucket)
// ============================================================

class TokenBucketRateLimiter {
    private final int maxTokens;
    private final int refillRate; // tokens per second
    private final AtomicInteger availableTokens;
    private long lastRefillTime;
    
    public TokenBucketRateLimiter(int maxTokens, int refillRate) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.availableTokens = new AtomicInteger(maxTokens);
        this.lastRefillTime = System.currentTimeMillis();
    }
    
    public boolean allowRequest() {
        refillTokens();
        int current = availableTokens.decrementAndGet();
        if (current >= 0) {
            return true;
        }
        // Return token since we couldn't use it
        availableTokens.incrementAndGet();
        return false;
    }
    
    private synchronized void refillTokens() {
        long now = System.currentTimeMillis();
        long timePassed = (now - lastRefillTime) / 1000; // convert to seconds
        if (timePassed > 0) {
            int tokensToAdd = (int) (timePassed * refillRate);
            int newTokens = Math.min(maxTokens, availableTokens.get() + tokensToAdd);
            availableTokens.set(newTokens);
            lastRefillTime = now;
        }
    }
}

// ============================================================
// MAIN DEMO CLASS
// ============================================================

public class SystemDesignExample {
    public static void main(String[] args) {
        System.out.println("=== System Design Fundamentals Demo ===\n");
        
        // 1. Scaling Examples
        demonstrateScaling();
        
        // 2. Load Balancing
        demonstrateLoadBalancing();
        
        // 3. Caching
        demonstrateCaching();
        
        // 4. Circuit Breaker
        demonstrateCircuitBreaker();
        
        // 5. Rate Limiting
        demonstrateRateLimiting();
    }
    
    private static void demonstrateScaling() {
        System.out.println("--- Vertical Scaling ---");
        VerticalScalableServer server = new VerticalScalableServer(4, 16, 500);
        server.processRequest("User login");
        server.upgrade(4, 32);
        server.processRequest("Data fetch");
        
        System.out.println("\n--- Horizontal Scaling ---");
        HorizontalScalableServer server1 = new HorizontalScalableServer("server-1");
        HorizontalScalableServer server2 = new HorizontalScalableServer("server-2");
        
        LoadBalancer lb = new LoadBalancer();
        lb.addServer(server1);
        lb.addServer(server2);
        
        for (int i = 0; i < 4; i++) {
            lb.routeRequest("Request-" + i);
        }
    }
    
    private static void demonstrateCaching() {
        System.out.println("\n--- Cache-Aside Pattern ---");
        CacheAsideCache<String, String> cache = new CacheAsideCache<>(5000);
        
        // First access - cache miss, load from source
        String value = cache.getOrLoad("user:1", () -> {
            System.out.println("Loading from database...");
            return "User{Name: John}";
        });
        System.out.println("Retrieved: " + value);
        
        // Second access - cache hit
        value = cache.get("user:1");
        System.out.println("Retrieved from cache: " + value);
    }
    
    private static void demonstrateCircuitBreaker() {
        System.out.println("\n--- Circuit Breaker Pattern ---");
        CircuitBreaker cb = new CircuitBreaker("payment-service", 3, 5000);
        
        // Simulate failures
        for (int i = 0; i < 5; i++) {
            System.out.println("Request " + (i+1) + " allowed: " + cb.allowRequest());
            cb.recordFailure();
        }
        System.out.println("Circuit state: " + cb.getState());
        
        // Wait and try again
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {}
        
        System.out.println("After timeout - Request allowed: " + cb.allowRequest());
    }
    
    private static void demonstrateRateLimiting() {
        System.out.println("\n--- Rate Limiter (Token Bucket) ---");
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(5, 2); // 5 tokens, 2 refills/sec
        
        for (int i = 0; i < 10; i++) {
            boolean allowed = limiter.allowRequest();
            System.out.println("Request " + (i+1) + ": " + (allowed ? "Allowed" : "Rejected"));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {}
        }
    }
    
    private static void demonstrateLoadBalancing() {
        // Already demonstrated in demonstrateScaling()
    }
}

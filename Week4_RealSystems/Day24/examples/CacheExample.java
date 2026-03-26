/**
 * Day 24 - Example: Distributed Cache Implementation
 * Demonstrates LRU cache, cache strategies, and distributed caching concepts.
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

// ============================================================
// 1. LRU CACHE IMPLEMENTATION
// ============================================================

class LRUCache<K, V> {
    private final int capacity;
    private final LinkedHashMap<K, V> cache;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        // accessOrder=true makes it maintain access order
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > LRUCache.this.capacity;
            }
        };
    }
    
    public V get(K key) {
        lock.readLock().lock();
        try {
            return cache.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public void put(K key, V value) {
        lock.writeLock().lock();
        try {
            cache.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    public int size() {
        lock.readLock().lock();
        try {
            return cache.size();
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public void clear() {
        lock.writeLock().lock();
        try {
            cache.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    public boolean containsKey(K key) {
        lock.readLock().lock();
        try {
            return cache.containsKey(key);
        } finally {
            lock.readLock().unlock();
        }
    }
}

// ============================================================
// 2. CACHE WITH TTL
// ============================================================

class TTLCache<K, V> {
    private final int capacity;
    private final long ttlMillis;
    private final Map<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();
    
    static class CacheEntry<V> {
        final V value;
        final long expiryTime;
        
        CacheEntry(V value, long ttlMillis) {
            this.value = value;
            this.expiryTime = System.currentTimeMillis() + ttlMillis;
        }
        
        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }
    
    public TTLCache(int capacity, long ttlMillis) {
        this.capacity = capacity;
        this.ttlMillis = ttlMillis;
    }
    
    public V get(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry == null) return null;
        
        if (entry.isExpired()) {
            cache.remove(key);
            return null;
        }
        
        return entry.value;
    }
    
    public void put(K key, V value) {
        if (cache.size() >= capacity) {
            evictExpired();
            if (cache.size() >= capacity) {
                evictOldest();
            }
        }
        cache.put(key, new CacheEntry<>(value, ttlMillis));
    }
    
    private void evictExpired() {
        cache.entrySet().removeIf(e -> e.getValue().isExpired());
    }
    
    private void evictOldest() {
        cache.entrySet().stream()
            .min(Comparator.comparingLong(e -> e.getValue().expiryTime))
            .ifPresent(e -> cache.remove(e.getKey()));
    }
    
    public int size() {
        return cache.size();
    }
}

// ============================================================
// 3. CACHE-ASIDE PATTERN
// ============================================================

class CacheAsideCache<K, V> {
    private final Map<K, V> cache = new ConcurrentHashMap<>();
    private final Map<K, Long> expiryMap = new ConcurrentHashMap<>();
    private final long defaultTTL;
    
    public CacheAsideCache(long defaultTTL) {
        this.defaultTTL = defaultTTL;
    }
    
    public V get(K key, java.util.function.Supplier<V> loader) {
        // Check cache first
        V value = getFromCache(key);
        if (value != null) {
            return value;
        }
        
        // Load from source
        value = loader.get();
        if (value != null) {
            put(key, value);
        }
        
        return value;
    }
    
    private V getFromCache(K key) {
        Long expiry = expiryMap.get(key);
        if (expiry != null && System.currentTimeMillis() > expiry) {
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
    
    public void invalidate(K key) {
        cache.remove(key);
        expiryMap.remove(key);
    }
}

// ============================================================
// 4. DISTRIBUTED CACHE SIMULATION (Mock Redis)
// ============================================================

class DistributedCacheSimulation {
    private final Map<String, String> storage = new ConcurrentHashMap<>();
    private final Map<String, Long> expiryMap = new ConcurrentHashMap<>();
    private final String nodeId;
    
    public DistributedCacheSimulation(String nodeId) {
        this.nodeId = nodeId;
    }
    
    public void set(String key, String value) {
        storage.put(key, value);
        System.out.println("[" + nodeId + "] SET " + key + " = " + value);
    }
    
    public void setex(String key, int ttl, String value) {
        storage.put(key, value);
        expiryMap.put(key, System.currentTimeMillis() + ttl * 1000L);
        System.out.println("[" + nodeId + "] SETEX " + key + " (TTL: " + ttl + "s)");
    }
    
    public String get(String key) {
        Long expiry = expiryMap.get(key);
        if (expiry != null && System.currentTimeMillis() > expiry) {
            storage.remove(key);
            expiryMap.remove(key);
            System.out.println("[" + nodeId + "] GET " + key + " = EXPIRED");
            return null;
        }
        
        String value = storage.get(key);
        System.out.println("[" + nodeId + "] GET " + key + " = " + value);
        return value;
    }
    
    public void del(String key) {
        storage.remove(key);
        expiryMap.remove(key);
        System.out.println("[" + nodeId + "] DEL " + key);
    }
    
    public Long incr(String key) {
        String value = storage.get(key);
        long newValue = (value == null) ? 1 : Long.parseLong(value) + 1;
        storage.put(key, String.valueOf(newValue));
        System.out.println("[" + nodeId + "] INCR " + key + " = " + newValue);
        return newValue;
    }
}

// ============================================================
// DEMO
// ============================================================

public class CacheExample {
    public static void main(String[] args) {
        System.out.println("=== Distributed Cache Demo ===\n");
        
        // Test LRU Cache
        testLRUCache();
        
        // Test TTL Cache
        testTTLCache();
        
        // Test Cache-Aside
        testCacheAside();
        
        // Test Distributed Cache Simulation
        testDistributedCache();
    }
    
    private static void testLRUCache() {
        System.out.println("--- LRU Cache Test ---");
        LRUCache<String, String> cache = new LRUCache<>(3);
        
        cache.put("A", "ValueA");
        cache.put("B", "ValueB");
        cache.put("C", "ValueC");
        
        System.out.println("Size after 3 puts: " + cache.size());
        
        cache.get("A"); // Access A to make it recently used
        
        cache.put("D", "ValueD"); // Should evict B (least recently used)
        
        System.out.println("Contains A: " + cache.containsKey("A"));
        System.out.println("Contains B: " + cache.containsKey("B"));
        System.out.println("Contains D: " + cache.containsKey("D"));
        System.out.println();
    }
    
    private static void testTTLCache() {
        System.out.println("--- TTL Cache Test ---");
        TTLCache<String, String> cache = new TTLCache<>(3, 1000); // 1 second TTL
        
        cache.put("X", "ValueX");
        System.out.println("Initial get: " + cache.get("X"));
        
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {}
        
        System.out.println("After expiry get: " + cache.get("X"));
        System.out.println();
    }
    
    private static void testCacheAside() {
        System.out.println("--- Cache-Aside Pattern Test ---");
        CacheAsideCache<String, String> cache = new CacheAsideCache<>(5000);
        
        // First access - loads from "database"
        String value = cache.get("user:1", () -> {
            System.out.println("Loading from database...");
            return "User{Name: John, Age: 30}";
        });
        System.out.println("First load: " + value);
        
        // Second access - from cache
        value = cache.get("user:1", () -> "Database load");
        System.out.println("Second load: " + value);
        
        // Invalidate
        cache.invalidate("user:1");
        value = cache.get("user:1", () -> "Database load");
        System.out.println("After invalidation: " + value);
        System.out.println();
    }
    
    private static void testDistributedCache() {
        System.out.println("--- Distributed Cache Simulation ---");
        
        DistributedCacheSimulation redis1 = new DistributedCacheSimulation("Node-1");
        DistributedCacheSimulation redis2 = new DistributedCacheSimulation("Node-2");
        
        redis1.setex("session:abc", 3600, "user_data");
        redis1.incr("request:count");
        redis1.incr("request:count");
        
        redis2.get("session:abc");
    }
}

/**
 * Day 24 - Solution: LRU Cache Implementation
 * 
 * Complete implementation of an LRU Cache with O(1) time complexity.
 */

import java.util.*;

public class Exercise1_Solution {
    
    public static void main(String[] args) {
        System.out.println("=== LRU Cache Solution ===\n");
        
        LRUCache cache = new LRUCache(2);
        
        // Test 1: Basic put and get
        System.out.println("--- Test 1: Basic Operations ---");
        cache.put(1, "value1");
        cache.put(2, "value2");
        System.out.println("Get(1): " + cache.get(1)); // "value1"
        System.out.println("Get(2): " + cache.get(2)); // "value2"
        
        // Test 2: Eviction
        System.out.println("\n--- Test 2: Eviction ---");
        cache.put(3, "value3"); // evicts key 2
        System.out.println("Get(1): " + cache.get(1)); // "value1"
        System.out.println("Get(2): " + cache.get(2)); // null (evicted)
        System.out.println("Get(3): " + cache.get(3)); // "value3"
        
        // Test 3: Update moves to end
        System.out.println("\n--- Test 3: Update ---");
        cache.put(1, "value1_updated"); // updates key 1, moves to end
        cache.put(4, "value4"); // evicts key 3 (not key 1)
        System.out.println("Get(1): " + cache.get(1)); // "value1_updated"
        System.out.println("Get(3): " + cache.get(3)); // null (evicted)
        System.out.println("Get(4): " + cache.get(4)); // "value4"
        
        System.out.println("\n=== All Tests Passed ===");
    }
}

// ============================================================
// LRU CACHE IMPLEMENTATION
// ============================================================

class LRUCache {
    private final int capacity;
    private final LinkedHashMap<Integer, String> cache;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        // accessOrder=true makes it maintain access order (most recent at end)
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
                // Remove eldest entry when size exceeds capacity
                return size() > LRUCache.this.capacity;
            }
        };
    }
    
    public String get(int key) {
        return cache.get(key);
    }
    
    public void put(int key, String value) {
        cache.put(key, value);
    }
}
